package com.zut.znajdzmiejsce.service;

import com.zut.znajdzmiejsce.dto.ParkingSpotRequestDTO;
import com.zut.znajdzmiejsce.dto.ParkingSpotResponseDTO;
import com.zut.znajdzmiejsce.exception.ParkingLotNotFoundException;
import com.zut.znajdzmiejsce.exception.ParkingSpotAddException;
import com.zut.znajdzmiejsce.exception.ParkingSpotNotFoundException;
import com.zut.znajdzmiejsce.model.ParkingLot;
import com.zut.znajdzmiejsce.model.ParkingSpot;
import com.zut.znajdzmiejsce.model.ParkingSpotStatus;
import com.zut.znajdzmiejsce.model.ParkingSpotType;
import com.zut.znajdzmiejsce.repository.ParkingLotRepository;
import com.zut.znajdzmiejsce.repository.ParkingSpotRepository;
import com.zut.znajdzmiejsce.repository.ParkingSpotTypeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingSpotTypeRepository parkingSpotTypeRepository;
    private final ParkingSpotStatusService parkingSpotStatusService;

    @Transactional
    public ParkingSpotResponseDTO addParkingSpot(
            @Valid ParkingSpotRequestDTO parkingSpotRequestDTO,
            Long parkingLotId
    ) {
        ParkingSpot parkingSpot = new ParkingSpot();

        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(() -> new ParkingSpotAddException("Parking lot not found"));

        parkingSpot.setParkingLot(parkingLot);

        ParkingSpotType parkingSpotType = parkingSpotTypeRepository.findByName(parkingSpotRequestDTO.getParkingSpotType())
                .orElseThrow(() -> new ParkingSpotAddException("Parking spot type not found"));

        parkingSpot.setParkingSpotType(parkingSpotType);

        parkingSpot.setLatitude(parkingSpotRequestDTO.getLatitude());
        parkingSpot.setLongitude(parkingSpotRequestDTO.getLongitude());

        // Here we create a new ParkingSpotStatus object and save it to the database
        ParkingSpotStatus savedParkingSpotStatus = parkingSpotStatusService.addParkingSpotStatus(parkingSpot);

        parkingSpot.setParkingSpotStatus(savedParkingSpotStatus);

        return new ParkingSpotResponseDTO(parkingSpotRepository.save(parkingSpot));
    }

    public List<ParkingSpotResponseDTO> getAllParkingSpotsForParkingLot(
            Long parkingLotId,
            int page,
            int size
    ) {

        if (!parkingLotRepository.existsById(parkingLotId)) {
            throw new ParkingLotNotFoundException("Parking lot not found");
        }

        if (size > 30) {
            size = 30;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "parkingSpotId"));

        Page<ParkingSpot> parkingSpotPage = parkingSpotRepository.findByParkingLot_ParkingLotId(parkingLotId, pageable);

        return parkingSpotPage.getContent().stream()
                .map(ParkingSpotResponseDTO::new)
                .toList();
    }

    @Transactional
    public ParkingSpotResponseDTO updateParkingSpot(
            @Valid ParkingSpotRequestDTO parkingSpotRequestDTO,
            Long parkingLotId,
            Long parkingSpotId
    ) {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingSpotRequestDTO.getParkingLotId())
                .orElseThrow(() -> new ParkingSpotAddException("Parking lot not found"));

        ParkingSpot parkingSpot = parkingSpotRepository.findById(parkingSpotId)
                .orElseThrow(() -> new ParkingSpotNotFoundException("Parking spot not found"));

        if (!Objects.equals(parkingSpot.getParkingLot().getParkingLotId(), parkingLotId)) {
            throw new ParkingSpotAddException("Parking spot not found in parking lot");
        }

        parkingSpot.setParkingLot(parkingLot);

        ParkingSpotType parkingSpotType = parkingSpotTypeRepository.findByName(parkingSpotRequestDTO.getParkingSpotType())
                .orElseThrow(() -> new ParkingSpotAddException("Parking spot type not found"));

        parkingSpot.setParkingSpotType(parkingSpotType);

        parkingSpot.setLatitude(parkingSpotRequestDTO.getLatitude());
        parkingSpot.setLongitude(parkingSpotRequestDTO.getLongitude());

        return new ParkingSpotResponseDTO(parkingSpotRepository.save(parkingSpot));
    }

    @Transactional
    public void deleteParkingSpot(
            Long parkingLotId,
            Long parkingSpotId
    ) {

        ParkingSpot parkingSpot = parkingSpotRepository.findById(parkingSpotId)
                .orElseThrow(() -> new ParkingSpotNotFoundException("Parking spot not found"));

        if (!Objects.equals(parkingSpot.getParkingLot().getParkingLotId(), parkingLotId)) {
            throw new ParkingSpotAddException("Parking spot not found in parking lot");
        }

        parkingSpotRepository.delete(parkingSpot);
    }
}
