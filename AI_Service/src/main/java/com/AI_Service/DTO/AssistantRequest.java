package com.AI_Service.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AssistantRequest(

        @NotNull(message = "Farm ID is required")
        Long farmId,

        @NotBlank(message = "Question is required")
        String question
) {
}