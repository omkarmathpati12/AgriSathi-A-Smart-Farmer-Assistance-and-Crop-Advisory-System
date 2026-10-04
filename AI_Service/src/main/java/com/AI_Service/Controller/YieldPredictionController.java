package com.AI_Service.Controller;

import com.AI_Service.DTO.YieldPredictionRequest;
import com.AI_Service.DTO.YieldPredictionResponse;
import com.AI_Service.Service.YieldPredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai/yield-prediction")
@RequiredArgsConstructor
public class YieldPredictionController {

    private final YieldPredictionService service;

    @PostMapping
    public ResponseEntity<YieldPredictionResponse> predict(@RequestBody YieldPredictionRequest request) {
        return ResponseEntity.ok(service.predict(request));
    }
}
