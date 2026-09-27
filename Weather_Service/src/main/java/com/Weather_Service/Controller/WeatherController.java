package com.Weather_Service.Controller;

import com.Weather_Service.Dto.WeatherRequest;
import com.Weather_Service.Service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/current/{farmId}")
    public ResponseEntity<?> getCurrentWeather(
            @PathVariable Long farmId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        weatherService.getCurrentWeather(
                                farmId
                        )
                );
    }


    // Get Weather Forecast
    @GetMapping("/forecast/{farmId}")
    public ResponseEntity<?> getForecast(
            @PathVariable Long farmId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        weatherService.getForecast(
                                farmId
                        )
                );
    }


    // Get Weather By Farm
    @GetMapping("/farm/{farmId}")
    public ResponseEntity<?> getWeatherByFarm(
            @PathVariable Long farmId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        weatherService.getWeatherByFarm(
                                farmId
                        )
                );
    }


    // Refresh Weather
    @PostMapping("/refresh/{farmId}")
    public ResponseEntity<?> refreshWeather(
            @PathVariable Long farmId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        weatherService.refreshWeather(
                                farmId
                        )
                );
    }


    // Store Weather Record
    @PostMapping
    public ResponseEntity<?> storeWeather(
            @RequestBody WeatherRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        weatherService.storeWeather(
                                request
                        )
                );
    }


    // Get Previous Weather Records
    @GetMapping("/records/{farmId}")
    public ResponseEntity<?> getPreviousWeather(
            @PathVariable Long farmId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        weatherService.getPreviousWeather(
                                farmId
                        )
                );
    }
}
