package com.AI_Service.DTO;

/**
 * Request DTO for Yield Prediction.
 * Takes farm area, crop type, and agricultural inputs.
 */
public record YieldPredictionRequest(
        Long farmId,
        String cropName,
        Double landAreaAcres,
        String soilType,
        String season,
        String waterSource,
        String fertilizerUsage
) {
}
