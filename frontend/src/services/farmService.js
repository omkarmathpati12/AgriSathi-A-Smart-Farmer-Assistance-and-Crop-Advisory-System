import { protectedApiRequest } from "./api";

export function getFarms() {
  return protectedApiRequest("/farm");
}

export function getFarmById(farmId) {
  return protectedApiRequest(`/farm/${farmId}`);
}

export function getFarmsByUser(authId) {
  return protectedApiRequest(`/farm/auth/${authId}`);
}

export function getFarmLocation(farmId) {
  return protectedApiRequest(`/farm/${farmId}/location`);
}

export function addFarm(farmData) {
  return protectedApiRequest("/farm", { method: "POST", body: farmData });
}

export function updateFarm(farmId, farmData) {
  return protectedApiRequest(`/farm/${farmId}`, {
    method: "PUT",
    body: farmData,
  });
}

export function deleteFarm(farmId) {
  return protectedApiRequest(`/farm/${farmId}`, { method: "DELETE" });
}
