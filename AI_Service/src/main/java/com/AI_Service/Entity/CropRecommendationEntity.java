package com.AI_Service.Entity;

import com.AI_Service.Enums.Season;
import com.AI_Service.Enums.Suitability;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "crop_recommendation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CropRecommendationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long farmId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Season season;

    @Column(nullable = false)
    private String recommendedCrop;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Suitability suitability;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reason;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}