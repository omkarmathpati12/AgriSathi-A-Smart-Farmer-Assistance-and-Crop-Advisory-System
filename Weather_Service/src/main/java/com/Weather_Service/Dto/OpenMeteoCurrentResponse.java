package com.Weather_Service.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OpenMeteoCurrentResponse(
        Current current
) {

    public record Current(

            @JsonProperty("temperature_2m")
            Double temperature,

            @JsonProperty("relative_humidity_2m")
            Double humidity,

            @JsonProperty("precipitation")
            Double precipitation,

            @JsonProperty("wind_speed_10m")
            Double windSpeed,

            @JsonProperty("weather_code")
            Integer weatherCode
    ) {
    }
}