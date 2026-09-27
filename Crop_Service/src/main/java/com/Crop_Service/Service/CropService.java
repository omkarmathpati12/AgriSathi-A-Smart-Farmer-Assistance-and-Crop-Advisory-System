package com.Crop_Service.Service;

import com.Crop_Service.Client.FarmClient;
import com.Crop_Service.Dto.CropRequest;
import com.Crop_Service.Dto.CropResponse;
import com.Crop_Service.Dto.FarmResponse;
import com.Crop_Service.Entity.CropEntity;
import com.Crop_Service.Enums.CropName;
import com.Crop_Service.Enums.Status;
import com.Crop_Service.Exceptions.ResourceNotFoundException;
import com.Crop_Service.Mapper.CropMapper;
import com.Crop_Service.Repository.CropRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CropService {

    private final CropRepo cropRepo;
    private final CropMapper cropMapper;
    private final FarmClient farmClient;

    public CropResponse create(CropRequest cropRequest) {

        FarmResponse farm=farmClient.getFarmById(cropRequest.farmId());

        if(farm==null){
            throw new ResourceNotFoundException("Farm Not Found",cropRequest.farmId());
        }

        if (cropRepo.existsByFarmIdAndCropName(
                cropRequest.farmId(),
                cropRequest.cropName())) {

            throw new RuntimeException(
                    "Crop already exists in this farm"
            );
        }

        CropEntity crop = cropMapper.toEntity(cropRequest);

        if (crop.getStatus() == null) {
            crop.setStatus(Status.PLANTED);
        }

        CropEntity saved = cropRepo.save(crop);

        return cropMapper.toResponse(saved);
    }


    // Get Crop By ID
    public CropResponse getById(Long cropId) {

        CropEntity crop = cropRepo.findById(cropId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Crop not found with id: " + cropId
                        )
                );

        return cropMapper.toResponse(crop);
    }


    // Get All Crops
    public List<CropResponse> getAll() {

        return cropRepo.findAll()
                .stream()
                .map(cropMapper::toResponse)
                .toList();
    }


    // Get Crops By Farm ID
    public List<CropResponse> getByFarmId(Long farmId) {

        return cropRepo.findByFarmId(farmId)
                .stream()
                .map(cropMapper::toResponse)
                .toList();
    }


    // Get Crops By Status
    public List<CropResponse> getByStatus(
            Status status) {

        return cropRepo.findByStatus(status)
                .stream()
                .map(cropMapper::toResponse)
                .toList();
    }


    // Get Crops By Name
    public List<CropResponse> getByCropName(
            CropName cropName) {

        return cropRepo.findByCropName(cropName)
                .stream()
                .map(cropMapper::toResponse)
                .toList();
    }


    // Update Crop
    public CropResponse update(
            Long cropId,
            CropRequest cropRequest) {

        CropEntity crop = cropRepo.findById(cropId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Crop not found with id: " + cropId
                        )
                );

        if (!crop.getFarmId()
                .equals(cropRequest.farmId())) {

            farmClient.getFarmById(
                    cropRequest.farmId()
            );
        }

        cropMapper.updateEntity(cropRequest, crop);

        CropEntity updated = cropRepo.save(crop);

        return cropMapper.toResponse(updated);
    }


    // Delete Crop
    public void delete(Long cropId) {

        CropEntity crop = cropRepo.findById(cropId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Crop not found with id: " + cropId
                        )
                );

        cropRepo.delete(crop);
    }


    // Change Crop Status
    public CropResponse changeStatus(
            Long cropId,
            Status status) {

        CropEntity crop = cropRepo.findById(cropId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Crop not found with id: " + cropId
                        )
                );

        crop.setStatus(status);

        CropEntity updated = cropRepo.save(crop);

        return cropMapper.toResponse(updated);
    }
}
