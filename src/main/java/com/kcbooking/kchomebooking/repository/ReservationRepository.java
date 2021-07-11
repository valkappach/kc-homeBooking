package com.kcbooking.kchomebooking.repository;

import com.kcbooking.kchomebooking.models.Reservation;
import com.kcbooking.kchomebooking.models.ReservationDates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
//    List<Reservation> findAllByPublicationTimeBetween(
//            Date publicationTimeStart,
//            Date publicationTimeEnd);

    List<Reservation> findAllByDatesBetween(
            ReservationDates checkInDateStart,
            ReservationDates checkOutDateEnd);

}
