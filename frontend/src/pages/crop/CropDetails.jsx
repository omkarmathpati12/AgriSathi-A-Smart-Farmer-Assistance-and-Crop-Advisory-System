import { useCallback, useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import ErrorMessage from "../../components/ErrorMessage";
import Loading from "../../components/Loading";
import { getCropById } from "../../services/cropService";

function CropDetails() {
  const { cropId } = useParams();
  const [crop, setCrop] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const loadCrop = useCallback(async () => {
    try {
      const result = await getCropById(cropId);
      setError("");
      setCrop(result);
    }
    catch (loadError) { setError(loadError.message); }
    finally { setLoading(false); }
  }, [cropId]);
  useEffect(() => { queueMicrotask(() => { void loadCrop(); }); }, [loadCrop]);

  return (
    <div className="space-y-5">
      <div className="flex items-center justify-between gap-3">
        <h1 className="text-2xl font-bold text-green-950">Crop details</h1>
        {crop && (
          <Link
            to={`/crops/${cropId}/edit`}
            className="rounded-lg bg-green-700 px-4 py-2 text-white"
          >
            Edit crop
          </Link>
        )}
      </div>
      <ErrorMessage message={error} onRetry={loadCrop} />
      {loading ? (
        <Loading />
      ) : (
        crop && (
          <section className="rounded-2xl border border-green-100 bg-white p-6 shadow-sm">
            <h2 className="text-xl font-bold text-green-900">{crop.cropName}</h2>
            <dl className="mt-5 grid gap-4 sm:grid-cols-2">
              {[
                ["Farm ID", crop.farmId],
                ["Season", crop.season],
                ["Status", crop.status],
                ["Sowing date", crop.sowingDate],
                ["Expected harvest", crop.expectedHarvestDate],
                ["Description", crop.description],
              ].map(([label, value]) => (
                <div key={label}>
                  <dt className="text-sm text-gray-500">{label}</dt>
                  <dd className="font-medium text-gray-800">{value || "—"}</dd>
                </div>
              ))}
            </dl>
          </section>
        )
      )}
    </div>
  );
}

export default CropDetails;
