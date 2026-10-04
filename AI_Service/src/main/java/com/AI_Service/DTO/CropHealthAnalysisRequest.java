package com.AI_Service.DTO;

/**
 * Request payload for analyzing crop health.
 * Fresher friendly: fields can be passed directly from Postman or UI.
 */
public record CropHealthAnalysisRequest(
        Long farmId,
        String cropName,
        Integer cropAgeDays,
        Double soilMoisture,
        String leafColor,
        String pestSymptoms
) {
}
