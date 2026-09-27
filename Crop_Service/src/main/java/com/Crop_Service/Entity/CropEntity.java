package com.Crop_Service.Entity;

import com.Crop_Service.Enums.CropName;
import com.Crop_Service.Enums.Season;
import com.Crop_Service.Enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "crop")
@Getter
@Setter
public class CropEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cropId;

    @Column(nullable = false)
    private Long farmId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CropName cropName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Season season;

    private LocalDate sowingDate;

    private LocalDate expectedHarvestDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();

        if (status == null) {
            status = Status.PLANTED;
        }
    }
}
