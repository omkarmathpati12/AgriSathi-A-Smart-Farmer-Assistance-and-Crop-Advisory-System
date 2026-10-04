package com.AI_Service.Client;

import com.AI_Service.DTO.FarmResponse;
import com.agrisathi.feign.FeignJwtConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "farm-service", configuration = FeignJwtConfig.class)
public interface FarmClient {
    @GetMapping("/farm/{farmId}")
    FarmResponse getFarmById(
            @PathVariable("farmId") Long farmId
    );
}
