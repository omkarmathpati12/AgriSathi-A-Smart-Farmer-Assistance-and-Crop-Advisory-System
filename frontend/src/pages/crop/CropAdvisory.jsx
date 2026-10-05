import { useCallback, useEffect, useState } from "react";
import ErrorMessage from "../../components/ErrorMessage";
import Loading from "../../components/Loading";
import { getLoggedInUser } from "../../services/authService";
import { analyzeFarmCropHealth } from "../../services/aiService";
import { getFarmsByUser } from "../../services/farmService";

function CropAdvisory() {
  const [farms, setFarms] = useState([]);
  const [selectedFarm, setSelectedFarm] = useState("");
  const [advice, setAdvice] = useState(null);
  const [loading, setLoading] = useState(true);
  const [analyzing, setAnalyzing] = useState(false);
  const [error, setError] = useState("");
  const loadFarms = useCallback(async () => {
    try {
      const user = await getLoggedInUser();
      const userFarms = await getFarmsByUser(user.authId);
      setError("");
      setFarms(userFarms);
      if (userFarms.length) setSelectedFarm(String(userFarms[0].farmId));
    } catch (loadError) { setError(loadError.message); }
    finally { setLoading(false); }
  }, []);
  useEffect(() => { queueMicrotask(() => { void loadFarms(); }); }, [loadFarms]);
  async function handleAnalyze(event) {
    event.preventDefault(); setAnalyzing(true); setError("");
    try { setAdvice(await analyzeFarmCropHealth(selectedFarm)); }
    catch (analysisError) { setError(analysisError.message); }
    finally { setAnalyzing(false); }
  }

  return <div className="mx-auto max-w-3xl space-y-5"><div><h1 className="text-2xl font-bold text-green-950">Crop advisory</h1><p className="mt-1 text-sm text-gray-600">Ask the AI service to review your selected farm&apos;s crop health.</p></div><ErrorMessage message={error} onRetry={loadFarms} />{loading ? <Loading /> : farms.length ? <form onSubmit={handleAnalyze} className="flex flex-wrap items-end gap-3 rounded-2xl border border-green-100 bg-white p-5 shadow-sm"><label className="min-w-56 flex-1 text-sm font-medium text-gray-700">Farm<select value={selectedFarm} onChange={(event) => setSelectedFarm(event.target.value)} className="mt-1 w-full rounded-lg border border-gray-300 px-3 py-2">{farms.map((farm) => <option key={farm.farmId} value={farm.farmId}>{farm.farmName}</option>)}</select></label><button disabled={analyzing} className="rounded-lg bg-green-700 px-4 py-2.5 font-semibold text-white disabled:opacity-60">{analyzing ? "Checking..." : "Get crop health advice"}</button></form> : <p className="rounded-xl bg-white p-5 text-gray-600">Add a farm first to request a crop advisory.</p>}
    {advice && <section className="space-y-3 rounded-2xl border border-green-100 bg-white p-5 shadow-sm"><h2 className="text-lg font-bold text-green-900">AI crop health result</h2><p><strong>Condition:</strong> {advice.condition} · <strong>Health score:</strong> {advice.healthScore}%</p><p>{advice.summary}</p>{advice.issuesFound?.length > 0 && <div><h3 className="font-semibold">Issues found</h3><ul className="list-inside list-disc text-sm text-gray-700">{advice.issuesFound.map((item) => <li key={item}>{item}</li>)}</ul></div>}{advice.recommendations?.length > 0 && <div><h3 className="font-semibold">Recommendations</h3><ul className="list-inside list-disc text-sm text-gray-700">{advice.recommendations.map((item) => <li key={item}>{item}</li>)}</ul></div>}</section>}
  </div>;
}

export default CropAdvisory;
