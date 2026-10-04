package com.AI_Service.DTO;

import com.AI_Service.Enums.Suitability;

public record CropRecommendationItem(
        String crop,
        Suitability suitability,
        String reason
) {
}