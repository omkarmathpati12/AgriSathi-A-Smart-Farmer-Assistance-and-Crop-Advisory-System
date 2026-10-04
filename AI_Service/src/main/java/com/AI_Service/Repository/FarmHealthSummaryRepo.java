package com.AI_Service.Repository;

import com.AI_Service.Entity.FarmHealthSummaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FarmHealthSummaryRepo extends JpaRepository<FarmHealthSummaryEntity, Long> {

    List<FarmHealthSummaryEntity>
    findByFarmIdOrderByCreatedAtDesc(Long farmId);
}
