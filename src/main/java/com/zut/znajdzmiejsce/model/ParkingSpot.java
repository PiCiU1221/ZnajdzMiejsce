package com.zut.znajdzmiejsce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "parking_spots")
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_spot_id")
    private Long parkingSpotId;

    @ManyToOne
    @JoinColumn(name = "parking_lot_id", nullable = false)
    @NotNull
    private ParkingLot parkingLot;

    @ManyToOne
    @JoinColumn(name = "parking_spot_type_id", nullable = false)
    @NotNull
    private ParkingSpotType parkingSpotType;

    @OneToOne(mappedBy = "parkingSpot", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @NotNull
    private ParkingSpotStatus parkingSpotStatus;

    @Column(name = "latitude", nullable = false)
    @NotNull
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    @NotNull
    private Double longitude;
}
