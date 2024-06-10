package com.zut.znajdzmiejsce.controller;

import com.zut.znajdzmiejsce.dto.LocalizationDTO;
import com.zut.znajdzmiejsce.dto.ParkingLotResponseDTO;
import com.zut.znajdzmiejsce.exception.ApiErrorResponse;
import com.zut.znajdzmiejsce.exception.ParkingLotNotFoundException;
import com.zut.znajdzmiejsce.service.ParkingLotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-lots")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @PostMapping
    public ResponseEntity<?> addParkingLot(
            @Valid @RequestBody LocalizationDTO localizationDTO
    ) {
        ParkingLotResponseDTO parkingLotResponseDTO = parkingLotService.addParkingLot(localizationDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(parkingLotResponseDTO);
    }

    @GetMapping
    public ResponseEntity<?> getAllParkingLots(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<ParkingLotResponseDTO> parkingLots = parkingLotService.getAllParkingLots(page, size);

        return ResponseEntity.status(HttpStatus.OK)
                .body(parkingLots);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateParkingLot(
            @PathVariable Long id,
            @Valid @RequestBody LocalizationDTO localizationDTO
    ) {
        try {
            ParkingLotResponseDTO parkingLotResponseDTO = parkingLotService.updateParkingLot(id, localizationDTO);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(parkingLotResponseDTO);
        } catch (ParkingLotNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiErrorResponse("ParkingLotNotFoundException", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteParkingLot(
            @PathVariable Long id
    ) {
        try {
            parkingLotService.deleteParkingLot(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .build();
        } catch (ParkingLotNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiErrorResponse("ParkingLotNotFoundException", e.getMessage()));
        }
    }
}
