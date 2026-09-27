package com.Crop_Service.Dto;

import com.Crop_Service.Enums.CropName;
import com.Crop_Service.Enums.Season;
import com.Crop_Service.Enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CropResponse(Long cropId,

                           Long farmId,

                           CropName cropName,

                           Season season,

                           LocalDate sowingDate,

                           LocalDate expectedHarvestDate,

                           Status status,

                           String description,

                           LocalDateTime createdAt) {
}
