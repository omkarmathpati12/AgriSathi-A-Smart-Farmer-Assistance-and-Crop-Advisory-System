const API_BASE_URL = "http://localhost:8080";

export class AuthenticationError extends Error {
  constructor(message) {
    super(message);
    this.name = "AuthenticationError";
  }
}

function rejectAuthentication(message, redirect) {
  if (redirect) {
    localStorage.removeItem("token");

    if (window.location.pathname !== "/login") {
      window.location.assign("/login");
    }
  }

  throw new AuthenticationError(message);
}

export function getTokenPayload({ redirect = true } = {}) {
  const token = localStorage.getItem("token");

  if (!token) {
    rejectAuthentication("Please log in to continue.", redirect);
  }

  const payloadPart = token.split(".")[1];

  if (!payloadPart) {
    rejectAuthentication(
      "Your login token is invalid. Please log in again.",
      redirect,
    );
  }

  let payload;

  try {
    const base64 = payloadPart.replace(/-/g, "+").replace(/_/g, "/");
    const paddedBase64 = base64.padEnd(Math.ceil(base64.length / 4) * 4, "=");
    payload = JSON.parse(atob(paddedBase64));
  } catch {
    rejectAuthentication(
      "Your login token is invalid. Please log in again.",
      redirect,
    );
  }

  if (
    !payload ||
    typeof payload !== "object" ||
    typeof payload.exp !== "number" ||
    payload.exp * 1000 <= Date.now()
  ) {
    rejectAuthentication(
      "Your session has expired. Please log in again.",
      redirect,
    );
  }

  return payload;
}

async function readResponse(response) {
  if (response.status === 204) {
    return null;
  }

  const responseText = await response.text();

  if (!responseText) {
    return null;
  }

  try {
    return JSON.parse(responseText);
  } catch {
    return responseText;
  }
}

function getErrorMessage(data, response) {
  if (typeof data === "string" && data) {
    return data;
  }

  return data?.message || data?.detail || response.statusText;
}

export async function apiRequest(path, { method = "GET", body } = {}) {
  const headers = {};
  const options = { method, headers };

  if (body !== undefined) {
    headers["Content-Type"] = "application/json";
    options.body = JSON.stringify(body);
  }

  return sendRequest(path, options, false);
}

export async function protectedApiRequest(
  path,
  { method = "GET", body, formData } = {},
) {
  getTokenPayload();
  const headers = { Authorization: `Bearer ${localStorage.getItem("token")}` };
  const options = { method, headers };

  if (formData) {
    options.body = formData;
  } else if (body !== undefined) {
    headers["Content-Type"] = "application/json";
    options.body = JSON.stringify(body);
  }

  return sendRequest(path, options, true);
}

async function sendRequest(path, options, isProtected) {
  let response;

  try {
    response = await fetch(`${API_BASE_URL}${path}`, options);
  } catch (error) {
    throw new Error("Unable to connect to the server. Please try again.", {
      cause: error,
    });
  }

  if (isProtected && response.status === 401) {
    rejectAuthentication(
      "Your session has expired. Please log in again.",
      true,
    );
  }

  const data = await readResponse(response);

  if (!response.ok) {
    throw new Error(
      `Request failed (${response.status}): ${getErrorMessage(data, response)}`,
    );
  }

  return data;
}
