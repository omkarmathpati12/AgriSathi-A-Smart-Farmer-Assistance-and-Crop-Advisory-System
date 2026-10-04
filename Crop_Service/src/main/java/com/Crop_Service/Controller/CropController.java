package com.Crop_Service.Controller;

import com.Crop_Service.Dto.CropRequest;
import com.Crop_Service.Dto.CropResponse;
import com.Crop_Service.Enums.CropName;
import com.Crop_Service.Enums.Status;
import com.Crop_Service.Service.CropService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/crop")
public class CropController {

    private final CropService cropService;

//    @PostMapping("/create")
//    public ResponseEntity<CropResponse> create(@Valid @RequestBody CropRequest cropRequest) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(cropService.create(cropRequest));
//    }

    @PostMapping({"", "/create"})
    public ResponseEntity<CropResponse> create(
            @Valid @RequestBody CropRequest request) {

        System.out.println("🔥🔥🔥 CROP CONTROLLER HIT 🔥🔥🔥");
        System.out.println("Farm ID: " + request.farmId());
        System.out.println("Crop Name: " + request.cropName());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cropService.create(request));
    }

    @GetMapping("/{cropId}")
    public ResponseEntity<CropResponse> getById(@PathVariable Long cropId) {
        return ResponseEntity.ok(cropService.getById(cropId));
    }

    @GetMapping
    public ResponseEntity<List<CropResponse>> getAll() {
        return ResponseEntity.ok(cropService.getAll());
    }

    @GetMapping("/farm/{farmId}")
    public ResponseEntity<List<CropResponse>> getByFarmId(@PathVariable Long farmId) {
        return ResponseEntity.ok(cropService.getByFarmId(farmId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<CropResponse>> getByStatus(@PathVariable Status status) {
        return ResponseEntity.ok(cropService.getByStatus(status));
    }

    @GetMapping("/name/{cropName}")
    public ResponseEntity<List<CropResponse>> getByCropName(@PathVariable CropName cropName) {
        return ResponseEntity.ok(cropService.getByCropName(cropName));
    }

    @PutMapping("/{cropId}")
    public ResponseEntity<CropResponse> update(
            @PathVariable Long cropId,
            @Valid @RequestBody CropRequest cropRequest) {
        return ResponseEntity.ok(cropService.update(cropId, cropRequest));
    }

    @DeleteMapping("/{cropId}")
    public ResponseEntity<Void> delete(@PathVariable Long cropId) {
        cropService.delete(cropId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cropId}/status")
    public ResponseEntity<CropResponse> changeStatus(
            @PathVariable Long cropId,
            @RequestParam Status status) {
        return ResponseEntity.ok(cropService.changeStatus(cropId, status));
    }
}
