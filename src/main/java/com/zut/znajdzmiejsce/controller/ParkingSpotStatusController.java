package com.zut.znajdzmiejsce.controller;

import com.zut.znajdzmiejsce.exception.ApiErrorResponse;
import com.zut.znajdzmiejsce.exception.ParkingSpotAddException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.zut.znajdzmiejsce.service.ParkingSpotStatusService;

@RestController
@RequestMapping("/api/parking-lots/{parkingLotId}/parking-spots")
@RequiredArgsConstructor
public class ParkingSpotStatusController {

    private final ParkingSpotStatusService parkingSpotStatusService;

    @PutMapping("/{parkingSpotId}/status")
    public ResponseEntity<?> updateParkingSpotStatus(
            @PathVariable("parkingLotId") Long parkingLotId,
            @PathVariable("parkingSpotId") Long parkingSpotId,
            @RequestParam("isTaken") Boolean isTaken
    ) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(parkingSpotStatusService.updateParkingSpotStatus(parkingLotId, parkingSpotId, isTaken));
        } catch (ParkingSpotAddException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiErrorResponse("ParkingSpotAddException", e.getMessage()));
        }
    }
}
