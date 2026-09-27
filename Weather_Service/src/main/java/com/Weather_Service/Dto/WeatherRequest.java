package com.Weather_Service.Dto;

import com.Weather_Service.Enums.WeatherCondition;

import java.time.LocalDateTime;

public record WeatherRequest(

        Long farmId,

        Double temperature,

        Double humidity,

        Double rainProbability,

        WeatherCondition condition,

        Double windSpeed,

        LocalDateTime weatherDate

) {
}