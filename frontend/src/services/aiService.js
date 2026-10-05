import { protectedApiRequest } from "./api";

export function analyzeCropHealth(analysisData) {
  return protectedApiRequest("/ai/crop-health/analyze", {
    method: "POST",
    body: analysisData,
  });
}

export function analyzeFarmCropHealth(farmId) {
  return protectedApiRequest(`/ai/crop-health/${farmId}`, { method: "POST" });
}

export function getCropRecommendations(recommendationData) {
  return protectedApiRequest("/ai/crop-recommendation", {
    method: "POST",
    body: recommendationData,
  });
}

export function analyzeDisease(diseaseData) {
  return protectedApiRequest("/ai/disease-detection/analyze", {
    method: "POST",
    body: diseaseData,
  });
}

export function analyzeDiseaseImage(file, cropName) {
  const formData = new FormData();
  formData.append("file", file);

  if (cropName) {
    formData.append("cropName", cropName);
  }

  return protectedApiRequest("/ai/disease-detection/upload", {
    method: "POST",
    formData,
  });
}

export function getFertilizerRecommendation(recommendationData) {
  return protectedApiRequest("/ai/fertilizer-recommendation", {
    method: "POST",
    body: recommendationData,
  });
}

export function getIrrigationRecommendation(recommendationData) {
  return protectedApiRequest("/ai/irrigation-recommendation", {
    method: "POST",
    body: recommendationData,
  });
}

export function predictYield(predictionData) {
  return protectedApiRequest("/ai/yield-prediction", {
    method: "POST",
    body: predictionData,
  });
}

export function getWeatherAdvice(adviceData) {
  return protectedApiRequest("/ai/weather-advice", {
    method: "POST",
    body: adviceData,
  });
}

export function getWeatherAdviceForFarm(farmId) {
  return protectedApiRequest(`/ai/weather-advice/${farmId}`);
}

export function getFarmHealthSummary(farmId) {
  return protectedApiRequest(`/ai/farm-health/${farmId}`);
}

export function askFarmerAssistant(questionData) {
  return protectedApiRequest("/ai/assistant", {
    method: "POST",
    body: questionData,
  });
}
