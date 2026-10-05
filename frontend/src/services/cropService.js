import { protectedApiRequest } from "./api";

export function getCrops() {
  return protectedApiRequest("/crop");
}

export function getCropById(cropId) {
  return protectedApiRequest(`/crop/${cropId}`);
}

export function getCropsByFarm(farmId) {
  return protectedApiRequest(`/crop/farm/${farmId}`);
}

export function getCropsByStatus(status) {
  return protectedApiRequest(`/crop/status/${encodeURIComponent(status)}`);
}

export function getCropsByName(cropName) {
  return protectedApiRequest(`/crop/name/${encodeURIComponent(cropName)}`);
}

export function addCrop(cropData) {
  return protectedApiRequest("/crop", { method: "POST", body: cropData });
}

export function updateCrop(cropId, cropData) {
  return protectedApiRequest(`/crop/${cropId}`, {
    method: "PUT",
    body: cropData,
  });
}

export function deleteCrop(cropId) {
  return protectedApiRequest(`/crop/${cropId}`, { method: "DELETE" });
}

export function updateCropStatus(cropId, status) {
  const query = new URLSearchParams({ status });
  return protectedApiRequest(`/crop/${cropId}/status?${query}`, {
    method: "PUT",
  });
}
