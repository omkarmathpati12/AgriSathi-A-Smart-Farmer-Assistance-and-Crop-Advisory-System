package com.Farm_Service.Controller;

import com.Farm_Service.Dto.FarmLocationResponse;
import com.Farm_Service.Dto.FarmRequest;
import com.Farm_Service.Dto.FarmResponse;
import com.Farm_Service.Service.FarmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/farm")
public class FarmController {

    private final FarmService farmService;

    // Check currently logged-in user
//    @GetMapping("/me")
//    public ResponseEntity<String> getLoggedInUser(
//            Authentication authentication) {
//
//        return ResponseEntity.ok(authentication.getName());
//    }

    // Supports both POST /farm and POST /farm/create
    @PostMapping({"", "/create"})
    public ResponseEntity<FarmResponse> createFarm(
            @Valid @RequestBody FarmRequest farmRequest) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(farmService.createFarm(farmRequest));
    }

    // GET /farm/{farmId}
    @GetMapping("/{farmId}")
    public ResponseEntity<FarmResponse> getFarmById(
            @PathVariable Long farmId) {

        return ResponseEntity.ok(
                farmService.getFarmById(farmId)
        );
    }

    // Supports both GET /farm and GET /farm/all
    @GetMapping({"", "/all"})
    public ResponseEntity<List<FarmResponse>> getAllFarms() {

        return ResponseEntity.ok(
                farmService.getAllFarms()
        );
    }

    // GET /farm/auth/{authId}
    @GetMapping("/auth/{authId}")
    public ResponseEntity<List<FarmResponse>> getFarmsByAuthId(
            @PathVariable Long authId) {

        return ResponseEntity.ok(
                farmService.getFarmsByAuthId(authId)
        );
    }

    // Supports both PUT /farm/{farmId}
    // and PUT /farm/update/{farmId}
    @PutMapping({"/{farmId}", "/update/{farmId}"})
    public ResponseEntity<FarmResponse> updateFarm(
            @PathVariable Long farmId,
            @Valid @RequestBody FarmRequest farmRequest) {

        return ResponseEntity.ok(
                farmService.updateFarm(farmRequest, farmId)
        );
    }

    // Supports both DELETE /farm/{farmId}
    // and DELETE /farm/delete/{farmId}
    @DeleteMapping({"/{farmId}", "/delete/{farmId}"})
    public ResponseEntity<String> deleteFarm(
            @PathVariable Long farmId) {

        farmService.deleteFarm(farmId);

        return ResponseEntity.ok(
                "Farm deleted successfully"
        );
    }

    // GET /farm/{farmId}/location
    @GetMapping("/{farmId}/location")
    public ResponseEntity<FarmLocationResponse> getFarmLocation(
            @PathVariable Long farmId) {

        return ResponseEntity.ok(
                farmService.getFarmLocation(farmId)
        );
    }
}