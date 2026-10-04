package com.AI_Service.DTO;

import com.AI_Service.Enums.HealthStatus;
import com.AI_Service.Enums.IrrigationStatus;
import com.AI_Service.Enums.RiskLevel;

import java.util.List;

public record FarmHealthResponse(
        Long farmId,
        HealthStatus overallStatus,
        HealthStatus cropHealth,
        RiskLevel weatherRisk,
        IrrigationStatus irrigationStatus,
        String summary,
        List<String> recommendations
) {
}