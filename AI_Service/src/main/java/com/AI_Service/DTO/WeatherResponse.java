package com.AI_Service.DTO;

import java.time.LocalDateTime;

public record WeatherResponse(
        Long weatherId,
        Long farmId,
        Double temperature,
        Double humidity,
        Double rainProbability,
        String condition,
        Double windSpeed,
        LocalDateTime weatherDate,
        LocalDateTime createdAt
) {
}