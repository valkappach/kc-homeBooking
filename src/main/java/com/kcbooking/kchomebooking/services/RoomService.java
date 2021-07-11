package com.kcbooking.kchomebooking.services;

import com.kcbooking.kchomebooking.models.Room;
import com.kcbooking.kchomebooking.repository.RoomRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoomService {

    @Autowired
    private final RoomRepository roomRepository;




    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    public Room addRoom(Room room){
        room.setRoomNumber(toString());
//        room.setRoomCode(UUID.randomUUID().toString());
        return roomRepository.save(room);
    }
    /** This methode find All Employees*/
    public List<Room> findAllRooms(){
        return roomRepository.findAll();
    }
    /*/** This methode find  Room By id*/

    public Room findRoomById(Long id) throws NotFoundException {
        return roomRepository.findRoomById(id)
                .orElseThrow(()-> new NotFoundException("Room by id " + id + "was not found"));
    }

    /** This methode update Room*/
    public Room updateRoom(Room room){
        return roomRepository.save(room);
    }

    /** This methode delete Room by id*/

    public void deleteRoom(Long id){
        roomRepository.deleteRoomById(id);
    }

}
