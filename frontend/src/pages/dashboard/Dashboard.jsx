import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getLoggedInUser, logout } from "../../services/authService";
import { getCropsByFarm } from "../../services/cropService";
import { getFarmsByUser } from "../../services/farmService";
import { getCurrentWeather } from "../../services/weatherService";
import { getWeatherAdviceForFarm } from "../../services/aiService";

function SummaryCard({ icon, label, value }) {
  return (
    <div className="rounded-2xl border border-green-100 bg-white p-5 shadow-sm">
      <div className="flex items-center gap-3">
        <span className="flex h-11 w-11 items-center justify-center rounded-xl bg-green-50 text-2xl">
          {icon}
        </span>
        <div>
          <p className="text-sm text-gray-500">{label}</p>
          <p className="text-xl font-bold text-green-900">{value}</p>
        </div>
      </div>
    </div>
  );
}

function Section({ title, icon, children }) {
  return (
    <section className="rounded-2xl border border-green-100 bg-white p-5 shadow-sm sm:p-6">
      <h2 className="mb-4 flex items-center gap-2 text-lg font-bold text-green-900">
        <span aria-hidden="true">{icon}</span>
        {title}
      </h2>
      {children}
    </section>
  );
}

function Dashboard() {
  const navigate = useNavigate();
  const [dashboard, setDashboard] = useState({
    user: null,
    farms: [],
    crops: [],
    weather: [],
    advisories: [],
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [advisoryError, setAdvisoryError] = useState("");

  useEffect(() => {
    let isCurrent = true;

    async function loadDashboard() {
      setLoading(true);
      setError("");
      setAdvisoryError("");
      try {
        const user = await getLoggedInUser();
        const farms = await getFarmsByUser(user.authId);

        const [cropLists, weather, advisoryResults] = await Promise.all([
          Promise.all(farms.map((farm) => getCropsByFarm(farm.farmId))),
          Promise.all(farms.map((farm) => getCurrentWeather(farm.farmId))),
          Promise.allSettled(
            farms.map((farm) => getWeatherAdviceForFarm(farm.farmId)),
          ),
        ]);

        if (isCurrent) {
          const advisories = advisoryResults
            .filter((result) => result.status === "fulfilled")
            .map((result) => result.value);
          const advisoryFailures = advisoryResults
            .filter((result) => result.status === "rejected")
            .map((result) => result.reason.message);

          setDashboard({
            user,
            farms,
            crops: cropLists.flat(),
            weather,
            advisories,
          });
          if (advisoryFailures.length) {
            setAdvisoryError(advisoryFailures.join(" "));
          }
        }
      } catch (loadError) {
        if (isCurrent) {
          setError(loadError.message);
        }
      } finally {
        if (isCurrent) {
          setLoading(false);
        }
      }
    }

    loadDashboard();

    return () => {
      isCurrent = false;
    };
  }, []);

  function handleLogout() {
    logout();
    navigate("/login", { replace: true });
  }

  const currentWeather = dashboard.weather[0];

  return (
    <main className="min-h-screen bg-green-50/70 px-4 py-6 text-gray-800 sm:px-6 lg:px-8">
      <div className="mx-auto max-w-7xl">
        <header className="mb-8 flex flex-wrap items-center justify-between gap-4">
          <div>
            <p className="text-sm font-semibold uppercase tracking-wide text-green-700">
              AgriSathi
            </p>
            <h1 className="mt-1 text-2xl font-bold text-green-950 sm:text-3xl">
              Welcome to AgriSathi 🌱
            </h1>
            <p className="mt-2 text-gray-600">
              {dashboard.user?.name
                ? `Good to see you, ${dashboard.user.name}.`
                : "Your farms and crop information at a glance."}
            </p>
          </div>
          <button
            type="button"
            onClick={handleLogout}
            className="rounded-xl border border-green-200 bg-white px-4 py-2.5 font-semibold text-green-800 transition hover:bg-green-100 focus:outline-none focus:ring-2 focus:ring-green-600 focus:ring-offset-2"
          >
            Log out
          </button>
        </header>

        {error && (
          <div
            role="alert"
            className="mb-6 flex flex-wrap items-center justify-between gap-3 rounded-xl border border-red-200 bg-red-50 p-4 text-sm text-red-800"
          >
            <span>We couldn&apos;t load your dashboard: {error}</span>
            <button
              type="button"
              onClick={() => window.location.reload()}
              className="font-semibold underline"
            >
              Try again
            </button>
          </div>
        )}

        {loading ? (
          <p className="rounded-2xl bg-white p-6 text-center text-green-800 shadow-sm">
            Loading your farm information...
          </p>
        ) : !error ? (
          <>
            <div className="mb-8 grid grid-cols-1 gap-4 sm:grid-cols-2 xl:grid-cols-4">
              <SummaryCard
                icon="🚜"
                label="Total Farms"
                value={dashboard.farms.length}
              />
              <SummaryCard
                icon="🌾"
                label="Total Crops"
                value={dashboard.crops.length}
              />
              <SummaryCard
                icon="☀️"
                label="Current Weather"
                value={currentWeather?.condition || "Not available"}
              />
              <SummaryCard
                icon="💡"
                label="Crop Advisory"
                value={dashboard.advisories.length ? "Available" : "—"}
              />
            </div>

            <div className="grid grid-cols-1 gap-6 lg:grid-cols-2">
              <Section title="My Farms" icon="🌱">
                {dashboard.farms.length ? (
                  <div className="grid gap-3 sm:grid-cols-2">
                    {dashboard.farms.map((farm) => (
                      <article
                        key={farm.farmId}
                        className="rounded-xl bg-green-50 p-4"
                      >
                        <h3 className="font-bold text-green-950">
                          {farm.farmName}
                        </h3>
                        <p className="mt-2 text-sm text-gray-600">
                          📍 {farm.address || "Location not provided"}
                        </p>
                        <p className="mt-1 text-sm text-gray-600">
                          Farm area: {farm.farmArea ?? "Not provided"}
                        </p>
                      </article>
                    ))}
                  </div>
                ) : (
                  <p className="text-sm text-gray-600">
                    No farms have been added yet.
                  </p>
                )}
              </Section>

              <Section title="Weather" icon="🌤️">
                {currentWeather ? (
                  <div>
                    <p className="mb-4 text-sm text-gray-600">
                      {dashboard.farms.find(
                        (farm) => farm.farmId === currentWeather.farmId,
                      )?.farmName || "Your farm"}
                    </p>
                    <div className="grid grid-cols-3 gap-3 text-center">
                      <div className="rounded-xl bg-green-50 p-3">
                        <p className="text-xs text-gray-500">Temperature</p>
                        <p className="mt-1 font-bold text-green-900">
                          {currentWeather.temperature ?? "—"}°C
                        </p>
                      </div>
                      <div className="rounded-xl bg-green-50 p-3">
                        <p className="text-xs text-gray-500">Humidity</p>
                        <p className="mt-1 font-bold text-green-900">
                          {currentWeather.humidity ?? "—"}%
                        </p>
                      </div>
                      <div className="rounded-xl bg-green-50 p-3">
                        <p className="text-xs text-gray-500">Condition</p>
                        <p className="mt-1 font-bold text-green-900">
                          {currentWeather.condition || "—"}
                        </p>
                      </div>
                    </div>
                  </div>
                ) : (
                  <p className="text-sm text-gray-600">
                    Weather information is not available yet. Add a farm to see
                    its current weather.
                  </p>
                )}
              </Section>

              <Section title="My Crops" icon="🌿">
                {dashboard.crops.length ? (
                  <div className="space-y-3">
                    {dashboard.crops.map((crop) => (
                      <article
                        key={crop.cropId}
                        className="rounded-xl bg-green-50 p-4"
                      >
                        <h3 className="font-bold text-green-950">
                          {crop.cropName}
                        </h3>
                        <p className="mt-1 text-sm text-gray-600">
                          Season: {crop.season || "Not provided"}
                          {crop.status && ` · Status: ${crop.status}`}
                        </p>
                        {crop.description && (
                          <p className="mt-2 text-sm text-gray-600">
                            {crop.description}
                          </p>
                        )}
                        {crop.expectedHarvestDate && (
                          <p className="mt-2 text-sm text-gray-600">
                            Expected harvest: {crop.expectedHarvestDate}
                          </p>
                        )}
                      </article>
                    ))}
                  </div>
                ) : (
                  <p className="text-sm text-gray-600">
                    No crops have been added to your farms yet.
                  </p>
                )}
              </Section>

              <Section title="Crop Advisory" icon="💡">
                {advisoryError && (
                  <p
                    role="alert"
                    className="mb-3 rounded-lg border border-amber-200 bg-amber-50 p-3 text-sm text-amber-900"
                  >
                    Some advisory information could not be loaded:{" "}
                    {advisoryError}
                  </p>
                )}
                {dashboard.advisories.length ? (
                  <div className="space-y-3">
                    {dashboard.advisories.map((advisory) => (
                      <article
                        key={advisory.farmId}
                        className="rounded-xl bg-amber-50 p-4 text-sm text-amber-950"
                      >
                        <p className="font-semibold">
                          {dashboard.farms.find(
                            (farm) => farm.farmId === advisory.farmId,
                          )?.farmName || "Your farm"}
                          {advisory.riskLevel &&
                            ` · ${advisory.riskLevel} risk`}
                        </p>
                        <p className="mt-1">{advisory.advice}</p>
                        {advisory.recommendations?.length > 0 && (
                          <ul className="mt-2 list-inside list-disc space-y-1">
                            {advisory.recommendations.map((recommendation) => (
                              <li key={recommendation}>{recommendation}</li>
                            ))}
                          </ul>
                        )}
                      </article>
                    ))}
                  </div>
                ) : (
                  <p className="text-sm text-gray-600">
                    {dashboard.farms.length
                      ? "No advisory information is available yet."
                      : "Add a farm to get weather-based advice."}
                  </p>
                )}
              </Section>
            </div>
          </>
        ) : null}
      </div>
    </main>
  );
}

export default Dashboard;
