package com.AI_Service.Service;

import com.AI_Service.DTO.YieldPredictionRequest;
import com.AI_Service.DTO.YieldPredictionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Feature 6: Crop Yield Prediction Service
 * -----------------------------------------
 * Estimates expected harvest yield based on crop type, farm land area,
 * soil quality, and irrigation availability.
 * Fresher friendly with clear comments.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class YieldPredictionService {

    private final GeminiService geminiService;

    public YieldPredictionResponse predict(YieldPredictionRequest request) {
        String crop = (request.cropName() != null && !request.cropName().isBlank()) ? request.cropName() : "Wheat";
        double area = (request.landAreaAcres() != null && request.landAreaAcres() > 0) ? request.landAreaAcres() : 2.0;
        String soil = (request.soilType() != null && !request.soilType().isBlank()) ? request.soilType() : "Alluvial";
        String water = (request.waterSource() != null && !request.waterSource().isBlank()) ? request.waterSource() : "Canal / Borewell";

        // Try AI prompt
        String prompt = """
                Estimate the expected crop yield:
                Crop: %s
                Land Area: %.1f Acres
                Soil Type: %s
                Water Source: %s

                Task:
                1. Expected yield per acre (in Quintals)
                2. Total expected yield
                3. Yield rating (HIGH, AVERAGE, or POOR)
                4. Estimated market revenue in INR
                5. Three actionable tips to maximize yield
                """.formatted(crop, area, soil, water);

        String aiResult = geminiService.ask(prompt);

        // Standard Indian crop average yield baseline (Quintals per acre)
        double baselineYieldPerAcre;
        double pricePerQuintal;

        String cropLower = crop.toLowerCase();
        if (cropLower.contains("wheat")) {
            baselineYieldPerAcre = 22.0;
            pricePerQuintal = 2275.0; // MSP reference
        } else if (cropLower.contains("rice") || cropLower.contains("paddy")) {
            baselineYieldPerAcre = 25.0;
            pricePerQuintal = 2300.0;
        } else if (cropLower.contains("cotton")) {
            baselineYieldPerAcre = 10.0;
            pricePerQuintal = 7120.0;
        } else if (cropLower.contains("soybean")) {
            baselineYieldPerAcre = 11.0;
            pricePerQuintal = 4892.0;
        } else if (cropLower.contains("sugarcane")) {
            baselineYieldPerAcre = 350.0;
            pricePerQuintal = 340.0;
        } else if (cropLower.contains("tomato")) {
            baselineYieldPerAcre = 120.0;
            pricePerQuintal = 1500.0;
        } else {
            baselineYieldPerAcre = 18.0;
            pricePerQuintal = 2500.0;
        }

        // Adjust based on water availability
        double factor = 1.0;
        if (water.toLowerCase().contains("rainfed") || water.toLowerCase().contains("low")) {
            factor = 0.75;
        } else if (water.toLowerCase().contains("drip") || water.toLowerCase().contains("borewell")) {
            factor = 1.15;
        }

        double finalYieldPerAcre = Math.round(baselineYieldPerAcre * factor * 10.0) / 10.0;
        double totalYield = Math.round(finalYieldPerAcre * area * 10.0) / 10.0;
        long estimatedRevenue = Math.round(totalYield * pricePerQuintal);

        String rating = factor >= 1.1 ? "HIGH" : (factor >= 0.9 ? "AVERAGE" : "POOR");

        List<String> keyFactors = new ArrayList<>();
        keyFactors.add("Irrigation source efficiency (" + water + ")");
        keyFactors.add("Soil fertility and organic matter in " + soil + " soil");
        keyFactors.add("Timely application of balanced NPK fertilizers and pest control");

        List<String> tips = new ArrayList<>();
        tips.add("Use certified high-yielding hybrid seeds treated with bio-fungicide.");
        tips.add("Apply micro-nutrients (Zinc Sulphate, Boron) during critical growth stages.");
        tips.add("Adopt micro-irrigation (drip/sprinkler) to optimize water and nutrient uptake.");

        return new YieldPredictionResponse(
                crop,
                area,
                finalYieldPerAcre + " Quintals / Acre",
                totalYield + " Quintals (Total for " + area + " acres)",
                rating,
                "₹ " + String.format("%,d", estimatedRevenue) + " (approximate at current market rates)",
                keyFactors,
                tips
        );
    }
}
