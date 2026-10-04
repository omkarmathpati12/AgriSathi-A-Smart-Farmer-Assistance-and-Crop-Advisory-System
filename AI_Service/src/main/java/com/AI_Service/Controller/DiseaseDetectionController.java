package com.AI_Service.Controller;

import com.AI_Service.DTO.DiseaseDetectionRequest;
import com.AI_Service.DTO.DiseaseDetectionResponse;
import com.AI_Service.Service.DiseaseDetectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ai/disease-detection")
@RequiredArgsConstructor
public class DiseaseDetectionController {

    private final DiseaseDetectionService service;

    // Detect disease from symptom description (JSON)
    @PostMapping("/analyze")
    public ResponseEntity<DiseaseDetectionResponse> analyzeSymptoms(@RequestBody DiseaseDetectionRequest request) {
        return ResponseEntity.ok(service.identifyDisease(request));
    }

    // Detect disease from uploaded leaf image (multipart)
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DiseaseDetectionResponse> uploadLeafImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "cropName", required = false, defaultValue = "Tomato") String cropName) {
        return ResponseEntity.ok(service.identifyFromImage(file, cropName));
    }
}
