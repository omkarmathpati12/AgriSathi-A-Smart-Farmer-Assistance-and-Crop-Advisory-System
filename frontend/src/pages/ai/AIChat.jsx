import { useCallback, useEffect, useState } from "react";
import { getLoggedInUser } from "../../services/authService";
import { askFarmerAssistant } from "../../services/aiService";
import { getFarmsByUser } from "../../services/farmService";
import ErrorMessage from "../../components/ErrorMessage";
import Loading from "../../components/Loading";

function AIChat() {
  const [farms, setFarms] = useState([]);
  const [farmId, setFarmId] = useState("");
  const [loading, setLoading] = useState(true);
  const [asking, setAsking] = useState(false);
  const [error, setError] = useState("");
  const [answer, setAnswer] = useState(null);
  const loadFarms = useCallback(async () => {
    try {
      const user = await getLoggedInUser();
      const list = await getFarmsByUser(user.authId);
      setError("");
      setFarms(list);
      if (list.length) setFarmId(String(list[0].farmId));
    } catch (loadError) { setError(loadError.message); }
    finally { setLoading(false); }
  }, []);
  useEffect(() => { queueMicrotask(() => { void loadFarms(); }); }, [loadFarms]);
  async function handleSubmit(event) {
    event.preventDefault();
    const question = new FormData(event.currentTarget).get("question");
    setAsking(true); setError(""); setAnswer(null);
    try { setAnswer(await askFarmerAssistant({ farmId: Number(farmId), question })); }
    catch (requestError) { setError(requestError.message); }
    finally { setAsking(false); }
  }

  return <div className="mx-auto max-w-2xl space-y-5"><div><h1 className="text-2xl font-bold text-green-950">Ask AgriSathi</h1><p className="mt-1 text-sm text-gray-600">Ask a farming question for one of your farms.</p></div><ErrorMessage message={error} onRetry={loadFarms} />{loading ? <Loading /> : farms.length ? <form onSubmit={handleSubmit} className="space-y-4 rounded-2xl border border-green-100 bg-white p-5 shadow-sm"><label className="block text-sm font-medium text-gray-700">Farm<select value={farmId} onChange={(event) => setFarmId(event.target.value)} className="mt-1 w-full rounded-lg border border-gray-300 px-3 py-2">{farms.map((farm) => <option key={farm.farmId} value={farm.farmId}>{farm.farmName}</option>)}</select></label><label className="block text-sm font-medium text-gray-700">Your question<textarea name="question" rows="4" required minLength="2" placeholder="What would you like help with?" className="mt-1 w-full rounded-lg border border-gray-300 px-3 py-2" /></label><button disabled={asking} className="rounded-lg bg-green-700 px-5 py-2.5 font-semibold text-white disabled:opacity-60">{asking ? "Thinking..." : "Ask question"}</button></form> : <p className="rounded-xl bg-white p-5 text-gray-600">Add a farm before asking farm-related questions.</p>}
    {answer && <section className="rounded-2xl border border-green-100 bg-white p-5 shadow-sm"><h2 className="font-bold text-green-900">Your answer</h2><p className="mt-2 whitespace-pre-wrap leading-7 text-gray-700">{answer.answer}</p></section>}
  </div>;
}

export default AIChat;
