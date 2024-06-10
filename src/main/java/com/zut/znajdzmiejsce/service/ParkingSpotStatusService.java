package com.zut.znajdzmiejsce.service;

import com.zut.znajdzmiejsce.dto.ParkingSpotStatusResponseDTO;
import com.zut.znajdzmiejsce.exception.ParkingSpotAddException;
import com.zut.znajdzmiejsce.exception.ParkingSpotNotFoundException;
import com.zut.znajdzmiejsce.model.ParkingLot;
import com.zut.znajdzmiejsce.model.ParkingSpot;
import com.zut.znajdzmiejsce.model.ParkingSpotStatus;
import com.zut.znajdzmiejsce.repository.ParkingLotRepository;
import com.zut.znajdzmiejsce.repository.ParkingSpotRepository;
import com.zut.znajdzmiejsce.repository.ParkingSpotStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ParkingSpotStatusService {

    private final ParkingSpotStatusRepository parkingSpotStatusRepository;
    private final ParkingSpotRepository parkingSpotRepository;
    private final ParkingLotRepository parkingLotRepository;

    @Transactional
    public ParkingSpotStatus addParkingSpotStatus(ParkingSpot parkingSpot) {
        ParkingSpotStatus parkingSpotStatus = new ParkingSpotStatus();
        parkingSpotStatus.setParkingSpot(parkingSpot);
        parkingSpotStatus.setIsTaken(false);
        return parkingSpotStatusRepository.save(parkingSpotStatus);
    }

    @Transactional
    public ParkingSpotStatusResponseDTO updateParkingSpotStatus(Long parkingLotId, Long parkingSpotId, boolean isTaken) {
        if (!parkingLotRepository.existsById(parkingLotId)) {
            throw new ParkingSpotAddException("Parking lot not found");
        }

        ParkingSpot parkingSpot = parkingSpotRepository.findById(parkingSpotId)
                .orElseThrow(() -> new ParkingSpotNotFoundException("Parking spot not found"));

        if (!Objects.equals(parkingSpot.getParkingLot().getParkingLotId(), parkingLotId)) {
            throw new ParkingSpotAddException("Parking spot not found in parking lot");
        }

        ParkingSpotStatus parkingSpotStatus = parkingSpot.getParkingSpotStatus();

        parkingSpotStatus.setIsTaken(isTaken);

        return new ParkingSpotStatusResponseDTO(parkingSpotId, isTaken);
    }

    @Transactional
    public void updateParkingSpotStatusByParkingSpotId(Long parkingSpotId, boolean isTaken) {
        ParkingSpotStatus parkingSpotStatus = parkingSpotStatusRepository.findByParkingSpot_ParkingSpotId(parkingSpotId)
                .orElseThrow(() -> new RuntimeException("Parking spot status not found"));
        parkingSpotStatus.setIsTaken(isTaken);
        parkingSpotStatusRepository.save(parkingSpotStatus);
    }
}
