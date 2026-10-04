package com.AI_Service.Controller;

import com.AI_Service.DTO.AssistantRequest;
import com.AI_Service.DTO.AssistantResponse;
import com.AI_Service.Service.AIAssistantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai/assistant")
@RequiredArgsConstructor
public class AIAssistantController {

    private final AIAssistantService service;

    @PostMapping
    public ResponseEntity<AssistantResponse> ask(@Valid @RequestBody AssistantRequest request) {
        return ResponseEntity.ok(service.ask(request));
    }
}