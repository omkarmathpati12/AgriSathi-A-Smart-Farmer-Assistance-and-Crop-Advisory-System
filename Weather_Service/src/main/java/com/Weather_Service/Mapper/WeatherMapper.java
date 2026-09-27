package com.Weather_Service.Mapper;

import com.Weather_Service.Dto.WeatherRequest;
import com.Weather_Service.Dto.WeatherResponse;
import com.Weather_Service.Entity.WeatherEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WeatherMapper {

    WeatherEntity toEntity(WeatherRequest request);

    @Mapping(source = "weatherId", target = "weatherId")
    WeatherResponse toResponse(WeatherEntity entity);

    void updateEntity(
            WeatherRequest request,
            @MappingTarget WeatherEntity entity
    );
}
