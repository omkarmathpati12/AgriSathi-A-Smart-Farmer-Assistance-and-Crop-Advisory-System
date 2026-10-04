package com.AI_Service.Controller;

import com.AI_Service.DTO.CropRecommendationRequest;
import com.AI_Service.DTO.CropRecommendationResponse;
import com.AI_Service.Service.CropRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai/crop-recommendation")
@RequiredArgsConstructor
public class CropRecommendationController {

    private final CropRecommendationService service;

    @PostMapping
    public ResponseEntity<CropRecommendationResponse> recommend(@RequestBody CropRecommendationRequest request) {
        return ResponseEntity.ok(service.recommend(request));
    }
}