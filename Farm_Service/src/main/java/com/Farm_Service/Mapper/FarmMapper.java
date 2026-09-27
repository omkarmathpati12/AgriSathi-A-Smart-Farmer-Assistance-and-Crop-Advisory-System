package com.Farm_Service.Mapper;

import com.Farm_Service.Dto.FarmRequest;
import com.Farm_Service.Dto.FarmResponse;
import com.Farm_Service.Entity.FarmEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FarmMapper {

    FarmEntity toRequest(FarmRequest farmRequest);
    FarmResponse toResponse(FarmEntity farmEntity);
    void  updateFarm(FarmRequest farmRequest, @MappingTarget FarmEntity farmEntity);
}
