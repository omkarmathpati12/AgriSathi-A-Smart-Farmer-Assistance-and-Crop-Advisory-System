package com.Weather_Service.Controller;

import com.Weather_Service.Dto.WeatherForeCastResponse;
import com.Weather_Service.Dto.WeatherRequest;
import com.Weather_Service.Dto.WeatherResponse;
import com.Weather_Service.Service.WeatherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/current/{farmId}")
    public ResponseEntity<WeatherResponse> getCurrentWeather(@PathVariable Long farmId) {
        return ResponseEntity.ok(weatherService.getCurrentWeather(farmId));
    }

    @GetMapping("/forecast/{farmId}")
    public ResponseEntity<List<WeatherForeCastResponse>> getForecast(@PathVariable Long farmId) {
        return ResponseEntity.ok(weatherService.getForecast(farmId));
    }

    @GetMapping("/farm/{farmId}")
    public ResponseEntity<List<WeatherResponse>> getWeatherByFarm(@PathVariable Long farmId) {
        return ResponseEntity.ok(weatherService.getWeatherByFarm(farmId));
    }

    @PostMapping("/refresh/{farmId}")
    public ResponseEntity<WeatherResponse> refreshWeather(@PathVariable Long farmId) {
        return ResponseEntity.ok(weatherService.refreshWeather(farmId));
    }

    @PostMapping
    public ResponseEntity<WeatherResponse> storeWeather(@Valid @RequestBody WeatherRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(weatherService.storeWeather(request));
    }

    @GetMapping("/records/{farmId}")
    public ResponseEntity<List<WeatherResponse>> getPreviousWeather(@PathVariable Long farmId) {
        return ResponseEntity.ok(weatherService.getPreviousWeather(farmId));
    }
}
