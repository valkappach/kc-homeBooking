package com.kcbooking.kchomebooking.repository;

import com.kcbooking.kchomebooking.models.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
