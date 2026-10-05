import { useState } from "react";
import { useNavigate } from "react-router-dom";
import ErrorMessage from "../../components/ErrorMessage";
import FarmForm from "../../components/FarmForm";
import { getLoggedInUser } from "../../services/authService";
import { addFarm } from "../../services/farmService";

function AddFarm() {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  async function handleSubmit(farmValues) {
    setLoading(true);
    setError("");
    try {
      const user = await getLoggedInUser();
      await addFarm({ ...farmValues, authId: user.authId });
      navigate("/farms");
    } catch (submitError) {
      setError(submitError.message);
    } finally {
      setLoading(false);
    }
  }

  return <div className="mx-auto max-w-2xl space-y-4"><h1 className="text-2xl font-bold text-green-950">Add a farm</h1><ErrorMessage message={error} /><FarmForm initialValues={{}} onSubmit={handleSubmit} submitLabel="Save farm" loading={loading} /></div>;
}

export default AddFarm;
