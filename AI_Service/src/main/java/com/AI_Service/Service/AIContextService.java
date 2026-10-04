package com.AI_Service.Service;

import com.AI_Service.Client.CropClient;
import com.AI_Service.Client.FarmClient;
import com.AI_Service.Client.WeatherClient;
import com.AI_Service.DTO.AIContext;
import com.AI_Service.DTO.CropResponse;
import com.AI_Service.DTO.FarmResponse;
import com.AI_Service.DTO.WeatherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service to fetch farm, crop, and weather context for AI decisions.
 * Has try-catch safety so if any microservice is offline, it doesn't crash.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AIContextService {

    private final FarmClient farmClient;
    private final CropClient cropClient;
    private final WeatherClient weatherClient;

    public AIContext getContext(Long farmId) {
        FarmResponse farm = null;
        List<CropResponse> crops = new ArrayList<>();
        List<WeatherResponse> weather = new ArrayList<>();

        if (farmId != null) {
            try {
                farm = farmClient.getFarmById(farmId);
            } catch (Exception e) {
                log.warn("Could not fetch farm details for farmId {}: {}", farmId, e.getMessage());
            }

            try {
                crops = cropClient.getCropsByFarmId(farmId);
            } catch (Exception e) {
                log.warn("Could not fetch crops for farmId {}: {}", farmId, e.getMessage());
            }

            try {
                weather = weatherClient.getWeatherByFarmId(farmId);
            } catch (Exception e) {
                log.warn("Could not fetch weather for farmId {}: {}", farmId, e.getMessage());
            }
        }

        return new AIContext(farm, crops, weather);
    }
}