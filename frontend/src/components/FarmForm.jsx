const farmTypes = [
  "BLACK",
  "RED",
  "ALLUVIAL",
  "LATERITE",
  "DESERT",
  "MOUNTAIN",
  "SALINE",
  "PEATY",
];

function FarmForm({ initialValues, onSubmit, submitLabel, loading }) {
  function handleSubmit(event) {
    event.preventDefault();
    const values = new FormData(event.currentTarget);
    const optionalNumber = (key) => {
      const value = values.get(key);
      return value === "" ? null : Number(value);
    };

    onSubmit({
      ...initialValues,
      farmName: values.get("farmName"),
      farmArea: optionalNumber("farmArea"),
      type: values.get("type"),
      waterAvailability: values.get("waterAvailability") === "on",
      address: values.get("address"),
      latitude: optionalNumber("latitude"),
      longitude: optionalNumber("longitude"),
    });
  }

  const fieldClass =
    "mt-1 w-full rounded-lg border border-gray-300 bg-white px-3 py-2 focus:border-green-600 focus:outline-none focus:ring-2 focus:ring-green-100";

  return (
    <form onSubmit={handleSubmit} className="space-y-4 rounded-2xl border border-green-100 bg-white p-5 shadow-sm sm:p-6">
      <label className="block text-sm font-medium text-gray-700">
        Farm name
        <input name="farmName" defaultValue={initialValues.farmName || ""} required className={fieldClass} />
      </label>
      <label className="block text-sm font-medium text-gray-700">
        Area
        <input name="farmArea" type="number" min="0" step="any" defaultValue={initialValues.farmArea ?? ""} className={fieldClass} />
      </label>
      <label className="block text-sm font-medium text-gray-700">
        Soil type
        <select name="type" defaultValue={initialValues.type || "BLACK"} className={fieldClass}>
          {farmTypes.map((type) => <option key={type} value={type}>{type}</option>)}
        </select>
      </label>
      <label className="block text-sm font-medium text-gray-700">
        Address
        <input name="address" defaultValue={initialValues.address || ""} className={fieldClass} />
      </label>
      <div className="grid gap-4 sm:grid-cols-2">
        <label className="block text-sm font-medium text-gray-700">
          Latitude
          <input name="latitude" type="number" step="any" defaultValue={initialValues.latitude ?? ""} className={fieldClass} />
        </label>
        <label className="block text-sm font-medium text-gray-700">
          Longitude
          <input name="longitude" type="number" step="any" defaultValue={initialValues.longitude ?? ""} className={fieldClass} />
        </label>
      </div>
      <label className="flex items-center gap-2 text-sm text-gray-700">
        <input name="waterAvailability" type="checkbox" defaultChecked={initialValues.waterAvailability || false} className="h-4 w-4 accent-green-700" />
        Water is available on this farm
      </label>
      <button disabled={loading} className="rounded-lg bg-green-700 px-5 py-2.5 font-semibold text-white hover:bg-green-800 disabled:opacity-60">
        {loading ? "Saving..." : submitLabel}
      </button>
    </form>
  );
}

export default FarmForm;
