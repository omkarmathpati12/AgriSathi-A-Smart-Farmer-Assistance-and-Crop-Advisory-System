package com.Farm_Service.Service;

import com.Farm_Service.Client.AuthClient;
import com.Farm_Service.Dto.AuthResponse;
import com.Farm_Service.Dto.FarmLocationResponse;
import com.Farm_Service.Dto.FarmRequest;
import com.Farm_Service.Dto.FarmResponse;
import com.Farm_Service.Entity.FarmEntity;
import com.Farm_Service.Exceptions.ResourceNotFoundException;
import com.Farm_Service.Mapper.FarmMapper;
import com.Farm_Service.Repository.FarmRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmService {

    private  final FarmRepo farmRepo;
    private final FarmMapper farmMapper;
    private final AuthClient authClient;

    public FarmResponse createFarm(FarmRequest farmRequest){

        AuthResponse auth=authClient.getAuthById(farmRequest.authId());

        if(auth==null){
            throw new ResourceNotFoundException("User not found",farmRequest.authId());
        }
        FarmEntity farm=farmMapper.toRequest(farmRequest);
        farm.setAuthId(auth.authId());
        FarmEntity saved=farmRepo.save(farm);
        return farmMapper.toResponse(saved);
    }

    public FarmResponse getFarmById(Long farmId) {

        FarmEntity farm = farmRepo.findById(farmId)
                .orElseThrow(() -> new RuntimeException(
                        "Farm not found with id: " + farmId
                ));

        return farmMapper.toResponse(farm);
    }

    public List<FarmResponse> getAllFarms() {

        return farmRepo.findAll()
                .stream()
                .map(farmMapper::toResponse)
                .toList();
    }

    public List<FarmResponse> getFarmsByAuthId(Long authId) {

        return farmRepo.findByAuthId(authId)
                .stream()
                .map(farmMapper::toResponse)
                .toList();
    }

    public FarmResponse updateFarm(FarmRequest farmRequest, Long farmId) {
        FarmEntity farm=farmRepo.findById(farmId)
                .orElseThrow(()-> new ResourceNotFoundException("Farm not found with id :", farmId));
        farmMapper.updateFarm(farmRequest,farm);
        FarmEntity update=farmRepo.save(farm);
        return farmMapper.toResponse(update);
    }

    public void deleteFarm(Long farmId) {

        FarmEntity farm = farmRepo.findById(farmId)
                .orElseThrow(() -> new RuntimeException(
                        "Farm not found with id: " + farmId
                ));

        farmRepo.delete(farm);
    }

    public FarmLocationResponse getFarmLocation(Long farmId) {

        FarmEntity farm =
                farmRepo.findById(farmId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Farm not found",
                                        farmId
                                )
                        );

        return new FarmLocationResponse(
                farm.getFarmId(),
                farm.getLatitude(),
                farm.getLongitude()
        );
    }
}
