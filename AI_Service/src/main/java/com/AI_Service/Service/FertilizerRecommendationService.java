package com.AI_Service.Service;

import com.AI_Service.DTO.FertilizerRecommendationRequest;
import com.AI_Service.DTO.FertilizerRecommendationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Feature 4: Fertilizer Recommendation Service
 * --------------------------------------------
 * Suggests appropriate fertilizers and dosages based on crop type,
 * soil condition, and NPK (Nitrogen, Phosphorus, Potassium) nutrient levels.
 * Fresher friendly with clear comments.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FertilizerRecommendationService {

    private final GeminiService geminiService;

    public FertilizerRecommendationResponse recommend(FertilizerRecommendationRequest request) {
        String crop = (request.cropName() != null && !request.cropName().isBlank()) ? request.cropName() : "General Crop";
        String soil = (request.soilType() != null && !request.soilType().isBlank()) ? request.soilType() : "Loamy Soil";
        double n = request.nitrogen() != null ? request.nitrogen() : 140.0; // standard medium
        double p = request.phosphorus() != null ? request.phosphorus() : 50.0;
        double k = request.potassium() != null ? request.potassium() : 45.0;
        double ph = request.soilPh() != null ? request.soilPh() : 6.8;

        // Try AI prompt
        String prompt = """
                You are a senior soil scientist.
                Recommend fertilizers for:
                Crop: %s
                Soil Type: %s
                Nitrogen (N): %.1f kg/ha
                Phosphorus (P): %.1f kg/ha
                Potassium (K): %.1f kg/ha
                Soil pH: %.1f

                Provide:
                1. Recommended fertilizer combination
                2. Dosage per acre
                3. Application timing
                4. Organic alternative
                5. Soil health advice
                """.formatted(crop, soil, n, p, k, ph);

        String aiResult = geminiService.ask(prompt);

        // Smart agronomic rules
        String fertilizer;
        String dosage;
        String timing;
        String organicAlt;
        String soilAdvice;

        if (n < 120.0) {
            fertilizer = "Urea (46% N) + DAP (18:46:0)";
            dosage = "50 kg DAP + 40 kg Urea per acre";
            timing = "Apply DAP as basal dose at sowing time. Split Urea into two top dressings (30 and 55 days after sowing).";
            organicAlt = "Apply Well-rotted Farm Yard Manure (FYM) 4 tons/acre + Azotobacter bio-fertilizer.";
        } else if (p < 30.0) {
            fertilizer = "Single Super Phosphate (SSP 16% P) + Urea";
            dosage = "100 kg SSP + 35 kg Urea per acre";
            timing = "Apply full SSP dose deep in the root zone before planting for strong root development.";
            organicAlt = "Phosphate Solubilizing Bacteria (PSB) @ 2 kg/acre mixed with 200 kg vermicompost.";
        } else if (k < 35.0) {
            fertilizer = "Muriate of Potash (MOP 60% K) + Complex NPK (10:26:26)";
            dosage = "30 kg MOP + 50 kg NPK complex per acre";
            timing = "Apply 50% at basal and 50% before flowering stage to improve grain filling and disease resistance.";
            organicAlt = "Wood ash @ 100 kg/acre or Potassium Mobilizing Biofertilizer (KMB).";
        } else {
            fertilizer = "NPK 19-19-19 Balanced Complex Fertilizer";
            dosage = "50 kg per acre as basal + 2 foliar sprays (1 kg/100 L water) during vegetative growth";
            timing = "Basal application at planting followed by micronutrient foliar spray at 40 days.";
            organicAlt = "Vermicompost 2 tons/acre + Jeevamrutha 200 liters/acre applied with irrigation water.";
        }

        if (ph < 6.0) {
            soilAdvice = "Soil is acidic (pH " + ph + "). Broadcast agricultural lime (calcium carbonate) 200 kg/acre to neutralize acidity.";
        } else if (ph > 7.8) {
            soilAdvice = "Soil is alkaline (pH " + ph + "). Apply agricultural gypsum 250 kg/acre and incorporate organic green manure.";
        } else {
            soilAdvice = "Soil pH (" + ph + ") is in the ideal optimal range (6.5 - 7.5). Micronutrient availability is excellent.";
        }

        return new FertilizerRecommendationResponse(
                crop,
                fertilizer,
                dosage,
                timing,
                organicAlt,
                aiResult != null ? (aiResult.length() > 250 ? aiResult.substring(0, 250) + "..." : aiResult) : soilAdvice
        );
    }
}
