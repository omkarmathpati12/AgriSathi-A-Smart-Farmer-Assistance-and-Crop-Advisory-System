package com.AI_Service.DTO;

import com.AI_Service.Enums.Season;

import java.util.List;

public record CropRecommendationResponse(
        Long farmId,
        Season season,
        List<CropRecommendationItem> recommendations
) {
}