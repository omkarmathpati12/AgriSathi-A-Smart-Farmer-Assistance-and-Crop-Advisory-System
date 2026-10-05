import { useState } from "react";
import ErrorMessage from "../../components/ErrorMessage";
import { analyzeDisease, analyzeDiseaseImage } from "../../services/aiService";

function DiseaseDetection() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [result, setResult] = useState(null);
  const [mode, setMode] = useState("symptoms");

  async function handleSubmit(event) {
    event.preventDefault();
    const form = event.currentTarget;
    const values = new FormData(form);
    setLoading(true); setError(""); setResult(null);
    try {
      if (mode === "image") {
        const file = values.get("file");
        if (!(file instanceof File) || !file.size) throw new Error("Choose a leaf image first.");
        setResult(await analyzeDiseaseImage(file, values.get("cropName")));
      } else {
        setResult(await analyzeDisease({
          cropName: values.get("cropName"),
          symptoms: values.get("symptoms"),
          imageUrl: values.get("imageUrl") || null,
        }));
      }
    } catch (requestError) { setError(requestError.message); }
    finally { setLoading(false); }
  }

  const fieldClass = "mt-1 w-full rounded-lg border border-gray-300 px-3 py-2";
  return <div className="mx-auto max-w-2xl space-y-5"><div><h1 className="text-2xl font-bold text-green-950">Crop disease detection</h1><p className="mt-1 text-sm text-gray-600">Describe symptoms or upload a crop image for analysis.</p></div><div className="flex gap-2"><button onClick={() => setMode("symptoms")} className={`rounded-lg px-4 py-2 ${mode === "symptoms" ? "bg-green-700 text-white" : "bg-white text-gray-700"}`}>Describe symptoms</button><button onClick={() => setMode("image")} className={`rounded-lg px-4 py-2 ${mode === "image" ? "bg-green-700 text-white" : "bg-white text-gray-700"}`}>Upload image</button></div><ErrorMessage message={error} /><form onSubmit={handleSubmit} className="space-y-4 rounded-2xl border border-green-100 bg-white p-5 shadow-sm">
    <label className="block text-sm font-medium text-gray-700">Crop name<input name="cropName" required className={fieldClass} /></label>
    {mode === "symptoms" ? <><label className="block text-sm font-medium text-gray-700">Symptoms<textarea name="symptoms" rows="4" required placeholder="Describe what you see on the plant" className={fieldClass} /></label><label className="block text-sm font-medium text-gray-700">Image URL (optional)<input name="imageUrl" type="url" className={fieldClass} /></label></> : <label className="block text-sm font-medium text-gray-700">Leaf image<input name="file" type="file" accept="image/*" required className={fieldClass} /></label>}
    <button disabled={loading} className="rounded-lg bg-green-700 px-5 py-2.5 font-semibold text-white disabled:opacity-60">{loading ? "Analyzing..." : "Analyze crop"}</button>
  </form>
  {result && <section className="space-y-2 rounded-2xl border border-green-100 bg-white p-5 shadow-sm"><h2 className="text-lg font-bold text-green-900">Analysis result</h2><p><strong>Crop:</strong> {result.cropName || "—"}</p><p><strong>Status:</strong> {result.status || "—"} · <strong>Detected disease:</strong> {result.detectedDisease || "—"}</p><p><strong>Confidence:</strong> {result.confidence || "—"}</p><p>{result.symptomsSummary}</p><p><strong>Causes:</strong> {result.causes || "—"}</p><p><strong>Chemical treatment:</strong> {result.chemicalTreatment || "—"}</p><p><strong>Organic treatment:</strong> {result.organicTreatment || "—"}</p><p><strong>Prevention:</strong> {result.preventionAdvice || "—"}</p></section>}
  </div>;
}

export default DiseaseDetection;
