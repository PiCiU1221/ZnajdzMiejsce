package com.zut.znajdzmiejsce.service;

import com.zut.znajdzmiejsce.model.Reservation;
import com.zut.znajdzmiejsce.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationSchedulerService {

    private final ReservationRepository reservationRepository;
    private final ParkingSpotStatusService parkingSpotStatusService;

    @Scheduled(fixedRate = 60000) // Runs every minute
    public void updateParkingSpotStatusForStartingReservations() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime soon = now.plusMinutes(5);

        List<Reservation> upcomingReservations = reservationRepository.findByStartTimeBetween(now, soon);

        for (Reservation reservation : upcomingReservations) {
            parkingSpotStatusService.updateParkingSpotStatusByParkingSpotId(reservation.getParkingSpot().getParkingSpotId(), true);
        }
    }
}
