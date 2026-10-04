package com.AI_Service.Controller;

import com.AI_Service.DTO.CropHealthAnalysisRequest;
import com.AI_Service.DTO.CropHealthAnalysisResponse;
import com.AI_Service.Service.CropHealthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai/crop-health")
@RequiredArgsConstructor
public class CropHealthController {

    private final CropHealthService cropHealthService;

    // Analyze crop health from direct input
    @PostMapping("/analyze")
    public ResponseEntity<CropHealthAnalysisResponse> analyzeCropHealth(@RequestBody CropHealthAnalysisRequest request) {
        return ResponseEntity.ok(cropHealthService.analyze(request));
    }

    // Analyze crop health using existing farm data
    @PostMapping("/{farmId}")
    public ResponseEntity<CropHealthAnalysisResponse> analyzeByFarmId(@PathVariable Long farmId) {
        return ResponseEntity.ok(cropHealthService.analyze(farmId));
    }
}