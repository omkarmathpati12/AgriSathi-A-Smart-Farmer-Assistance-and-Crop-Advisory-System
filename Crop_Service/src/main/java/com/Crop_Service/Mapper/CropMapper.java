package com.Crop_Service.Mapper;

import com.Crop_Service.Dto.CropRequest;
import com.Crop_Service.Dto.CropResponse;
import com.Crop_Service.Entity.CropEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CropMapper {
    CropEntity toEntity(CropRequest request);

    CropResponse toResponse(CropEntity entity);

    void updateEntity(
            CropRequest request,
            @MappingTarget CropEntity entity
    );
}
