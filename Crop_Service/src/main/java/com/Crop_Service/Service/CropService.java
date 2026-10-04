package com.Crop_Service.Service;

import com.Crop_Service.Client.FarmClient;
import com.Crop_Service.Dto.CropRequest;
import com.Crop_Service.Dto.CropResponse;
import com.Crop_Service.Dto.FarmResponse;
import com.Crop_Service.Entity.CropEntity;
import com.Crop_Service.Enums.CropName;
import com.Crop_Service.Enums.Status;
import com.Crop_Service.Exceptions.DuplicateResourceException;
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
        FarmResponse farm = farmClient.getFarmById(cropRequest.farmId());
        if (farm == null) {
            throw new ResourceNotFoundException("Farm", cropRequest.farmId());
        }

        if (cropRepo.existsByFarmIdAndCropName(cropRequest.farmId(), cropRequest.cropName())) {
            throw new DuplicateResourceException("Crop already exists in this farm: " + cropRequest.cropName());
        }

        CropEntity crop = cropMapper.toEntity(cropRequest);
        if (crop.getStatus() == null) {
            crop.setStatus(Status.PLANTED);
        }

        CropEntity saved = cropRepo.save(crop);
        return cropMapper.toResponse(saved);
    }

    public CropResponse getById(Long cropId) {
        CropEntity crop = cropRepo.findById(cropId)
                .orElseThrow(() -> new ResourceNotFoundException("Crop", cropId));
        return cropMapper.toResponse(crop);
    }

    public List<CropResponse> getAll() {
        return cropRepo.findAll()
                .stream()
                .map(cropMapper::toResponse)
                .toList();
    }

    public List<CropResponse> getByFarmId(Long farmId) {
        return cropRepo.findByFarmId(farmId)
                .stream()
                .map(cropMapper::toResponse)
                .toList();
    }

    public List<CropResponse> getByStatus(Status status) {
        return cropRepo.findByStatus(status)
                .stream()
                .map(cropMapper::toResponse)
                .toList();
    }

    public List<CropResponse> getByCropName(CropName cropName) {
        return cropRepo.findByCropName(cropName)
                .stream()
                .map(cropMapper::toResponse)
                .toList();
    }

    public CropResponse update(Long cropId, CropRequest cropRequest) {
        CropEntity crop = cropRepo.findById(cropId)
                .orElseThrow(() -> new ResourceNotFoundException("Crop", cropId));

        if (!crop.getFarmId().equals(cropRequest.farmId())) {
            FarmResponse farm = farmClient.getFarmById(cropRequest.farmId());
            if (farm == null) {
                throw new ResourceNotFoundException("Farm", cropRequest.farmId());
            }
        }

        cropMapper.updateEntity(cropRequest, crop);
        CropEntity updated = cropRepo.save(crop);
        return cropMapper.toResponse(updated);
    }

    public void delete(Long cropId) {
        CropEntity crop = cropRepo.findById(cropId)
                .orElseThrow(() -> new ResourceNotFoundException("Crop", cropId));
        cropRepo.delete(crop);
    }

    public CropResponse changeStatus(Long cropId, Status status) {
        CropEntity crop = cropRepo.findById(cropId)
                .orElseThrow(() -> new ResourceNotFoundException("Crop", cropId));

        crop.setStatus(status);
        CropEntity updated = cropRepo.save(crop);
        return cropMapper.toResponse(updated);
    }
}
