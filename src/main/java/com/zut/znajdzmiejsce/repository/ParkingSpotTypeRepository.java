package com.zut.znajdzmiejsce.repository;

import com.zut.znajdzmiejsce.model.ParkingSpotType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingSpotTypeRepository extends JpaRepository<ParkingSpotType, Long> {
    Optional<ParkingSpotType> findByName(String parkingSpotType);
}
