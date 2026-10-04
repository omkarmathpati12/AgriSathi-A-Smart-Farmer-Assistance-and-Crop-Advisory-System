package com.AI_Service.DTO;

/**
 * Request DTO for plant disease detection.
 * Freshers can send symptom description or image info.
 */
public record DiseaseDetectionRequest(
        String cropName,
        String symptoms,
        String imageUrl
) {
}
