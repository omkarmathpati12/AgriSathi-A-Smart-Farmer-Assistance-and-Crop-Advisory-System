package com.Auth_Service.Mapper;

import com.Auth_Service.Dto.AuthRequest;
import com.Auth_Service.Dto.AuthResponse;
import com.Auth_Service.Entity.AuthEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    AuthEntity toRequest(AuthRequest authRequest);

    @Mapping(source = "authId",target = "authId")
    AuthResponse toResponse(AuthEntity authEntity);
    void updateAuth(AuthRequest authRequest, @MappingTarget AuthEntity authEntity);
}
