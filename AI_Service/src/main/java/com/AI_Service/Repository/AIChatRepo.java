package com.AI_Service.Repository;

import com.AI_Service.Entity.AIChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AIChatRepo extends JpaRepository<AIChatEntity, Long> {

    List<AIChatEntity>
    findByFarmIdOrderByCreatedAtDesc(Long farmId);
}
