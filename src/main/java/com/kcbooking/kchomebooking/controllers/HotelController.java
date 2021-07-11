package com.kcbooking.kchomebooking.controllers;

import com.kcbooking.kchomebooking.models.Hotel;
import com.kcbooking.kchomebooking.services.HotelService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private final HotelService hotelService;



    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;

    }
    @GetMapping("/all")
    public ResponseEntity<List<Hotel>> getAllHotels () {
        List<Hotel> hotels = hotelService.findAllHotels();
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Hotel> getHotelById (@PathVariable("id") Long id) throws NotFoundException {
        Hotel hotel = hotelService.findHotelById(id);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Hotel> addHotel(@RequestBody Hotel hotel) {
        Hotel newHotel = hotelService.addHotel(hotel);
        return new ResponseEntity<>(newHotel, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Hotel> updateHotel(@RequestBody Hotel hotel) {
        Hotel updateHotel = hotelService.updateHotel(hotel);
        return new ResponseEntity<>(updateHotel, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHotel(@PathVariable("id") Long id) {
        hotelService.deleteHotel(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
