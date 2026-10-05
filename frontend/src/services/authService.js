import { apiRequest, getTokenPayload, protectedApiRequest } from "./api";

export async function register(userData) {
  const user = await apiRequest("/auth/register", {
    method: "POST",
    body: userData,
  });

  if (user?.token) {
    localStorage.setItem("token", user.token);
  }

  return user;
}

export async function login(credentials) {
  const user = await apiRequest("/auth/login", {
    method: "POST",
    body: credentials,
  });

  if (!user?.token) {
    throw new Error("The login response did not include a token.");
  }

  localStorage.setItem("token", user.token);
  return user;
}

export function logout() {
  localStorage.removeItem("token");
}

export function getUserById(authId) {
  return protectedApiRequest(`/auth/${authId}`);
}

export function getUserByEmail(email) {
  return protectedApiRequest(`/auth/email/${encodeURIComponent(email)}`);
}

export function getLoggedInUser() {
  const payload = getTokenPayload();

  if (typeof payload.sub !== "string" || !payload.sub) {
    throw new Error("Your login token does not include a user email.");
  }

  return getUserByEmail(payload.sub);
}

export function getUsers() {
  return protectedApiRequest("/auth");
}

export function updateUser(authId, userData) {
  return protectedApiRequest(`/auth/${authId}`, {
    method: "PUT",
    body: userData,
  });
}

export function deleteUser(authId) {
  return protectedApiRequest(`/auth/${authId}`, { method: "DELETE" });
}

export function activateUser(authId) {
  return protectedApiRequest(`/auth/${authId}/activate`, { method: "PUT" });
}

export function deactivateUser(authId) {
  return protectedApiRequest(`/auth/${authId}/deactivate`, { method: "PUT" });
}

export function updateUserRole(authId, role) {
  const query = new URLSearchParams({ role });
  return protectedApiRequest(`/auth/${authId}/role?${query}`, {
    method: "PUT",
  });
}
