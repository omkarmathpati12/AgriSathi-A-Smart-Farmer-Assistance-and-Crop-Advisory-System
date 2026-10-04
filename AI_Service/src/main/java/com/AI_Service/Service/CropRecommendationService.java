package com.AI_Service.Service;

import com.AI_Service.DTO.AIContext;
import com.AI_Service.DTO.CropRecommendationItem;
import com.AI_Service.DTO.CropRecommendationRequest;
import com.AI_Service.DTO.CropRecommendationResponse;
import com.AI_Service.Entity.CropRecommendationEntity;
import com.AI_Service.Enums.Season;
import com.AI_Service.Enums.Suitability;
import com.AI_Service.Repository.CropRecommendationRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Feature 2: Crop Recommendation Service
 * ---------------------------------------
 * Recommends suitable crops based on farm details (soil type, season, water, area).
 * Fresher friendly with clear comments.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CropRecommendationService {

    private final AIContextService aiContextService;
    private final GeminiService geminiService;
    private final CropRecommendationRepo repository;

    public CropRecommendationResponse recommend(CropRecommendationRequest request) {
        Long farmId = request.farmId() != null ? request.farmId() : 1L;
        Season season = request.season() != null ? request.season() : Season.KHARIF;
        String soil = (request.soilType() != null && !request.soilType().isBlank()) ? request.soilType() : "Alluvial";
        String water = (request.waterAvailability() != null && !request.waterAvailability().isBlank()) ? request.waterAvailability() : "Medium";

        // If farmId is provided and context exists, enrich details
        if (request.farmId() != null) {
            AIContext context = aiContextService.getContext(request.farmId());
            if (context.farm() != null) {
                if (context.farm().type() != null) {
                    soil = context.farm().type();
                }
                if (context.farm().waterAvailability()) {
                    water = "High";
                }
            }
        }

        // Try AI prompt
        String prompt = """
                Recommend 3 suitable agricultural crops based on farm details:
                Soil Type: %s
                Season: %s
                Water Availability: %s
                Land Area: %s acres
                Location: %s

                Format for each crop:
                Crop Name | Suitability (HIGH/MEDIUM) | Short Reason
                """.formatted(
                soil,
                season,
                water,
                request.landAreaAcres() != null ? request.landAreaAcres() : 2.0,
                request.locationState() != null ? request.locationState() : "General"
        );

        String aiResult = geminiService.ask(prompt);

        // Smart agricultural heuristic recommendation
        List<CropRecommendationItem> recommendations = new ArrayList<>();
        String soilLower = soil.toLowerCase();

        if (soilLower.contains("black")) {
            recommendations.add(new CropRecommendationItem("Cotton", Suitability.HIGH, "Black soil retains moisture exceptionally well, ideal for cotton fibers."));
            recommendations.add(new CropRecommendationItem("Soybean", Suitability.HIGH, "High yield and nitrogen fixation benefits in black cotton soil."));
            recommendations.add(new CropRecommendationItem("Wheat", Suitability.MEDIUM, "Excellent for Rabi season rotation after pulses."));
        } else if (soilLower.contains("red")) {
            recommendations.add(new CropRecommendationItem("Groundnut (Peanut)", Suitability.HIGH, "Red soil offers loose texture perfect for pod formation."));
            recommendations.add(new CropRecommendationItem("Finger Millet (Ragi)", Suitability.HIGH, "Drought-hardy crop with low water requirement."));
            recommendations.add(new CropRecommendationItem("Tomato", Suitability.MEDIUM, "Suitable with proper irrigation and organic matter."));
        } else if (soilLower.contains("sandy")) {
            recommendations.add(new CropRecommendationItem("Watermelon / Muskmelon", Suitability.HIGH, "Well-drained warm sandy soils promote rapid root growth and sweet fruits."));
            recommendations.add(new CropRecommendationItem("Mustard", Suitability.MEDIUM, "Low water consumption, suitable for winter in light soils."));
            recommendations.add(new CropRecommendationItem("Green Gram (Moong)", Suitability.HIGH, "Short duration pulse restoring soil nitrogen."));
        } else {
            // Alluvial / Loamy soil
            if (season == Season.KHARIF) {
                recommendations.add(new CropRecommendationItem("Paddy (Rice)", Suitability.HIGH, "Fertile alluvial soil holds sufficient water for rice cultivation."));
                recommendations.add(new CropRecommendationItem("Maize (Corn)", Suitability.HIGH, "High market demand and good yield with medium irrigation."));
                recommendations.add(new CropRecommendationItem("Sugarcane", Suitability.MEDIUM, "High cash crop potential where water supply is assured."));
            } else {
                recommendations.add(new CropRecommendationItem("Wheat", Suitability.HIGH, "Ideal winter staple crop yielding 20-25 quintals/acre in alluvial plains."));
                recommendations.add(new CropRecommendationItem("Chickpea (Gram)", Suitability.HIGH, "Thrives in cool dry conditions with low irrigation."));
                recommendations.add(new CropRecommendationItem("Mustard", Suitability.HIGH, "High oil content and profitable cash crop for winter."));
            }
        }

        // Save top recommendation in database
        if (!recommendations.isEmpty()) {
            CropRecommendationItem top = recommendations.get(0);
            CropRecommendationEntity entity = new CropRecommendationEntity();
            entity.setFarmId(farmId);
            entity.setSeason(season);
            entity.setRecommendedCrop(top.crop());
            entity.setSuitability(top.suitability());
            entity.setReason(aiResult != null ? aiResult : top.reason());
            repository.save(entity);
        }

        return new CropRecommendationResponse(farmId, season, recommendations);
    }
}