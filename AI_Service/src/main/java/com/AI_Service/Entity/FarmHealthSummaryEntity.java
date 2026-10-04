package com.AI_Service.Entity;

import com.AI_Service.Enums.HealthStatus;
import com.AI_Service.Enums.IrrigationStatus;
import com.AI_Service.Enums.RiskLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "farm_health_summary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FarmHealthSummaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long farmId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HealthStatus overallStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HealthStatus cropHealth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RiskLevel weatherRisk;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IrrigationStatus irrigationStatus;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String summary;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String recommendations;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}