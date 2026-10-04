package com.AI_Service.DTO;

import com.AI_Service.Enums.RiskLevel;

import java.util.List;

public record WeatherAdviceResponse(
        Long farmId,
        RiskLevel riskLevel,
        String advice,
        List<String> recommendations,
        Double temperature,
        Double humidity,
        Double rainProbability,
        Double windSpeed,
        String weatherCondition
) {
}