package com.AI_Service.DTO;

import java.time.LocalDateTime;

public record FarmResponse(
        Long farmId,
        Long authId,
        String farmName,
        Double farmArea,
        String type,
        boolean waterAvailability,
        String address,
        Double latitude,
        Double longitude,
        LocalDateTime createdAt
) {
}