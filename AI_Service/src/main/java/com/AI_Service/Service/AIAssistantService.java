package com.AI_Service.Service;

import com.AI_Service.DTO.AIContext;
import com.AI_Service.DTO.AssistantRequest;
import com.AI_Service.DTO.AssistantResponse;
import com.AI_Service.Entity.AIChatEntity;
import com.AI_Service.Repository.AIChatRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AIAssistantService {

    private final AIContextService aiContextService;
    private final GeminiService geminiService;
    private final AIChatRepo repository;

    public AssistantResponse ask(AssistantRequest request) {
        Long farmId = request.farmId() != null ? request.farmId() : 1L;
        AIContext context = aiContextService.getContext(farmId);

        String prompt = """
                You are AgriSathi, a friendly agricultural AI assistant.
                Answer the farmer's question in simple, helpful language:
                Farmer question: %s
                Farm details: %s
                Weather: %s
                """.formatted(
                request.question(),
                context.farm(),
                context.weather()
        );

        String answer = geminiService.ask(prompt);

        if (answer == null || answer.isBlank()) {
            answer = "Hello farmer! Regarding your question: \"" + request.question() + "\", AgriSathi recommends: "
                    + "1. Ensure soil moisture is checked before watering. "
                    + "2. Inspect leaves regularly for early signs of spots or curling. "
                    + "3. Apply balanced NPK fertilizers and avoid over-use of nitrogen.";
        }

        AIChatEntity entity = new AIChatEntity();
        entity.setFarmId(farmId);
        entity.setQuestion(request.question());
        entity.setAnswer(answer);
        repository.save(entity);

        return new AssistantResponse(
                farmId,
                request.question(),
                answer
        );
    }
}