package com.Auth_Service.Dto;

import com.Auth_Service.Enums.Role;

public record AuthRequest(String name,
                          String password,
                          String email,
                          String phone) {
}
