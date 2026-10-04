package com.AI_Service.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CropResponse(
        Long cropId,
        Long farmId,
        String cropName,
        String season,
        LocalDate sowingDate,
        LocalDate expectedHarvestDate,
        String status,
        String description,
        LocalDateTime createdAt
) {
}