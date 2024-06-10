package com.zut.znajdzmiejsce.controller;

import com.zut.znajdzmiejsce.dto.ReservationRequestDTO;
import com.zut.znajdzmiejsce.exception.ApiErrorResponse;
import com.zut.znajdzmiejsce.exception.ReservationAddException;
import com.zut.znajdzmiejsce.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<?> addReservation(
            @Valid @RequestBody ReservationRequestDTO reservationRequestDTO
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(reservationService.addReservation(reservationRequestDTO));
        } catch (ReservationAddException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiErrorResponse("ReservationAddException", e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllReservationsForUser(
            @PathVariable("userId") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(reservationService.getAllReservationsForUser(userId, page, size));
        } catch (ReservationAddException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiErrorResponse("UserNotFoundException", e.getMessage()));
        }
    }
}
