package com.zut.znajdzmiejsce.repository;

import com.zut.znajdzmiejsce.model.ParkingSpotStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingSpotStatusRepository extends JpaRepository<ParkingSpotStatus, Long> {
    Optional<ParkingSpotStatus> findByParkingSpot_ParkingSpotId(Long parkingSpotId);
}
