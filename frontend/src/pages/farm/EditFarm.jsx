import { useCallback, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import ErrorMessage from "../../components/ErrorMessage";
import FarmForm from "../../components/FarmForm";
import Loading from "../../components/Loading";
import { updateFarm, getFarmById } from "../../services/farmService";

function EditFarm() {
  const { farmId } = useParams();
  const navigate = useNavigate();
  const [farm, setFarm] = useState(null);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  const loadFarm = useCallback(async () => {
    try {
      const result = await getFarmById(farmId);
      setError("");
      setFarm(result);
    } catch (loadError) {
      setError(loadError.message);
    } finally {
      setLoading(false);
    }
  }, [farmId]);

  useEffect(() => { queueMicrotask(() => { void loadFarm(); }); }, [loadFarm]);

  async function handleSubmit(values) {
    setSaving(true);
    setError("");
    try {
      await updateFarm(farmId, { ...values, authId: farm.authId });
      navigate(`/farms/${farmId}`);
    } catch (saveError) {
      setError(saveError.message);
    } finally {
      setSaving(false);
    }
  }

  return <div className="mx-auto max-w-2xl space-y-4"><h1 className="text-2xl font-bold text-green-950">Edit farm</h1><ErrorMessage message={error} onRetry={loadFarm} />{loading ? <Loading /> : farm && <FarmForm initialValues={farm} onSubmit={handleSubmit} submitLabel="Save changes" loading={saving} />}</div>;
}

export default EditFarm;
