package com.Farm_Service.Repository;

import com.Farm_Service.Entity.FarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmRepo extends JpaRepository<FarmEntity,Long> {
    List<FarmEntity> findByAuthId(Long authId);
}
