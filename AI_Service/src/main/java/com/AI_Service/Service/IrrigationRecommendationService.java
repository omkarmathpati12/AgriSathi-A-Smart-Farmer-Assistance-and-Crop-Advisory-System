package com.AI_Service.Service;

import com.AI_Service.DTO.IrrigationRecommendationRequest;
import com.AI_Service.DTO.IrrigationRecommendationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Feature 5: Irrigation Recommendation Service
 * --------------------------------------------
 * Suggests whether the crop needs watering: YES, NO, or WATER SOON.
 * Considers soil moisture %, rain forecast, temperature, and crop needs.
 * Fresher friendly with clear comments.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IrrigationRecommendationService {

    private final GeminiService geminiService;

    public IrrigationRecommendationResponse recommend(IrrigationRecommendationRequest request) {
        String crop = (request.cropName() != null && !request.cropName().isBlank()) ? request.cropName() : "General Crop";
        double moisture = request.soilMoisturePercent() != null ? request.soilMoisturePercent() : 35.0;
        double temp = request.temperature() != null ? request.temperature() : 28.0;
        boolean rainExpected = Boolean.TRUE.equals(request.rainForecast());
        int daysSinceWater = request.daysSinceLastWatering() != null ? request.daysSinceLastWatering() : 3;

        // Try AI prompt
        String prompt = """
                You are an irrigation engineer.
                Recommend whether irrigation is needed:
                Crop: %s
                Soil Moisture: %.1f%%
                Current Temperature: %.1f °C
                Rain Forecast within 24h: %s
                Days since last watered: %d days

                Determine:
                1. Needs Watering strictly as: YES, NO, or WATER SOON
                2. Optimal watering schedule
                3. Recommended water depth/amount
                4. Primary reason
                5. Water saving tip
                """.formatted(crop, moisture, temp, rainExpected ? "YES" : "NO", daysSinceWater);

        String aiResult = geminiService.ask(prompt);

        // Smart irrigation logic
        String needsWatering;
        String schedule;
        String amount;
        String reason;
        String waterSavingTip;

        if (rainExpected) {
            needsWatering = "NO";
            schedule = "Postpone watering - rainfall is predicted soon";
            amount = "0 mm (Rely on incoming rain)";
            reason = "Weather forecast predicts rain within 24 hours. Watering now may cause waterlogging and wastage.";
            waterSavingTip = "Ensure bunds and drainage trenches are open to capture rainwater effectively.";
        } else if (moisture < 30.0 || (moisture < 40.0 && temp > 33.0)) {
            needsWatering = "YES";
            schedule = "Water immediately during early morning (6:00 AM - 8:30 AM) or late evening (5:30 PM - 7:00 PM)";
            amount = "Deep irrigation (40 - 50 mm / approx. 4-5 hours drip run)";
            reason = "Soil moisture level (" + moisture + "%) is below crop critical wilting threshold.";
            waterSavingTip = "Irrigate in the early morning to minimize water loss due to daytime solar evaporation.";
        } else if (moisture <= 50.0 || daysSinceWater >= 5) {
            needsWatering = "WATER SOON";
            schedule = "Plan to irrigate tomorrow morning or within next 36 hours";
            amount = "Moderate irrigation (20 - 25 mm)";
            reason = "Soil moisture is declining (" + moisture + "%). Crop is approaching moisture depletion point.";
            waterSavingTip = "Apply straw or dry grass mulch around plant roots to conserve existing soil moisture.";
        } else {
            needsWatering = "NO";
            schedule = "Next check scheduled in 3-4 days";
            amount = "No irrigation required";
            reason = "Current soil moisture (" + moisture + "%) is adequate and healthy for " + crop + ".";
            waterSavingTip = "Avoid unnecessary overwatering to protect root health and prevent fungal root rot.";
        }

        return new IrrigationRecommendationResponse(
                crop,
                needsWatering,
                schedule,
                amount,
                aiResult != null ? (aiResult.length() > 200 ? aiResult.substring(0, 200) + "..." : aiResult) : reason,
                waterSavingTip
        );
    }
}
