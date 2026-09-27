package com.Crop_Service.Client;

import com.Crop_Service.Dto.FarmResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Farm-Service")
public interface FarmClient {
    @GetMapping("/api/farms/{farmId}")
    FarmResponse getFarmById(
            @PathVariable("farmId") Long farmId
    );
}
