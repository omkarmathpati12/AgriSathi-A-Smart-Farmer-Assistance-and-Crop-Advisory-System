package com.Crop_Service.Dto;

import com.Crop_Service.Enums.FarmType;

import java.time.LocalDateTime;

public record FarmResponse(Long farmId,

                           Long authId,

                           String farmName,

                           Double farmArea,

                           FarmType type,

                           boolean waterAvailability,

                           String address,

                           Double latitude,

                           Double longitude,

                           LocalDateTime createdAt) {
}
