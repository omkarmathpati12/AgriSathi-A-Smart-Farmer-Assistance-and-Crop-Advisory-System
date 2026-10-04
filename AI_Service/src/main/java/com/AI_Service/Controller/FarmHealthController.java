package com.AI_Service.Controller;

import com.AI_Service.DTO.FarmHealthResponse;
import com.AI_Service.Service.FarmHealthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai/farm-health")
@RequiredArgsConstructor
public class FarmHealthController {

    private final FarmHealthService service;

    @GetMapping("/{farmId}")
    public ResponseEntity<FarmHealthResponse> getSummary(@PathVariable Long farmId) {
        return ResponseEntity.ok(service.generateSummary(farmId));
    }
}