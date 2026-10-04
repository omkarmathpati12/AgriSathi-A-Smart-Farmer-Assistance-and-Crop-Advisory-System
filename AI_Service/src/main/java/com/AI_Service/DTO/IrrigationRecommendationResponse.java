package com.AI_Service.DTO;

/**
 * Response DTO for Irrigation Recommendation.
 * Output: YES, NO, or WATER SOON with clear scheduling.
 */
public record IrrigationRecommendationResponse(
        String cropName,
        String needsWatering,        // "YES", "NO", or "WATER SOON"
        String wateringSchedule,     // e.g., "Water today evening (5 PM - 7 PM)"
        String recommendedAmount,    // e.g., "Moderate irrigation (25-30 mm)"
        String reason,               // e.g., "Soil moisture is below critical threshold"
        String waterSavingTip        // e.g., "Use drip irrigation to reduce evaporation loss"
) {
}
