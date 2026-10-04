package com.AI_Service.DTO;

/**
 * Response DTO for fertilizer advice.
 * Suggests exact fertilizer, dosage, and organic alternatives.
 */
public record FertilizerRecommendationResponse(
        String cropName,
        String recommendedFertilizer,
        String dosagePerAcre,
        String applicationTiming,
        String organicAlternative,
        String soilHealthAdvice
) {
}
