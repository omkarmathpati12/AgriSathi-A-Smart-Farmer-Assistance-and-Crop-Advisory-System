package com.Crop_Service.Repository;

import com.Crop_Service.Entity.CropEntity;
import com.Crop_Service.Enums.CropName;
import com.Crop_Service.Enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropRepo extends JpaRepository<CropEntity, Long> {
    List<CropEntity> findByFarmId(Long farmId);

    List<CropEntity> findByCropName(CropName cropName);

    List<CropEntity> findByStatus(Status status);

    boolean existsByFarmIdAndCropName(
            Long farmId,
            CropName cropName
    );
}
