package com.AI_Service.Service;

import com.AI_Service.DTO.AIContext;
import com.AI_Service.DTO.CropHealthAnalysisRequest;
import com.AI_Service.DTO.CropHealthAnalysisResponse;
import com.AI_Service.Entity.CropHealthEntity;
import com.AI_Service.Enums.HealthStatus;
import com.AI_Service.Enums.RiskLevel;
import com.AI_Service.Repository.CropHealthRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Feature 1: Crop Health Analysis Service
 * ----------------------------------------
 * Checks the current health condition of crops.
 * Output condition: GOOD, AVERAGE, POOR
 * Written in simple, clean style with clear comments.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CropHealthService {

    private final AIContextService aiContextService;
    private final GeminiService geminiService;
    private final CropHealthRepo repository;

    /**
     * Analyze crop health using direct input details (fresher/testing friendly)
     */
    public CropHealthAnalysisResponse analyze(CropHealthAnalysisRequest request) {
        String crop = (request.cropName() != null && !request.cropName().isBlank()) ? request.cropName() : "General Crop";
        Long farmId = request.farmId() != null ? request.farmId() : 1L;

        // Try AI prompt if available
        String prompt = """
                You are a senior agricultural expert.
                Analyze the crop health condition:
                Crop: %s
                Age in days: %s
                Soil Moisture: %s%%
                Leaf Color: %s
                Observed Pest Symptoms: %s

                Task:
                1. Determine crop condition strictly as one of: GOOD, AVERAGE, or POOR.
                2. Give a brief summary.
                3. List potential issues.
                4. List 3 farmer recommendations.
                """.formatted(
                crop,
                request.cropAgeDays(),
                request.soilMoisture(),
                request.leafColor(),
                request.pestSymptoms()
        );

        String aiResult = geminiService.ask(prompt);

        // Compute condition: GOOD, AVERAGE, or POOR
        String condition = "GOOD";
        int healthScore = 85;
        List<String> issues = new ArrayList<>();
        List<String> recommendations = new ArrayList<>();

        // Smart rules fallback or enrichment
        boolean hasYellowLeaves = request.leafColor() != null && request.leafColor().toLowerCase().contains("yellow");
        boolean hasPests = request.pestSymptoms() != null && !request.pestSymptoms().isBlank();
        boolean lowMoisture = request.soilMoisture() != null && request.soilMoisture() < 30.0;
        boolean excessMoisture = request.soilMoisture() != null && request.soilMoisture() > 80.0;

        if (hasYellowLeaves && hasPests) {
            condition = "POOR";
            healthScore = 40;
            issues.add("Yellowing leaves combined with pest symptoms indicates severe pest stress or root rot.");
            recommendations.add("Apply recommended organic/chemical pesticide immediately.");
            recommendations.add("Inspect root zone for fungal damage and avoid overwatering.");
        } else if (hasYellowLeaves || hasPests || lowMoisture || excessMoisture) {
            condition = "AVERAGE";
            healthScore = 65;
            if (hasYellowLeaves) issues.add("Yellow leaves suggest mild Nitrogen deficiency or moisture stress.");
            if (hasPests) issues.add("Initial pest traces noticed. Early intervention needed.");
            if (lowMoisture) issues.add("Soil moisture is below optimal level. Plant is experiencing water stress.");
            if (excessMoisture) issues.add("High soil moisture can cause root asphyxiation.");

            recommendations.add("Maintain balanced irrigation and check soil drainage.");
            recommendations.add("Spray micronutrient foliar spray (Zinc + Nitrogen) to restore green color.");
        } else {
            condition = "GOOD";
            healthScore = 90;
            issues.add("No major diseases or stress symptoms detected.");
            recommendations.add("Continue regular monitoring and optimal watering schedule.");
            recommendations.add("Apply scheduled basal/top-dress fertilizers on time.");
        }

        String summary = aiResult != null ? aiResult : ("Crop condition is " + condition + " with health score " + healthScore + "%");

        // Save record into database for history
        CropHealthEntity entity = new CropHealthEntity();
        entity.setFarmId(farmId);
        entity.setCrop(crop);
        entity.setHealthStatus(HealthStatus.valueOf(condition));
        entity.setRiskLevel("POOR".equals(condition) ? RiskLevel.HIGH : ("AVERAGE".equals(condition) ? RiskLevel.MEDIUM : RiskLevel.LOW));
        entity.setIssues(String.join("; ", issues));
        entity.setRecommendations(String.join("; ", recommendations));
        repository.save(entity);

        return new CropHealthAnalysisResponse(
                farmId,
                crop,
                condition,
                healthScore,
                summary,
                issues,
                recommendations
        );
    }

    /**
     * Analyze crop health by farmId (integrates with Farm, Crop, and Weather microservices)
     */
    public CropHealthAnalysisResponse analyze(Long farmId) {
        AIContext context = aiContextService.getContext(farmId);

        String cropName = "Wheat";
        if (context.crops() != null && !context.crops().isEmpty()) {
            cropName = context.crops().get(0).cropName();
        }

        Double soilMoisture = 45.0; // default moderate
        CropHealthAnalysisRequest req = new CropHealthAnalysisRequest(
                farmId,
                cropName,
                45,
                soilMoisture,
                "Green",
                "None"
        );

        return analyze(req);
    }
}