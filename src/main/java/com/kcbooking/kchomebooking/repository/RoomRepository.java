package com.kcbooking.kchomebooking.repository;

import com.kcbooking.kchomebooking.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    void deleteRoomById(Long id);
    Optional<Room> findRoomById(Long id);


//    TEST1
//    SELECT r.id FROM room r
//    WHERE NOT EXISTS (
//            SELECT 1 FROM reservation rs
//            WHERE rs.room_id = r.id AND (
//            (rs.date_in BETWEEN :dateIn AND :dateOut) OR
//    (rs.date_out BETWEEN :dateIn AND :dateOut) OR
//            (rs.date_in < :dateIn AND rs.date_out > :dateOut)
//  )
//          )



//    TEST2
//    select r.id
//    from room r
//    where not exists (select 1
//            from reservation rs
//            where rs.room_id = r.id and
//            rs.date_in < :dateOut and
//            rs.date_out >= :dateIn
//    );




//    TEST 3
//    SELECT rooms.*
//    FROM rooms
//    LEFT JOIN reservations ON
//            (
//                    rooms.id = reservations.room_id
//                    AND (
//                    NOT (reservations.date_in > :dateOut OR reservations.date_out < :dateIn)
//        )
//                )
//                ) WHERE rooms.id = :id AND reservations.room_id IS NULL



//    Commençons par considérer la période demandée (A...B) et
//    une hypothétique réservation (C...D), on peut schématiser
//    toutes les possibilités et voir qu'il y a plusieurs possibilités de conflit :



//    A.....B  C.....D       OKAY: note B < C never happens otherwise
//    A........C..B..D       NOT OKAY    (B inside C..D)
//    A........C.....D..B    NOT OKAY    (C inside A..B, D inside A..B)
//    C..A..D..B    NOT OKAY    (A inside C..D, D inside A..B)
//    C.A.B.D       NOT OKAY    (A inside C..D, B inside C..D)
//    C.....D A..B  OKAY: note A > D never happens otherwise
//    A...B                  OKAY        (no C...D reservation at all)

}
