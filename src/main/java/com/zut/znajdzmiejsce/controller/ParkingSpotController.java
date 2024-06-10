package com.zut.znajdzmiejsce.controller;

import com.zut.znajdzmiejsce.dto.ParkingSpotRequestDTO;
import com.zut.znajdzmiejsce.dto.ParkingSpotResponseDTO;
import com.zut.znajdzmiejsce.exception.ApiErrorResponse;
import com.zut.znajdzmiejsce.exception.ParkingLotNotFoundException;
import com.zut.znajdzmiejsce.exception.ParkingSpotAddException;
import com.zut.znajdzmiejsce.service.ParkingSpotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-lots")
@RequiredArgsConstructor
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    @PostMapping("/{parkingLotId}/parking-spots")
    public ResponseEntity<?> addParkingSpot(
            @PathVariable("parkingLotId") Long parkingLotId,
            @Valid @RequestBody ParkingSpotRequestDTO parkingSpotRequestDTO
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(parkingSpotService.addParkingSpot(parkingSpotRequestDTO, parkingLotId));
        } catch (ParkingSpotAddException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiErrorResponse("ParkingSpotAddException", e.getMessage()));
        }
    }

    @GetMapping("/{parkingLotId}/parking-spots")
    public ResponseEntity<?> getAllParkingSpots(
            @PathVariable("parkingLotId") Long parkingLotId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            List<ParkingSpotResponseDTO> parkingSpots = parkingSpotService.getAllParkingSpotsForParkingLot(parkingLotId, page, size);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(parkingSpots);
        } catch (ParkingLotNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiErrorResponse("ParkingSpotAddException", e.getMessage()));
        }
    }

    @PutMapping("/{parkingLotId}/parking-spots/{parkingSpotId}")
    public ResponseEntity<?> updateParkingSpot(
            @PathVariable("parkingLotId") Long parkingLotId,
            @PathVariable("parkingSpotId") Long parkingSpotId,
            @Valid @RequestBody ParkingSpotRequestDTO parkingSpotRequestDTO
    ) {
        try {
            ParkingSpotResponseDTO parkingSpotResponseDTO = parkingSpotService.updateParkingSpot(parkingSpotRequestDTO, parkingLotId, parkingSpotId);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(parkingSpotResponseDTO);
        } catch (ParkingSpotAddException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiErrorResponse("ParkingSpotAddException", e.getMessage()));
        }
    }

    @DeleteMapping("/{parkingLotId}/parking-spots/{parkingSpotId}")
    public ResponseEntity<?> deleteParkingSpot(
            @PathVariable("parkingLotId") Long parkingLotId,
            @PathVariable("parkingSpotId") Long parkingSpotId
    ) {
        try {
            parkingSpotService.deleteParkingSpot(parkingLotId, parkingSpotId);
            return ResponseEntity.status(HttpStatus.OK)
                    .build();
        } catch (ParkingSpotAddException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiErrorResponse("ParkingSpotAddException", e.getMessage()));
        }
    }
}
