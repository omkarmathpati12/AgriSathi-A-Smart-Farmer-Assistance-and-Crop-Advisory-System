package com.Weather_Service.Dto;

import com.Weather_Service.Enums.FarmType;

public record FarmResponse(Long farmId,

                           Long authId,

                           String farmName,

                           Double farmArea,

                           FarmType type,

                           boolean waterAvailability,

                           String address,

                           Double latitude,

                           Double longitude) {
}
