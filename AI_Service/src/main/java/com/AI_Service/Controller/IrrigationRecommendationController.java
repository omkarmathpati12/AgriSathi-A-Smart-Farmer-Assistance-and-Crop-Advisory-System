package com.AI_Service.Controller;

import com.AI_Service.DTO.IrrigationRecommendationRequest;
import com.AI_Service.DTO.IrrigationRecommendationResponse;
import com.AI_Service.Service.IrrigationRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai/irrigation-recommendation")
@RequiredArgsConstructor
public class IrrigationRecommendationController {

    private final IrrigationRecommendationService service;

    @PostMapping
    public ResponseEntity<IrrigationRecommendationResponse> recommend(@RequestBody IrrigationRecommendationRequest request) {
        return ResponseEntity.ok(service.recommend(request));
    }
}
