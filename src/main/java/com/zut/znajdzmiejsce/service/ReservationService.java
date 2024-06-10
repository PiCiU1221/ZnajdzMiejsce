package com.zut.znajdzmiejsce.service;

import com.zut.znajdzmiejsce.dto.ReservationRequestDTO;
import com.zut.znajdzmiejsce.exception.ReservationAddException;
import com.zut.znajdzmiejsce.exception.UserNotFoundException;
import com.zut.znajdzmiejsce.model.ParkingSpot;
import com.zut.znajdzmiejsce.model.Reservation;
import com.zut.znajdzmiejsce.model.User;
import com.zut.znajdzmiejsce.repository.ParkingSpotRepository;
import com.zut.znajdzmiejsce.repository.ReservationRepository;
import com.zut.znajdzmiejsce.repository.UserRepository;
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
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ParkingSpotRepository parkingSpotRepository;

    @Transactional
    public Reservation addReservation(
            @Valid ReservationRequestDTO reservationRequestDTO
    ) {
        boolean hasConflictingReservations = reservationRepository.hasConflictingReservations(
                reservationRequestDTO.getParkingSpotId(),
                reservationRequestDTO.getStartTime(),
                reservationRequestDTO.getEndTime());

        if (hasConflictingReservations) {
            throw new ReservationAddException("The parking spot is already reserved during the specified time interval");
        }

        Reservation reservation = new Reservation();

        User user = userRepository.findById(reservationRequestDTO.getUserId())
                .orElseThrow(() -> new ReservationAddException("User not found"));

        ParkingSpot parkingSpot = parkingSpotRepository.findById(reservationRequestDTO.getParkingSpotId())
                .orElseThrow(() -> new ReservationAddException("Parking spot not found"));

        reservation.setUser(user);
        reservation.setParkingSpot(parkingSpot);

        reservation.setStartTime(reservationRequestDTO.getStartTime());
        reservation.setEndTime(reservationRequestDTO.getEndTime());

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservationsForUser(
            Long userId,
            int page,
            int size
    ) {
        if (size > 30) {
            size = 30;
        }

        if (userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "startTime"));

        Page<Reservation> reservationPage = reservationRepository.findByUser_UserId(userId, pageable);

        return reservationPage.getContent();
    }
}
