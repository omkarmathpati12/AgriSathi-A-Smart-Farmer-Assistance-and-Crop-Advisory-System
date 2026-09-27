package com.Farm_Service.Entity;

import com.Farm_Service.Enums.FarmType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "farm")
@Getter
@Setter
public class FarmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long farmId;

    private Long authId;

    private String farmName;

    private Double farmArea;

    @Enumerated(EnumType.STRING)
    private FarmType type;

    private boolean waterAvailability;

    private String address;

    private Double latitude;

    private Double longitude;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
