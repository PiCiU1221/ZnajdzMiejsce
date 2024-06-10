package com.zut.znajdzmiejsce.dto;

import lombok.Data;

@Data
public class ParkingSpotStatusResponseDTO {
    private Long parkingSpotId;
    private boolean isTaken;

    public ParkingSpotStatusResponseDTO(Long parkingSpotId, boolean isTaken) {
        this.parkingSpotId = parkingSpotId;
        this.isTaken = isTaken;
    }
}