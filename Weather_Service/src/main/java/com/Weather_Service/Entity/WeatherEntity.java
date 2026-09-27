package com.Weather_Service.Entity;

import com.Weather_Service.Enums.WeatherCondition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "weather")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weatherId;

    @Column(nullable = false)
    private Long farmId;

    @Column(nullable = false)
    private Double temperature;

    @Column(nullable = false)
    private Double humidity;

    @Column(nullable = false)
    private Double rainProbability;

    @Enumerated(EnumType.STRING)
    @Column(name = "weather_condition", nullable = false)
    private WeatherCondition condition;

    @Column(nullable = false)
    private Double windSpeed;

    @Column(nullable = false)
    private LocalDateTime weatherDate;

    @CreationTimestamp
    private LocalDateTime createdAt;
}