package com.AI_Service.DTO;

public record CropHealthRequest(
        Long farmId,
        Double farmArea,
        String farmType,
        Boolean waterAvailability
) {
}