package com.Auth_Service.Dto;

import com.Auth_Service.Enums.Role;
import com.Auth_Service.Enums.Status;

import java.time.LocalDateTime;

public record AuthResponse(Long authId,
                           String name,
                           String email,
                           String phone,
                           Role role,
                           Status status,
                           LocalDateTime createdAt,
                           String token) {
}
