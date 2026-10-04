package com.AI_Service.DTO;

import java.util.List;

/**
 * Output response for Crop Health Analysis.
 * Condition output: GOOD, AVERAGE, POOR as requested.
 */
public record CropHealthAnalysisResponse(
        Long farmId,
        String cropName,
        String condition, // "GOOD", "AVERAGE", "POOR"
        int healthScore,  // 0 to 100%
        String summary,
        List<String> issuesFound,
        List<String> recommendations
) {
}
