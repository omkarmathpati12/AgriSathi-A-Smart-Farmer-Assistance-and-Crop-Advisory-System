package com.Farm_Service.Controller;

import com.Farm_Service.Dto.FarmRequest;
import com.Farm_Service.Service.FarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/farm")
public class FarmController {

    private final FarmService farmService;

    @PostMapping("/create")
    public ResponseEntity<?> createFarm(@RequestBody FarmRequest farmRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(farmService.createFarm(farmRequest));
    }

    @GetMapping("/{farmId}")
    public ResponseEntity<?> getFarmById(
            @PathVariable Long farmId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(farmService.getFarmById(farmId));
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllFarms() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(farmService.getAllFarms());
    }


    @GetMapping("/auth/{authId}")
    public ResponseEntity<?> getFarmsByAuthId(
            @PathVariable Long authId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(farmService.getFarmsByAuthId(authId));
    }



    @PutMapping("/update/{farmId}")
    public ResponseEntity<?> updateFarm(
            @PathVariable Long farmId,
            @RequestBody FarmRequest farmRequest) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(farmService.updateFarm(farmRequest, farmId));
    }


    // DELETE
    @DeleteMapping("/delete/{farmId}")
    public ResponseEntity<?> deleteFarm(
            @PathVariable Long farmId) {

        farmService.deleteFarm(farmId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Farm deleted successfully");
    }

    @GetMapping("/{farmId}/location")
    public ResponseEntity<?> getFarmLocation(
            @PathVariable Long farmId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(farmService.getFarmLocation(farmId));
    }
}
