const cropNames = ["WHEAT", "RICE", "ONION", "TOMATO", "POTATO", "MAIZE", "COTTON", "SUGARCANE"];
const seasons = ["KHARIF", "RABI", "SUMMER"];
const statuses = ["PLANTED", "GROWING", "HARVESTED"];

function CropForm({ farms, initialValues = {}, onSubmit, submitLabel, loading }) {
  function handleSubmit(event) {
    event.preventDefault();
    const values = new FormData(event.currentTarget);
    onSubmit({
      farmId: Number(values.get("farmId")),
      cropName: values.get("cropName"),
      season: values.get("season"),
      sowingDate: values.get("sowingDate") || null,
      expectedHarvestDate: values.get("expectedHarvestDate") || null,
      status: values.get("status"),
      description: values.get("description"),
    });
  }

  const fieldClass =
    "mt-1 w-full rounded-lg border border-gray-300 bg-white px-3 py-2 focus:border-green-600 focus:outline-none focus:ring-2 focus:ring-green-100";

  return (
    <form onSubmit={handleSubmit} className="space-y-4 rounded-2xl border border-green-100 bg-white p-5 shadow-sm sm:p-6">
      <label className="block text-sm font-medium text-gray-700">Farm
        <select name="farmId" defaultValue={initialValues.farmId || farms[0]?.farmId || ""} required className={fieldClass}>
          {farms.map((farm) => <option key={farm.farmId} value={farm.farmId}>{farm.farmName}</option>)}
        </select>
      </label>
      <div className="grid gap-4 sm:grid-cols-2">
        <label className="block text-sm font-medium text-gray-700">Crop
          <select name="cropName" defaultValue={initialValues.cropName || cropNames[0]} className={fieldClass}>
            {cropNames.map((name) => <option key={name} value={name}>{name}</option>)}
          </select>
        </label>
        <label className="block text-sm font-medium text-gray-700">Season
          <select name="season" defaultValue={initialValues.season || seasons[0]} className={fieldClass}>
            {seasons.map((season) => <option key={season} value={season}>{season}</option>)}
          </select>
        </label>
      </div>
      <div className="grid gap-4 sm:grid-cols-2">
        <label className="block text-sm font-medium text-gray-700">Sowing date
          <input name="sowingDate" type="date" defaultValue={initialValues.sowingDate || ""} className={fieldClass} />
        </label>
        <label className="block text-sm font-medium text-gray-700">Expected harvest date
          <input name="expectedHarvestDate" type="date" defaultValue={initialValues.expectedHarvestDate || ""} className={fieldClass} />
        </label>
      </div>
      <label className="block text-sm font-medium text-gray-700">Growth status
        <select name="status" defaultValue={initialValues.status || "PLANTED"} className={fieldClass}>
          {statuses.map((status) => <option key={status} value={status}>{status}</option>)}
        </select>
      </label>
      <label className="block text-sm font-medium text-gray-700">Description
        <textarea name="description" rows="3" defaultValue={initialValues.description || ""} className={fieldClass} />
      </label>
      <button disabled={loading || farms.length === 0} className="rounded-lg bg-green-700 px-5 py-2.5 font-semibold text-white hover:bg-green-800 disabled:opacity-60">
        {loading ? "Saving..." : submitLabel}
      </button>
    </form>
  );
}

export default CropForm;
