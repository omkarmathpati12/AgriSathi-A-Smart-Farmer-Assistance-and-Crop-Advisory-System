import { useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import CropForm from "../../components/CropForm";
import ErrorMessage from "../../components/ErrorMessage";
import Loading from "../../components/Loading";
import { getLoggedInUser } from "../../services/authService";
import { addCrop } from "../../services/cropService";
import { getFarmsByUser } from "../../services/farmService";

function AddCrop() {
  const navigate = useNavigate();
  const [farms, setFarms] = useState([]);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");
  const loadFarms = useCallback(async () => {
    try {
      const user = await getLoggedInUser();
      const result = await getFarmsByUser(user.authId);
      setError("");
      setFarms(result);
    } catch (loadError) { setError(loadError.message); }
    finally { setLoading(false); }
  }, []);
  useEffect(() => { queueMicrotask(() => { void loadFarms(); }); }, [loadFarms]);
  async function handleSubmit(data) {
    setSaving(true); setError("");
    try { await addCrop(data); navigate("/crops"); }
    catch (saveError) { setError(saveError.message); }
    finally { setSaving(false); }
  }

  return <div className="mx-auto max-w-2xl space-y-4"><h1 className="text-2xl font-bold text-green-950">Add a crop</h1><ErrorMessage message={error} onRetry={loadFarms} />{loading ? <Loading /> : farms.length ? <CropForm farms={farms} onSubmit={handleSubmit} submitLabel="Save crop" loading={saving} /> : <p className="rounded-xl bg-white p-5 text-gray-600">Add a farm before adding a crop.</p>}</div>;
}

export default AddCrop;
