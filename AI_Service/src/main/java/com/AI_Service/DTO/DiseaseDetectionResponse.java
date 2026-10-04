package com.AI_Service.DTO;

/**
 * Response DTO for plant disease detection.
 * Identifies the common disease and provides actionable cure & prevention.
 */
public record DiseaseDetectionResponse(
        String cropName,
        String detectedDisease,
        String status, // "HEALTHY" or "DISEASED"
        String confidence,
        String symptomsSummary,
        String causes,
        String chemicalTreatment,
        String organicTreatment,
        String preventionAdvice
) {
}
