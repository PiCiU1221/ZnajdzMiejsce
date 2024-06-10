package com.zut.znajdzmiejsce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "parking_spot_types")
public class ParkingSpotType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_spot_type_id")
    private Long parkingSpotTypeId;

    @Column(name = "name", nullable = false)
    @NotNull
    private String name;
}
