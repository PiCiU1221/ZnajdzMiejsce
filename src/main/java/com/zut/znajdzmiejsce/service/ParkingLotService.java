package com.zut.znajdzmiejsce.service;

import com.zut.znajdzmiejsce.dto.LocalizationDTO;
import com.zut.znajdzmiejsce.dto.ParkingLotResponseDTO;
import com.zut.znajdzmiejsce.exception.ParkingLotNotFoundException;
import com.zut.znajdzmiejsce.model.ParkingLot;
import com.zut.znajdzmiejsce.repository.ParkingLotRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;

    @Transactional
    public ParkingLotResponseDTO addParkingLot(
            @Valid LocalizationDTO localizationDTO
    ) {
        ParkingLot parkingLot = new ParkingLot();

        parkingLot.setLatitude(localizationDTO.getLatitude());
        parkingLot.setLongitude(localizationDTO.getLongitude());

        return new ParkingLotResponseDTO(parkingLotRepository.save(parkingLot));
    }

    public List<ParkingLotResponseDTO> getAllParkingLots(int page, int size) {

        if (size > 30) {
            size = 30;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "parkingLotId"));

        Page<ParkingLot> parkingLotPage = parkingLotRepository.findAll(pageable);

        return parkingLotPage.getContent().stream()
                .map(ParkingLotResponseDTO::new)
                .toList();
    }

    @Transactional
    public ParkingLotResponseDTO updateParkingLot(
            Long parkingLotId,
            @Valid LocalizationDTO localizationDTO
    ) {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(() -> new ParkingLotNotFoundException("Parking lot not found"));

        parkingLot.setLatitude(localizationDTO.getLatitude());
        parkingLot.setLongitude(localizationDTO.getLongitude());

        return new ParkingLotResponseDTO(parkingLotRepository.save(parkingLot));
    }

    @Transactional
    public void deleteParkingLot(Long parkingLotId) {

        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(() -> new ParkingLotNotFoundException("Parking lot not found"));

        parkingLotRepository.delete(parkingLot);
    }
}
