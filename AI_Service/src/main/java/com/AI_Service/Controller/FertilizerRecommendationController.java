package com.AI_Service.Controller;

import com.AI_Service.DTO.FertilizerRecommendationRequest;
import com.AI_Service.DTO.FertilizerRecommendationResponse;
import com.AI_Service.Service.FertilizerRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai/fertilizer-recommendation")
@RequiredArgsConstructor
public class FertilizerRecommendationController {

    private final FertilizerRecommendationService service;

    @PostMapping
    public ResponseEntity<FertilizerRecommendationResponse> recommend(@RequestBody FertilizerRecommendationRequest request) {
        return ResponseEntity.ok(service.recommend(request));
    }
}
