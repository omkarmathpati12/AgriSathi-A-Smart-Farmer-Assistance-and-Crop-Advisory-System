package com.AI_Service.Repository;

import com.AI_Service.Entity.WeatherAdviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherAdviceRepo extends JpaRepository<WeatherAdviceEntity, Long> {

    List<WeatherAdviceEntity>
    findByFarmIdOrderByCreatedAtDesc(Long farmId);

}
