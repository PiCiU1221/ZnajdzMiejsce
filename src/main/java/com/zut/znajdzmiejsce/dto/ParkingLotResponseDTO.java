package com.zut.znajdzmiejsce.dto;

import com.zut.znajdzmiejsce.model.ParkingLot;
import lombok.Data;

@Data
public class ParkingLotResponseDTO {

    private Long parkingLotId;
    private Double latitude;
    private Double longitude;

    public ParkingLotResponseDTO (ParkingLot parkingLot) {
        this.parkingLotId = parkingLot.getParkingLotId();
        this.latitude = parkingLot.getLatitude();
        this.longitude = parkingLot.getLongitude();
    }
}
