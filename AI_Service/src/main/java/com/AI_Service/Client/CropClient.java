package com.AI_Service.Client;

import com.AI_Service.DTO.CropResponse;
import com.agrisathi.feign.FeignJwtConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "crop-service", configuration = FeignJwtConfig.class)
public interface CropClient {

    @GetMapping("/crop/farm/{farmId}")
    List<CropResponse> getCropsByFarmId(
            @PathVariable("farmId") Long farmId
    );
}