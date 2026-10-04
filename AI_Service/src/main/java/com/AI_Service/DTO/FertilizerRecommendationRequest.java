package com.AI_Service.DTO;

/**
 * Request DTO for fertilizer suggestion.
 * Takes crop name and soil nutrient information.
 */
public record FertilizerRecommendationRequest(
        Long farmId,
        String cropName,
        String soilType,
        Double nitrogen,   // N level
        Double phosphorus, // P level
        Double potassium,  // K level
        Double soilPh
) {
}
