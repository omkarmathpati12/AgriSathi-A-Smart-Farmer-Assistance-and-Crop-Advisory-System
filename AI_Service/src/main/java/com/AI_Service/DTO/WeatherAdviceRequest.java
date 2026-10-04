package com.AI_Service.DTO;

/**
 * Request DTO for generating simple weather-based crop advice.
 * Allows passing either farmId or custom weather values.
 */
public record WeatherAdviceRequest(
        Long farmId,
        String cropName,
        Double temperature,
        Double humidity,
        Double rainfallMm,
        Double windSpeedKmh,
        String weatherCondition // e.g. "Sunny", "Rainy", "Humid", "Cloudy"
) {
}
