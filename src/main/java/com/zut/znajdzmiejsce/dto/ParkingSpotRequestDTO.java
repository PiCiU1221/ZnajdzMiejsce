package com.zut.znajdzmiejsce.dto;

import lombok.Data;

@Data
public class ParkingSpotRequestDTO {
    private Long parkingLotId;
    private String parkingSpotType;
    private Double latitude;
    private Double longitude;
}
