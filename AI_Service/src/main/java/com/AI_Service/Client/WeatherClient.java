package com.AI_Service.Client;

import com.AI_Service.DTO.WeatherResponse;
import com.agrisathi.feign.FeignJwtConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "weather-service", configuration = FeignJwtConfig.class)
public interface WeatherClient {

    @GetMapping("/weather/farm/{farmId}")
    List<WeatherResponse> getWeatherByFarmId(
            @PathVariable("farmId") Long farmId
    );
}