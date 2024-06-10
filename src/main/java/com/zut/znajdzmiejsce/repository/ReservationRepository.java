package com.zut.znajdzmiejsce.repository;

import com.zut.znajdzmiejsce.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT COUNT(r) > 0 FROM Reservation r " +
            "WHERE r.parkingSpot.parkingSpotId = :parkingSpotId " +
            "AND ((r.startTime BETWEEN :startTime AND :endTime) OR " +
            "(r.endTime BETWEEN :startTime AND :endTime))")
    boolean hasConflictingReservations(@Param("parkingSpotId") Long parkingSpotId,
                                       @Param("startTime") LocalDateTime startTime,
                                       @Param("endTime") LocalDateTime endTime);

    Page<Reservation> findByUser_UserId(Long userId, Pageable pageable);

    List<Reservation> findByStartTimeBetween(LocalDateTime now, LocalDateTime soon);
}
