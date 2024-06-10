package com.zut.znajdzmiejsce.repository;

import com.zut.znajdzmiejsce.model.ParkingSpot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    Page<ParkingSpot> findByParkingLot_ParkingLotId(Long parkingLotId, Pageable pageable);
}
