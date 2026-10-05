import { useCallback, useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import ErrorMessage from "../../components/ErrorMessage";
import Loading from "../../components/Loading";
import { getFarmById } from "../../services/farmService";

function FarmDetails() {
  const { farmId } = useParams();
  const [farm, setFarm] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const loadFarm = useCallback(async () => {
    try {
      const result = await getFarmById(farmId);
      setError("");
      setFarm(result);
    }
    catch (loadError) { setError(loadError.message); }
    finally { setLoading(false); }
  }, [farmId]);
  useEffect(() => { queueMicrotask(() => { void loadFarm(); }); }, [loadFarm]);

  return <div className="space-y-5"><div className="flex items-center justify-between gap-3"><h1 className="text-2xl font-bold text-green-950">Farm details</h1><Link to={`/farms/${farmId}/edit`} className="rounded-lg bg-green-700 px-4 py-2 text-white">Edit farm</Link></div><ErrorMessage message={error} onRetry={loadFarm} />{loading ? <Loading /> : farm && <section className="rounded-2xl border border-green-100 bg-white p-6 shadow-sm"><h2 className="text-xl font-bold text-green-900">{farm.farmName}</h2><dl className="mt-5 grid gap-4 sm:grid-cols-2">{[["Address", farm.address], ["Area", farm.farmArea], ["Soil type", farm.type], ["Water available", farm.waterAvailability ? "Yes" : "No"], ["Latitude", farm.latitude], ["Longitude", farm.longitude], ["Created", farm.createdAt]].map(([label, value]) => <div key={label}><dt className="text-sm text-gray-500">{label}</dt><dd className="font-medium text-gray-800">{value ?? "—"}</dd></div>)}</dl></section>}</div>;
}

export default FarmDetails;
