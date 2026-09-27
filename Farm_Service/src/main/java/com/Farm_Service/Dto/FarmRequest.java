package com.Farm_Service.Dto;

import com.Farm_Service.Enums.FarmType;

public record FarmRequest (Long authId,
                           String farmName,
                           Double farmArea,
                           FarmType type,
                           boolean waterAvailability,
                           String address,
                           Double latitude,
                           Double longitude){
}
