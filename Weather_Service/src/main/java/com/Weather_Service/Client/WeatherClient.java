package com.Weather_Service.Client;

import com.Weather_Service.Dto.OpenMeteoCurrentResponse;
import com.Weather_Service.Dto.OpenMeteoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "WEATHER-API",
        url = "https://api.open-meteo.com/v1"
)
public interface WeatherClient {

    @GetMapping("/forecast")
    OpenMeteoCurrentResponse getCurrentWeather(
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam("current") String current
    );

    @GetMapping("/forecast")
    OpenMeteoResponse getForecast(
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam("hourly") String hourly,
            @RequestParam("forecast_days") Integer forecastDays
    );
}