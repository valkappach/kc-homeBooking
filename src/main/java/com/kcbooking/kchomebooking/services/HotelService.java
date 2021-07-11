package com.kcbooking.kchomebooking.services;

import com.kcbooking.kchomebooking.models.Hotel;
import com.kcbooking.kchomebooking.repository.HotelRepository;
import javassist.NotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Data
@Service
@Transactional
public class HotelService {

    @Autowired
    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }


    public Hotel addHotel(Hotel hotel){
       // hotel.setHotel(hotel);
        return hotelRepository.save(hotel);
    }
    /** This methode find All Hotels*/
    public List<Hotel> findAllHotels(){
        return hotelRepository.findAll();
    }
    /*/** This methode find  Hotel By id*/

    public Hotel findHotelById(Long id) throws NotFoundException {
        return hotelRepository.findHotelById(id)
                .orElseThrow(()-> new NotFoundException("Hotel by id " + id + "was not found"));
    }

    /** This methode update Hotel*/
    public Hotel updateHotel(Hotel hotel){
        return hotelRepository.save(hotel);
    }

    /** This methode delete Hotel by id*/

    public void deleteHotel(Long id){
        hotelRepository.deleteHotelById(id);
    }





}
