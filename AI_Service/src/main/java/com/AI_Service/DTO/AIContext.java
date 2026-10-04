package com.AI_Service.DTO;

import java.util.List;

public record AIContext(
        FarmResponse farm,
        List<CropResponse> crops,
        List<WeatherResponse> weather
) {
}