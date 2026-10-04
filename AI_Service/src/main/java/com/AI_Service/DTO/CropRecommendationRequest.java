package com.AI_Service.DTO;

import com.AI_Service.Enums.Season;

/**
 * Request payload for recommending suitable crops.
 * Can take farmId or direct farm characteristics (soil, water, season, area).
 */
public record CropRecommendationRequest(
        Long farmId,
        Season season,
        String soilType, // e.g. "Black", "Alluvial", "Red", "Sandy", "Clay", "Loamy"
        String waterAvailability, // e.g. "High", "Medium", "Low"
        Double landAreaAcres,
        String locationState) {
}
