import { useCallback, useEffect, useState } from "react";
import { getLoggedInUser } from "../../services/authService";
import { getCropRecommendations } from "../../services/aiService";
import { getFarmsByUser } from "../../services/farmService";
import ErrorMessage from "../../components/ErrorMessage";
import Loading from "../../components/Loading";

function AIAdvice() {
  const [farms, setFarms] = useState([]);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState("");
  const [result, setResult] = useState(null);
  const [farmId, setFarmId] = useState("");
  const loadFarms = useCallback(async () => {
    try {
      const user = await getLoggedInUser();
      const userFarms = await getFarmsByUser(user.authId);
      setError("");
      setFarms(userFarms);
      if (userFarms.length) setFarmId(String(userFarms[0].farmId));
    } catch (loadError) { setError(loadError.message); }
    finally { setLoading(false); }
  }, []);
  useEffect(() => { queueMicrotask(() => { void loadFarms(); }); }, [loadFarms]);

  async function handleSubmit(event) {
    event.preventDefault();
    const values = new FormData(event.currentTarget);
    setSubmitting(true); setError(""); setResult(null);
    try {
      setResult(await getCropRecommendations({
        farmId: farmId ? Number(farmId) : null,
        season: values.get("season"),
        soilType: values.get("soilType"),
        waterAvailability: values.get("waterAvailability"),
        landAreaAcres: values.get("landAreaAcres") ? Number(values.get("landAreaAcres")) : null,
        locationState: values.get("locationState") || null,
      }));
    } catch (requestError) { setError(requestError.message); }
    finally { setSubmitting(false); }
  }

  const fieldClass = "mt-1 w-full rounded-lg border border-gray-300 px-3 py-2";
  return <div className="mx-auto max-w-3xl space-y-5"><div><h1 className="text-2xl font-bold text-green-950">AI crop recommendations</h1><p className="mt-1 text-sm text-gray-600">Get suitable crop suggestions using your farm and season information.</p></div><ErrorMessage message={error} onRetry={loadFarms} />{loading ? <Loading /> : <form onSubmit={handleSubmit} className="grid gap-4 rounded-2xl border border-green-100 bg-white p-5 shadow-sm sm:grid-cols-2">
    {farms.length > 0 && <label className="text-sm font-medium text-gray-700">Farm (optional)<select value={farmId} onChange={(event) => setFarmId(event.target.value)} className={fieldClass}><option value="">Use the details below</option>{farms.map((farm) => <option key={farm.farmId} value={farm.farmId}>{farm.farmName}</option>)}</select></label>}
    <label className="text-sm font-medium text-gray-700">Season<select name="season" className={fieldClass}><option>KHARIF</option><option>RABI</option><option>SUMMER</option></select></label>
    <label className="text-sm font-medium text-gray-700">Soil type<input name="soilType" placeholder="For example: BLACK" className={fieldClass} /></label>
    <label className="text-sm font-medium text-gray-700">Water availability<select name="waterAvailability" className={fieldClass}><option>HIGH</option><option>MEDIUM</option><option>LOW</option></select></label>
    <label className="text-sm font-medium text-gray-700">Land area (acres)<input name="landAreaAcres" type="number" min="0" step="any" className={fieldClass} /></label>
    <label className="text-sm font-medium text-gray-700">State<input name="locationState" placeholder="State name" className={fieldClass} /></label>
    <div className="sm:col-span-2"><button disabled={submitting} className="rounded-lg bg-green-700 px-5 py-2.5 font-semibold text-white disabled:opacity-60">{submitting ? "Getting suggestions..." : "Get crop recommendations"}</button></div>
  </form>}
  {result && <section className="space-y-3 rounded-2xl border border-green-100 bg-white p-5 shadow-sm"><h2 className="text-lg font-bold text-green-900">Recommendation result</h2><p>Season: {result.season || "—"} · Farm ID: {result.farmId ?? "—"}</p>{result.recommendations?.length ? result.recommendations.map((item, index) => <pre key={index} className="overflow-x-auto rounded-lg bg-green-50 p-3 text-sm">{JSON.stringify(item, null, 2)}</pre>) : <p>No recommendations were returned.</p>}</section>}
  </div>;
}

export default AIAdvice;
