package com.AI_Service.DTO;

import java.util.List;

/**
 * Response DTO for Crop Yield Prediction.
 * Freshers can view estimated harvest in Quintals and key tips.
 */
public record YieldPredictionResponse(
        String cropName,
        Double landAreaAcres,
        String expectedYieldPerAcre,
        String totalExpectedYield,
        String yieldRating, // "HIGH", "AVERAGE", "POOR"
        String estimatedMarketValue,
        List<String> keyInfluencingFactors,
        List<String> tipsToIncreaseYield
) {
}
