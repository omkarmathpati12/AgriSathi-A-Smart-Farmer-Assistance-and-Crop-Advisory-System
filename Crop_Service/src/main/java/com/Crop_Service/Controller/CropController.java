package com.Crop_Service.Controller;

import com.Crop_Service.Dto.CropRequest;
import com.Crop_Service.Enums.CropName;
import com.Crop_Service.Enums.Status;
import com.Crop_Service.Service.CropService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/crop")
public class CropController {

    private final CropService cropService;

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody CropRequest cropRequest) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cropService.create(cropRequest));
    }


    // Get Crop By ID
    @GetMapping("/{cropId}")
    public ResponseEntity<?> getById(
            @PathVariable Long cropId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cropService.getById(cropId));
    }


    // Get All Crops
    @GetMapping
    public ResponseEntity<?> getAll() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cropService.getAll());
    }


    // Get Crops By Farm ID
    @GetMapping("/farm/{farmId}")
    public ResponseEntity<?> getByFarmId(
            @PathVariable Long farmId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cropService.getByFarmId(farmId));
    }


    // Get Crops By Status
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getByStatus(
            @PathVariable Status status) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cropService.getByStatus(status));
    }


    // Get Crops By Name
    @GetMapping("/name/{cropName}")
    public ResponseEntity<?> getByCropName(
            @PathVariable CropName cropName) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cropService.getByCropName(cropName));
    }


    // Update Crop
    @PutMapping("/{cropId}")
    public ResponseEntity<?> update(
            @PathVariable Long cropId,
            @RequestBody CropRequest cropRequest) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cropService.update(cropId, cropRequest));
    }


    // Delete Crop
    @DeleteMapping("/{cropId}")
    public ResponseEntity<?> delete(
            @PathVariable Long cropId) {

        cropService.delete(cropId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


    // Change Crop Status
    @PutMapping("/{cropId}/status")
    public ResponseEntity<?> changeStatus(
            @PathVariable Long cropId,
            @RequestParam Status status) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cropService.changeStatus(cropId, status));
    }


}
