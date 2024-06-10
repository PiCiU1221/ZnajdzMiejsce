package com.zut.znajdzmiejsce.dto;

import com.zut.znajdzmiejsce.model.ParkingSpot;
import lombok.Data;

@Data
public class ParkingSpotResponseDTO {

    private Long parkingSpotId;
    private Long parkingLotId;
    private String parkingSpotType;
    private Double latitude;
    private Double longitude;
    private Boolean isTaken;

    public ParkingSpotResponseDTO(ParkingSpot parkingSpot) {
        this.parkingSpotId = parkingSpot.getParkingSpotId();
        this.parkingLotId = parkingSpot.getParkingLot().getParkingLotId();
        this.parkingSpotType = parkingSpot.getParkingSpotType().getName();
        this.latitude = parkingSpot.getLatitude();
        this.longitude = parkingSpot.getLongitude();
        this.isTaken = parkingSpot.getParkingSpotStatus().getIsTaken();
    }
}
