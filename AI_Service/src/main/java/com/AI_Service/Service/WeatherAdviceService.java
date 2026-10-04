package com.AI_Service.Service;

import com.AI_Service.DTO.AIContext;
import com.AI_Service.DTO.WeatherAdviceRequest;
import com.AI_Service.DTO.WeatherAdviceResponse;
import com.AI_Service.DTO.WeatherResponse;
import com.AI_Service.Entity.WeatherAdviceEntity;
import com.AI_Service.Enums.RiskLevel;
import com.AI_Service.Repository.WeatherAdviceRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherAdviceService {

    private final AIContextService aiContextService;
    private final GeminiService geminiService;
    private final WeatherAdviceRepo repository;

    // Generate weather-based crop advice from direct request
    public WeatherAdviceResponse generateAdvice(WeatherAdviceRequest request) {
        Long farmId = request.farmId() != null ? request.farmId() : 1L;
        String crop = (request.cropName() != null && !request.cropName().isBlank()) ? request.cropName() : "General Crop";
        double temp = request.temperature() != null ? request.temperature() : 30.0;
        double humidity = request.humidity() != null ? request.humidity() : 60.0;
        double rainProb = request.rainfallMm() != null ? (request.rainfallMm() > 10.0 ? 80.0 : 25.0) : 20.0;
        double wind = request.windSpeedKmh() != null ? request.windSpeedKmh() : 12.0;
        String condition = (request.weatherCondition() != null && !request.weatherCondition().isBlank()) ? request.weatherCondition() : "Partly Cloudy";

        String prompt = """
                You are a senior agricultural weather specialist.
                Give simple, direct farmer advice for:
                Crop: %s
                Temperature: %.1f °C
                Humidity: %.1f%%
                Rain probability: %.1f%%
                Wind speed: %.1f km/h
                Condition: %s
                """.formatted(crop, temp, humidity, rainProb, wind, condition);

        String aiResult = geminiService.ask(prompt);

        RiskLevel riskLevel = calculateRisk(rainProb, wind, temp);
        String advice;
        String recommendations;

        if (rainProb >= 70.0) {
            advice = "Heavy rain expected. Postpone pesticide sprays and clear drainage channels.";
            recommendations = "1. Stop irrigation. 2. Postpone spray. 3. Ensure drainage.";
        } else if (wind >= 25.0) {
            advice = "High winds (" + wind + " km/h). Stake tall plants and avoid chemical dusting.";
            recommendations = "1. Stake plants. 2. Postpone chemical dusting. 3. Inspect covers.";
        } else if (temp >= 36.0) {
            advice = "Heatwave (" + temp + " °C). Irrigate in early morning and apply mulch.";
            recommendations = "1. Irrigate early morning. 2. Apply mulch. 3. Avoid midday stress.";
        } else if (humidity >= 85.0) {
            advice = "High humidity (" + humidity + "%). Risk of fungal disease elevated.";
            recommendations = "1. Monitor for fungal spots. 2. Maintain clean row spacing. 3. Prepare bio-fungicide.";
        } else {
            advice = "Weather is favorable (" + temp + " °C, " + condition + "). Good for farm activities.";
            recommendations = "1. Normal irrigation. 2. Safe for fertilizer. 3. Ideal harvest conditions.";
        }

        String finalAdvice = aiResult != null ? aiResult : advice;

        // Persist
        WeatherAdviceEntity entity = new WeatherAdviceEntity();
        entity.setFarmId(farmId);
        entity.setRiskLevel(riskLevel);
        entity.setAdvice(finalAdvice);
        entity.setRecommendations(recommendations);
        entity.setTemperature(temp);
        entity.setHumidity(humidity);
        entity.setRainProbability(rainProb);
        entity.setWindSpeed(wind);
        entity.setWeatherCondition(condition);
        repository.save(entity);

        return new WeatherAdviceResponse(farmId, riskLevel, finalAdvice, List.of(recommendations.split("\\. ?")),
                temp, humidity, rainProb, wind, condition);
    }

    // Generate advice by fetching weather from the Weather microservice
    public WeatherAdviceResponse generateAdvice(Long farmId) {
        AIContext context = aiContextService.getContext(farmId);

        String cropName = "General Crop";
        if (context.crops() != null && !context.crops().isEmpty()) {
            cropName = context.crops().get(0).cropName();
        }

        WeatherAdviceRequest req;
        if (context.weather() != null && !context.weather().isEmpty()) {
            WeatherResponse w = context.weather().get(0);
            req = new WeatherAdviceRequest(farmId, cropName, w.temperature(), w.humidity(),
                    w.rainProbability(), w.windSpeed(), w.condition());
        } else {
            req = new WeatherAdviceRequest(farmId, cropName, 29.5, 58.0, 15.0, 11.0, "Clear Sky");
        }

        return generateAdvice(req);
    }

    private RiskLevel calculateRisk(double rainProb, double wind, double temp) {
        if (rainProb >= 75.0 || wind >= 35.0 || temp >= 42.0) return RiskLevel.HIGH;
        if (rainProb >= 45.0 || wind >= 20.0 || temp >= 37.0) return RiskLevel.MEDIUM;
        return RiskLevel.LOW;
    }
}