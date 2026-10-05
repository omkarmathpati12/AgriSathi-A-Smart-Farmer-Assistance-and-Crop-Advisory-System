import { useCallback, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import CropForm from "../../components/CropForm";
import ErrorMessage from "../../components/ErrorMessage";
import Loading from "../../components/Loading";
import { getLoggedInUser } from "../../services/authService";
import { getCropById, updateCrop } from "../../services/cropService";
import { getFarmsByUser } from "../../services/farmService";

function EditCrop() {
  const { cropId } = useParams();
  const navigate = useNavigate();
  const [crop, setCrop] = useState(null);
  const [farms, setFarms] = useState([]);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  const loadData = useCallback(async () => {
    try {
      const user = await getLoggedInUser();
      const [cropData, userFarms] = await Promise.all([
        getCropById(cropId),
        getFarmsByUser(user.authId),
      ]);
      setCrop(cropData);
      setFarms(userFarms);
      setError("");
    } catch (loadError) {
      setError(loadError.message);
    } finally {
      setLoading(false);
    }
  }, [cropId]);

  useEffect(() => {
    queueMicrotask(() => {
      void loadData();
    });
  }, [loadData]);

  async function handleSubmit(values) {
    setSaving(true);
    setError("");
    try {
      await updateCrop(cropId, values);
      navigate(`/crops/${cropId}`);
    } catch (saveError) {
      setError(saveError.message);
    } finally {
      setSaving(false);
    }
  }

  return (
    <div className="mx-auto max-w-2xl space-y-4">
      <h1 className="text-2xl font-bold text-green-950">Edit crop</h1>
      <ErrorMessage message={error} />
      {loading ? (
        <Loading />
      ) : crop && farms.length ? (
        <CropForm
          farms={farms}
          initialValues={crop}
          onSubmit={handleSubmit}
          submitLabel="Save changes"
          loading={saving}
        />
      ) : (
        !error && (
          <p className="rounded-xl bg-white p-5 text-gray-600">
            The crop or its farm could not be found.
          </p>
        )
      )}
    </div>
  );
}

export default EditCrop;
