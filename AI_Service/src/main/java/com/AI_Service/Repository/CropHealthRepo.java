package com.AI_Service.Repository;

import com.AI_Service.Entity.CropHealthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropHealthRepo  extends JpaRepository<CropHealthEntity, Long> {
    List<CropHealthEntity>
    findByFarmIdOrderByCreatedAtDesc(Long farmId);
}
