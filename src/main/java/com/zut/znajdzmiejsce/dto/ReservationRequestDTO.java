package com.zut.znajdzmiejsce.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationRequestDTO {
    private Long userId;
    private Long parkingSpotId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
