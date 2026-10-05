import { useCallback, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import ErrorMessage from "../../components/ErrorMessage";
import Loading from "../../components/Loading";
import { getLoggedInUser } from "../../services/authService";
import { deleteFarm, getFarmsByUser } from "../../services/farmService";

function FarmList() {
  const [farms, setFarms] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const loadFarms = useCallback(async () => {
    try {
      const user = await getLoggedInUser();
      const result = await getFarmsByUser(user.authId);
      setError("");
      setFarms(result);
    } catch (loadError) {
      setError(loadError.message);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => { queueMicrotask(() => { void loadFarms(); }); }, [loadFarms]);

  async function handleDelete(farmId) {
    if (!window.confirm("Delete this farm?")) return;
    try {
      await deleteFarm(farmId);
      await loadFarms();
    } catch (deleteError) {
      setError(deleteError.message);
    }
  }

  return (
    <div className="space-y-5">
      <div className="flex flex-wrap items-center justify-between gap-3">
        <div><h1 className="text-2xl font-bold text-green-950">My farms</h1><p className="mt-1 text-sm text-gray-600">Manage the farms linked to your account.</p></div>
        <Link to="/farms/new" className="rounded-lg bg-green-700 px-4 py-2.5 font-semibold text-white hover:bg-green-800">Add farm</Link>
      </div>
      <ErrorMessage message={error} onRetry={loadFarms} />
      {loading ? <Loading message="Loading your farms..." /> : farms.length ? (
        <div className="grid gap-4 sm:grid-cols-2">
          {farms.map((farm) => (
            <article key={farm.farmId} className="rounded-2xl border border-green-100 bg-white p-5 shadow-sm">
              <h2 className="text-lg font-bold text-green-900">{farm.farmName}</h2>
              <p className="mt-2 text-sm text-gray-600">{farm.address || "No address provided"}</p>
              <p className="mt-1 text-sm text-gray-600">Area: {farm.farmArea ?? "—"} · Soil: {farm.type || "—"}</p>
              <div className="mt-4 flex flex-wrap gap-2">
                <Link to={`/farms/${farm.farmId}`} className="rounded-lg bg-green-50 px-3 py-2 text-sm font-semibold text-green-800 hover:bg-green-100">View details</Link>
                <Link to={`/farms/${farm.farmId}/edit`} className="rounded-lg border border-gray-200 px-3 py-2 text-sm font-semibold text-gray-700 hover:bg-gray-50">Edit</Link>
                <button onClick={() => handleDelete(farm.farmId)} className="rounded-lg border border-red-200 px-3 py-2 text-sm font-semibold text-red-700 hover:bg-red-50">Delete</button>
              </div>
            </article>
          ))}
        </div>
      ) : <p className="rounded-xl bg-white p-5 text-gray-600">No farms found. Add your first farm to get started.</p>}
    </div>
  );
}

export default FarmList;
