package com.Weather_Service.Client;

import com.Weather_Service.Dto.FarmLocationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "farm-Service")
public interface FarmClient {
    @GetMapping("/farm/{farmId}/location")
    FarmLocationResponse getFarmLocation(
            @PathVariable("farmId") Long farmId
    );
}
