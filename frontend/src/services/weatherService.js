import { protectedApiRequest } from "./api";

export function getCurrentWeather(farmId) {
  return protectedApiRequest(`/weather/current/${farmId}`);
}

export function getWeatherForecast(farmId) {
  return protectedApiRequest(`/weather/forecast/${farmId}`);
}

export function getWeatherHistory(farmId) {
  return protectedApiRequest(`/weather/farm/${farmId}`);
}

export function getWeatherRecords(farmId) {
  return protectedApiRequest(`/weather/records/${farmId}`);
}

export function refreshWeather(farmId) {
  return protectedApiRequest(`/weather/refresh/${farmId}`, { method: "POST" });
}

export function addWeatherRecord(weatherData) {
  return protectedApiRequest("/weather", {
    method: "POST",
    body: weatherData,
  });
}
