import { useCallback, useEffect, useState } from "react";
import ErrorMessage from "../../components/ErrorMessage";
import Loading from "../../components/Loading";
import { getLoggedInUser } from "../../services/authService";
import { getFarmsByUser } from "../../services/farmService";
import { getCurrentWeather, getWeatherForecast, getWeatherHistory } from "../../services/weatherService";

function Weather() {
  const [farms, setFarms] = useState([]);
  const [farmId, setFarmId] = useState("");
  const [current, setCurrent] = useState(null);
  const [forecast, setForecast] = useState([]);
  const [history, setHistory] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [requesting, setRequesting] = useState(false);

  const loadWeather = useCallback(async (selectedId) => {
    if (!selectedId) return;
    setRequesting(true); setError("");
    try {
      const [now, next, past] = await Promise.all([
        getCurrentWeather(selectedId),
        getWeatherForecast(selectedId),
        getWeatherHistory(selectedId),
      ]);
      setCurrent(now);
      setForecast(next);
      setHistory(past);
    } catch (loadError) { setError(loadError.message); }
    finally { setRequesting(false); setLoading(false); }
  }, []);

  useEffect(() => {
    let active = true;
    async function loadFarms() {
      try {
        const user = await getLoggedInUser();
        const userFarms = await getFarmsByUser(user.authId);
        if (!active) return;
        setFarms(userFarms);
        if (userFarms.length) {
          const firstFarmId = String(userFarms[0].farmId);
          setFarmId(firstFarmId);
          await loadWeather(firstFarmId);
        } else {
          setLoading(false);
        }
      } catch (loadError) {
        if (active) { setError(loadError.message); setLoading(false); }
      }
    }
    loadFarms();
    return () => { active = false; };
  }, [loadWeather]);

  async function handleFarmChange(event) {
    const nextFarmId = event.target.value;
    setFarmId(nextFarmId);
    await loadWeather(nextFarmId);
  }

  const weatherCard = (item, key) => (
    <article key={key} className="rounded-xl bg-green-50 p-4">
      <p className="font-semibold text-green-950">{item.condition || "Weather"}</p>
      <p className="mt-1 text-sm text-gray-600">{item.weatherDate || "—"}</p>
      <p className="mt-2 text-sm text-gray-700">Temperature: {item.temperature ?? "—"}°C · Humidity: {item.humidity ?? "—"}%</p>
      <p className="mt-1 text-sm text-gray-700">Rain probability: {item.rainProbability ?? "—"}% · Wind: {item.windSpeed ?? "—"}</p>
    </article>
  );

  return <div className="space-y-5">
    <div><h1 className="text-2xl font-bold text-green-950">Farm weather</h1><p className="mt-1 text-sm text-gray-600">Current conditions and forecast for your farm.</p></div>
    <ErrorMessage message={error} onRetry={() => loadWeather(farmId)} />
    {loading ? <Loading message="Loading weather..." /> : farms.length ? <>
      <div className="flex flex-wrap items-end gap-3 rounded-2xl border border-green-100 bg-white p-4">
        <label className="min-w-52 flex-1 text-sm font-medium text-gray-700">Choose farm
          <select value={farmId} onChange={handleFarmChange} className="mt-1 w-full rounded-lg border border-gray-300 px-3 py-2">{farms.map((farm) => <option key={farm.farmId} value={farm.farmId}>{farm.farmName}</option>)}</select>
        </label>
        <button disabled={requesting} onClick={() => loadWeather(farmId)} className="rounded-lg bg-green-700 px-4 py-2.5 font-semibold text-white disabled:opacity-60">{requesting ? "Refreshing..." : "Refresh weather"}</button>
      </div>
      {current && <section className="rounded-2xl border border-green-100 bg-white p-5 shadow-sm"><h2 className="text-lg font-bold text-green-900">Current conditions · {current.condition}</h2><div className="mt-4 grid gap-3 sm:grid-cols-3"><div className="rounded-xl bg-green-50 p-4"><p className="text-sm text-gray-500">Temperature</p><p className="text-xl font-bold">{current.temperature ?? "—"}°C</p></div><div className="rounded-xl bg-green-50 p-4"><p className="text-sm text-gray-500">Humidity</p><p className="text-xl font-bold">{current.humidity ?? "—"}%</p></div><div className="rounded-xl bg-green-50 p-4"><p className="text-sm text-gray-500">Rain probability</p><p className="text-xl font-bold">{current.rainProbability ?? "—"}%</p></div></div></section>}
      <section className="space-y-3 rounded-2xl border border-green-100 bg-white p-5 shadow-sm"><h2 className="text-lg font-bold text-green-900">Forecast</h2>{forecast.length ? forecast.map((item, index) => weatherCard(item, `${item.weatherDate}-${index}`)) : <p className="text-sm text-gray-600">No forecast records available.</p>}</section>
      <section className="space-y-3 rounded-2xl border border-green-100 bg-white p-5 shadow-sm"><h2 className="text-lg font-bold text-green-900">Recent weather records</h2>{history.length ? history.slice(0, 5).map((item, index) => weatherCard(item, `${item.weatherId || item.weatherDate}-${index}`)) : <p className="text-sm text-gray-600">No past weather records available.</p>}</section>
    </> : <p className="rounded-xl bg-white p-5 text-gray-600">Add a farm to view its weather.</p>}
  </div>;
}

export default Weather;
