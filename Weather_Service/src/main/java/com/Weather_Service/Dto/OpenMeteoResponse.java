package com.Weather_Service.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OpenMeteoResponse(

        Double latitude,

        Double longitude,

        Hourly hourly

) {

    public record Hourly(

            List<String> time,

            @JsonProperty("temperature_2m")
            List<Double> temperature,

            @JsonProperty("relative_humidity_2m")
            List<Double> humidity,

            @JsonProperty("precipitation_probability")
            List<Double> rainProbability,

            @JsonProperty("wind_speed_10m")
            List<Double> windSpeed,

            @JsonProperty("weather_code")
            List<Integer> weatherCode

    ) {
    }
}