package com.AI_Service.Service;

import com.AI_Service.DTO.AIContext;
import com.AI_Service.DTO.FarmHealthResponse;
import com.AI_Service.Entity.FarmHealthSummaryEntity;
import com.AI_Service.Enums.HealthStatus;
import com.AI_Service.Enums.IrrigationStatus;
import com.AI_Service.Enums.RiskLevel;
import com.AI_Service.Repository.FarmHealthSummaryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmHealthService {

    private final AIContextService aiContextService;
    private final GeminiService geminiService;
    private final FarmHealthSummaryRepo repository;

    public FarmHealthResponse generateSummary(Long farmId) {
        AIContext context = aiContextService.getContext(farmId);

        String prompt = """
                You are an agricultural farm management AI.
                Generate a complete health summary for farm:
                Farm: %s
                Crops: %s
                Weather: %s

                Analyze: overall farm condition, crop condition, weather risks, irrigation, actions.
                """.formatted(context.farm(), context.crops(), context.weather());

        String summary = geminiService.ask(prompt);
        if (summary == null || summary.isBlank()) {
            summary = "Overall farm condition is stable. Soil moisture is within acceptable bounds. Continue regular scouting and irrigation schedule.";
        }

        RiskLevel weatherRisk = calculateWeatherRisk(context);
        IrrigationStatus irrigationStatus = calculateIrrigationStatus(context);

        // Persist the summary
        FarmHealthSummaryEntity entity = new FarmHealthSummaryEntity();
        entity.setFarmId(farmId);
        entity.setOverallStatus(HealthStatus.GOOD);
        entity.setCropHealth(HealthStatus.GOOD);
        entity.setWeatherRisk(weatherRisk);
        entity.setIrrigationStatus(irrigationStatus);
        entity.setSummary(summary);
        entity.setRecommendations("Follow balanced nutrient management and monitor weather forecasts.");
        repository.save(entity);

        return new FarmHealthResponse(
                farmId,
                HealthStatus.GOOD,
                HealthStatus.GOOD,
                weatherRisk,
                irrigationStatus,
                summary,
                List.of("Follow balanced nutrient management and monitor weather forecasts.")
        );
    }

    private RiskLevel calculateWeatherRisk(AIContext context) {
        if (context.weather() == null || context.weather().isEmpty()) return RiskLevel.LOW;
        Double rain = context.weather().get(0).rainProbability();
        if (rain != null && rain >= 80) return RiskLevel.HIGH;
        if (rain != null && rain >= 50) return RiskLevel.MEDIUM;
        return RiskLevel.LOW;
    }

    private IrrigationStatus calculateIrrigationStatus(AIContext context) {
        if (context.weather() == null || context.weather().isEmpty()) return IrrigationStatus.MONITOR;
        Double rain = context.weather().get(0).rainProbability();
        if (rain != null && rain >= 70) return IrrigationStatus.NOT_REQUIRED;
        if (rain != null && rain >= 40) return IrrigationStatus.MONITOR;
        return IrrigationStatus.REQUIRED;
    }
}