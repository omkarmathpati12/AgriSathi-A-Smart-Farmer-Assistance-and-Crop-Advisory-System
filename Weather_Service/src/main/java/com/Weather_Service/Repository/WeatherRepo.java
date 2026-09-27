package com.Weather_Service.Repository;

import com.Weather_Service.Entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherRepo extends JpaRepository<WeatherEntity, Long> {
    Optional<WeatherEntity>
    findTopByFarmIdOrderByWeatherDateDesc(Long farmId);

    List<WeatherEntity>
    findByFarmIdOrderByWeatherDateDesc(Long farmId);

    List<WeatherEntity>
    findByFarmIdOrderByWeatherDateAsc(Long farmId);
}
