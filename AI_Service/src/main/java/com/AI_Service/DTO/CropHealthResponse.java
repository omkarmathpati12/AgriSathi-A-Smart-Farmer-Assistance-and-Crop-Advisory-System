package com.AI_Service.DTO;

import com.AI_Service.Enums.HealthStatus;
import com.AI_Service.Enums.RiskLevel;

import java.util.List;

public record CropHealthResponse(
        String health,
        Double confidence,
        String message
) {
}