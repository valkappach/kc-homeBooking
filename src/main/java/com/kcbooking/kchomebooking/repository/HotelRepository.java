package com.kcbooking.kchomebooking.repository;

import com.kcbooking.kchomebooking.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
    void deleteHotelById(Long id);
    Optional<Hotel> findHotelById(Long id);

//    void addHotel(Hotel grandHotel);
}
