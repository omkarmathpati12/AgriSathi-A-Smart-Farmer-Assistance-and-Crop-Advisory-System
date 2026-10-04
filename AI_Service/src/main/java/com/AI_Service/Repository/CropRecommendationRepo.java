package com.AI_Service.Repository;

import com.AI_Service.Entity.CropRecommendationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropRecommendationRepo extends JpaRepository<CropRecommendationEntity, Long> {
    List<CropRecommendationEntity>
    findByFarmIdOrderByCreatedAtDesc(Long farmId);
}
