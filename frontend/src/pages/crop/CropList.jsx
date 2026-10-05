import { useCallback, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import ErrorMessage from "../../components/ErrorMessage";
import Loading from "../../components/Loading";
import { getLoggedInUser } from "../../services/authService";
import { deleteCrop, getCropsByFarm } from "../../services/cropService";
import { getFarmsByUser } from "../../services/farmService";

function CropList() {
  const [crops, setCrops] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const loadCrops = useCallback(async () => {
    try {
      const user = await getLoggedInUser();
      const farms = await getFarmsByUser(user.authId);
      const lists = await Promise.all(farms.map((farm) => getCropsByFarm(farm.farmId)));
      setError("");
      setCrops(lists.flat());
    } catch (loadError) { setError(loadError.message); }
    finally { setLoading(false); }
  }, []);
  useEffect(() => { queueMicrotask(() => { void loadCrops(); }); }, [loadCrops]);

  async function handleDelete(cropId) {
    if (!window.confirm("Delete this crop?")) return;
    try { await deleteCrop(cropId); await loadCrops(); }
    catch (deleteError) { setError(deleteError.message); }
  }

  return <div className="space-y-5">
    <div className="flex flex-wrap items-center justify-between gap-3"><div><h1 className="text-2xl font-bold text-green-950">My crops</h1><p className="mt-1 text-sm text-gray-600">View and manage crops across your farms.</p></div><Link to="/crops/new" className="rounded-lg bg-green-700 px-4 py-2.5 font-semibold text-white">Add crop</Link></div>
    <ErrorMessage message={error} onRetry={loadCrops} />
    {loading ? <Loading message="Loading your crops..." /> : crops.length ? <div className="grid gap-4 sm:grid-cols-2">
      {crops.map((crop) => <article key={crop.cropId} className="rounded-2xl border border-green-100 bg-white p-5 shadow-sm">
        <h2 className="text-lg font-bold text-green-900">{crop.cropName}</h2>
        <p className="mt-2 text-sm text-gray-600">{crop.season} season · {crop.status}</p>
        <p className="mt-1 text-sm text-gray-600">Expected harvest: {crop.expectedHarvestDate || "—"}</p>
        <div className="mt-4 flex gap-2">
          <Link to={`/crops/${crop.cropId}`} className="rounded-lg bg-green-50 px-3 py-2 text-sm font-semibold text-green-800">Details</Link>
          <button onClick={() => handleDelete(crop.cropId)} className="rounded-lg border border-red-200 px-3 py-2 text-sm font-semibold text-red-700">Delete</button>
        </div>
      </article>)}
    </div> : <p className="rounded-xl bg-white p-5 text-gray-600">No crops found. Add a crop to one of your farms.</p>}
  </div>;
}

export default CropList;
