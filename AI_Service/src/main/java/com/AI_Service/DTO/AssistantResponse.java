package com.AI_Service.DTO;

public record AssistantResponse(
        Long farmId,
        String question,
        String answer
) {
}