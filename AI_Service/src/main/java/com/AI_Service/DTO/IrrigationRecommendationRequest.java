package com.AI_Service.DTO;

/**
 * Request DTO for Irrigation Recommendation.
 * Asks whether the crop needs watering based on soil moisture and climate.
 */
public record IrrigationRecommendationRequest(
        Long farmId,
        String cropName,
        Double soilMoisturePercent,
        Double temperature,
        String soilType,
        Boolean rainForecast,
        Integer daysSinceLastWatering
) {
}
