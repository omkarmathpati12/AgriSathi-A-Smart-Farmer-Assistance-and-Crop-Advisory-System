package com.Weather_Service.Dto;

public record WeatherRefreshResponse(Long farmId,

                                     WeatherResponse weather) {
}
