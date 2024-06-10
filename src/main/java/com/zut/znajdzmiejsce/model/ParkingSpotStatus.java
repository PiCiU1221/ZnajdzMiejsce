package com.zut.znajdzmiejsce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "parking_spot_statuses")
public class ParkingSpotStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_spot_status_id")
    private Long parkingSpotStatusId;

    @OneToOne
    @JoinColumn(name = "parking_spot_id", nullable = false)
    @NotNull
    private ParkingSpot parkingSpot;

    @Column(name = "is_taken", nullable = false)
    private Boolean isTaken = false;
}
