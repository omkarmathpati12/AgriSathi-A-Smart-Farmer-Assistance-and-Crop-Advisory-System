# AgriSathi — API Endpoints & Architecture Reference

All client requests should be routed through the **API Gateway** on port `8080`.

- **Base Gateway URL:** `http://localhost:8080`
- **Eureka Service Registry:** `http://localhost:8761`

---

## Architecture & JWT Authentication

### How Authentication Works (Fresher-Friendly & Clean)
1. **User Registers or Logs in** via `POST /auth/register` or `POST /auth/login`.
2. The response includes user details along with a **JWT token**:
   ```json
   {
     "authId": 1,
     "name": "Ramesh Kumar",
     "email": "ramesh@example.com",
     "phone": "9876543210",
     "role": "USER",
     "status": "ACTIVE",
     "createdAt": "2026-10-01T10:00:00",
     "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYW1lc2hAZXhhbXBsZS5jb20iLCJyb2xlIjoiVVNFUiIsImlhdCI6MTc5MDgxNDAwMCwiZXhwIjoxNzkwOTAwNDAwfQ..."
   }
   ```
3. Pass the token in the `Authorization` header for all protected endpoints:
   ```http
   Authorization: Bearer <your_jwt_token_here>
   ```
4. **API Gateway Filter**:
   - Endpoints `/auth/login`, `/auth/register`, and `/eureka/**` are open.
   - All other routes validate the Bearer token.
   - If missing or expired, Gateway returns `401 Unauthorized`.
   - On success, Gateway forwards `X-User-Email` and `X-User-Role` headers to microservices.

---

## 1. Auth Service (`/auth`) — Port 8081

| Method | Endpoint | Request Body | Description |
|--------|----------|--------------|-------------|
| `POST` | `/auth/register` | `{ "name": "...", "email": "...", "password": "...", "phone": "..." }` | Register new user. Returns user + JWT token |
| `POST` | `/auth/login` | `{ "email": "...", "password": "..." }` | Authenticate user. Returns user + JWT token |
| `GET` | `/auth/{authId}` | — | Get user details by Auth ID |
| `GET` | `/auth/email/{email}` | — | Get user details by Email address |
| `GET` | `/auth` | — | Get all registered users |
| `PUT` | `/auth/{authId}` | `{ "name": "...", "email": "...", "password": "...", "phone": "..." }` | Update user profile |
| `DELETE` | `/auth/{authId}` | — | Delete user account |
| `PUT` | `/auth/{authId}/activate` | — | Activate account status |
| `PUT` | `/auth/{authId}/deactivate` | — | Deactivate account status |
| `PUT` | `/auth/{authId}/role?role=ADMIN` | Query param: `role` (`ADMIN` / `USER`) | Update user role |

---

## 2. Farm Service (`/farm`) — Port 8082

| Method | Endpoint | Request Body | Description |
|--------|----------|--------------|-------------|
| `POST` | `/farm` or `/farm/create` | `{ "farmName": "Green Valley", "authId": 1, "latitude": 18.5204, "longitude": 73.8567, "totalArea": 5.5, "soilType": "BLACK" }` | Register a new farm |
| `GET` | `/farm/{farmId}` | — | Get farm details by ID |
| `GET` | `/farm` or `/farm/all` | — | List all farms |
| `GET` | `/farm/auth/{authId}` | — | List all farms belonging to a user |
| `PUT` | `/farm/{farmId}` or `/farm/update/{farmId}` | `{ "farmName": "...", "authId": 1, "latitude": 18.52, "longitude": 73.85, "totalArea": 6.0, "soilType": "BLACK" }` | Update farm details |
| `DELETE` | `/farm/{farmId}` or `/farm/delete/{farmId}` | — | Delete a farm |
| `GET` | `/farm/{farmId}/location` | — | Get farm latitude & longitude coordinates |

---

## 3. Crop Service (`/crop`) — Port 8083

| Method | Endpoint | Request Body | Description |
|--------|----------|--------------|-------------|
| `POST` | `/crop` | `{ "farmId": 1, "cropName": "WHEAT", "season": "RABI", "plantingDate": "2026-10-15", "expectedHarvestDate": "2027-03-20", "areaPlanted": 2.5 }` | Add a crop to a farm |
| `GET` | `/crop/{cropId}` | — | Get crop details by ID |
| `GET` | `/crop` | — | List all crops |
| `GET` | `/crop/farm/{farmId}` | — | List all crops for a farm |
| `GET` | `/crop/status/{status}` | — | Filter crops by status (`PLANTED`, `GROWING`, `HARVESTED`) |
| `GET` | `/crop/name/{cropName}` | — | Filter crops by crop name (`WHEAT`, `RICE`, etc.) |
| `PUT` | `/crop/{cropId}` | `{ "farmId": 1, "cropName": "...", "season": "...", "plantingDate": "...", "expectedHarvestDate": "...", "areaPlanted": 2.5 }` | Update crop |
| `DELETE` | `/crop/{cropId}` | — | Delete a crop |
| `PUT` | `/crop/{cropId}/status?status=HARVESTED` | Query param: `status` | Update crop growth stage/status |

---

## 4. Weather Service (`/weather`) — Port 8084

| Method | Endpoint | Request Body | Description |
|--------|----------|--------------|-------------|
| `GET` | `/weather/current/{farmId}` | — | Fetches real-time weather from Open-Meteo for farm GPS and stores record |
| `GET` | `/weather/forecast/{farmId}` | — | 3-day hourly weather forecast for farm |
| `GET` | `/weather/farm/{farmId}` | — | Stored weather history for farm (newest first) |
| `POST` | `/weather/refresh/{farmId}` | — | Force refreshes current weather from Open-Meteo |
| `POST` | `/weather` | `{ "farmId": 1, "temperature": 28.5, "humidity": 65.0, "rainProbability": 20.0, "condition": "PARTLY_CLOUDY", "windSpeed": 12.0 }` | Manually store a weather observation |
| `GET` | `/weather/records/{farmId}` | — | Retrieve all historical weather records for farm |

---

## 5. AI Service (`/ai`) — Port 8085

### 5.1 Crop Health Analysis
| Method | Endpoint | Request Body | Description |
|--------|----------|--------------|-------------|
| `POST` | `/ai/crop-health/analyze` | `{ "farmId": 1, "cropName": "Tomato", "soilMoisture": 35.0, "temperature": 31.0, "humidity": 70.0, "symptoms": "Yellowing leaves" }` | Direct crop health analysis |
| `POST` | `/ai/crop-health/{farmId}` | — | Auto-analyze using farm's current weather & crops |

### 5.2 Crop Recommendation
| Method | Endpoint | Request Body | Description |
|--------|----------|--------------|-------------|
| `POST` | `/ai/crop-recommendation` | `{ "farmId": 1, "soilType": "BLACK", "season": "KHARIF", "waterAvailability": "HIGH", "landAreaAcres": 3.0, "locationState": "Maharashtra" }` | Recommends best crops based on soil, season & state |

### 5.3 Disease Detection
| Method | Endpoint | Request Body / Parameters | Description |
|--------|----------|---------------------------|-------------|
| `POST` | `/ai/disease-detection/analyze` | `{ "cropName": "Potato", "symptoms": "Brown spots on lower leaves", "imageUrl": "..." }` | Disease diagnosis from symptom description |
| `POST` | `/ai/disease-detection/upload` | Multipart file: `file` + RequestParam: `cropName` | Image-based disease detection using Gemini AI |

### 5.4 Fertilizer Recommendation
| Method | Endpoint | Request Body | Description |
|--------|----------|--------------|-------------|
| `POST` | `/ai/fertilizer-recommendation` | `{ "farmId": 1, "cropName": "Cotton", "soilType": "BLACK", "soilPH": 6.8, "nitrogenLevel": 120.0, "phosphorusLevel": 45.0, "potassiumLevel": 50.0 }` | Recommends fertilizer N-P-K dosages and schedule |

### 5.5 Irrigation Recommendation
| Method | Endpoint | Request Body | Description |
|--------|----------|--------------|-------------|
| `POST` | `/ai/irrigation-recommendation` | `{ "farmId": 1, "cropName": "Sugarcane", "soilMoisture": 25.0, "temperature": 32.0, "rainfallMm": 0.0, "growthStage": "VEGETATIVE" }` | Calculates water requirements and watering schedule |

### 5.6 Yield Prediction
| Method | Endpoint | Request Body | Description |
|--------|----------|--------------|-------------|
| `POST` | `/ai/yield-prediction` | `{ "cropName": "Wheat", "landAreaAcres": 4.0, "soilType": "ALLUVIAL", "waterSource": "BOREWELL" }` | Predicts expected crop yield in quintals and estimated revenue |

### 5.7 Weather Advice
| Method | Endpoint | Request Body | Description |
|--------|----------|--------------|-------------|
| `POST` | `/ai/weather-advice` | `{ "farmId": 1, "cropName": "Rice", "temperature": 29.0, "humidity": 80.0, "rainfallMm": 15.0, "windSpeedKmh": 18.0, "weatherCondition": "RAIN" }` | Generates actionable farming advice based on weather conditions |
| `GET` | `/ai/weather-advice/{farmId}` | — | Automatically generates advice using farm's live weather data |

### 5.8 Farm Health Summary
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/ai/farm-health/{farmId}` | Comprehensive diagnostic overview aggregating crop, weather, and soil health |

### 5.9 AI Farmer Assistant (Chatbot)
| Method | Endpoint | Request Body | Description |
|--------|----------|--------------|-------------|
| `POST` | `/ai/assistant` | `{ "farmId": 1, "question": "What is the best time to sow mustard in Rajasthan?" }` | Natural-language AI farming assistant |

---

## 6. Microservices Startup Order

Start services in this sequence:
1. **ServiceDiscovery** (Eureka) — `http://localhost:8761`
2. **Auth_Service** — Port `8081`
3. **Farm_Service** — Port `8082`
4. **Crop_Service** — Port `8083`
5. **Weather_Service** — Port `8084`
6. **AI_Service** — Port `8085`
7. **APIGateway** — Port `8080`
8. **Frontend** (Vite + React) — `npm run dev` in `frontend/`

---

## 7. Sample cURL Commands

### 1. Register User
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Ramesh Kumar",
    "email": "ramesh@gmail.com",
    "password": "password123",
    "phone": "9876543210"
  }'
```

### 2. Login & Get JWT Token
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "ramesh@gmail.com",
    "password": "password123"
  }'
```

### 3. Create Farm (Authenticated)
```bash
curl -X POST http://localhost:8080/farm \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN_HERE>" \
  -d '{
    "farmName": "Ramesh Organic Farm",
    "authId": 1,
    "latitude": 18.5204,
    "longitude": 73.8567,
    "totalArea": 4.5,
    "soilType": "BLACK"
  }'
```

### 4. Get Current Weather for Farm
```bash
curl -X GET http://localhost:8080/weather/current/1 \
  -H "Authorization: Bearer <TOKEN_HERE>"
```

### 5. Ask AgriSathi AI Assistant
```bash
curl -X POST http://localhost:8080/ai/assistant \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN_HERE>" \
  -d '{
    "farmId": 1,
    "question": "How much water does wheat need in winter?"
  }'
```
