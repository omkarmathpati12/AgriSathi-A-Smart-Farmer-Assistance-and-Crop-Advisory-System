package com.AI_Service.Controller;

import com.AI_Service.DTO.WeatherAdviceRequest;
import com.AI_Service.DTO.WeatherAdviceResponse;
import com.AI_Service.Service.WeatherAdviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai/weather-advice")
@RequiredArgsConstructor
public class WeatherAdviceController {

    private final WeatherAdviceService service;

    // Direct weather condition advice
    @PostMapping
    public ResponseEntity<WeatherAdviceResponse> getAdviceByConditions(@RequestBody WeatherAdviceRequest request) {
        return ResponseEntity.ok(service.generateAdvice(request));
    }

    // Advice fetched using existing farm's weather data
    @GetMapping("/{farmId}")
    public ResponseEntity<WeatherAdviceResponse> getAdviceByFarmId(@PathVariable Long farmId) {
        return ResponseEntity.ok(service.generateAdvice(farmId));
    }
}