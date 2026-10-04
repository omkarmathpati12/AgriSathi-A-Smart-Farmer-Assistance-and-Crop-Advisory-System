package com.Farm_Service.Client;

import com.Farm_Service.Dto.AuthResponse;
import com.agrisathi.feign.FeignJwtConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Auth-Service", configuration = FeignJwtConfig.class)
public interface AuthClient {
    @GetMapping("/auth/{authId}")
    AuthResponse getAuthById(@PathVariable Long authId
    );
}
