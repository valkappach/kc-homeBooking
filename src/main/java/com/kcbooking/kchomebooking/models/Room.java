package com.kcbooking.kchomebooking.models;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@NoArgsConstructor



public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomCode;

//    private UUID roomCodeUUID = UUID.randomUUID();

    @ManyToOne
    private Hotel hotel;
    @Column(nullable = false, unique = true)
    private String roomNumber;
    @Column(nullable = false)
   @Enumerated(EnumType.STRING)
    private RoomType roomType;
    @Column(nullable = false)
    private int beds;
    private String urlPhoto;
    public boolean availlable;
    @Column(nullable = false)
    private BigDecimal priceNight;
    private String descrption;
    @OneToOne(cascade = CascadeType.ALL)
    private Reservation reservation;

    public Room(Long id,
                String roomCode,
                Hotel hotel,
                String roomNumber,
                RoomType roomType,
                int beds,
                String urlPhoto,
                boolean availlable,
                BigDecimal priceNight,
                String descrption) {
        this.id = id;
        this.roomCode = roomCode;
        this.hotel = hotel;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.beds = beds;
        this.urlPhoto = urlPhoto;
        this.availlable = availlable;
        this.priceNight = priceNight;
        this.descrption= descrption;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public boolean isAvaillable() {
        if (reservation != null){
            availlable=false;
        }
        return availlable;
    }

    public void setAvaillable(boolean availlable) {
        this.availlable = availlable;
    }

    public BigDecimal getPriceNight() {
        return priceNight;
    }

    public void setPriceNight(BigDecimal priceNight) {
        this.priceNight = priceNight;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        if (reservation != null) {
            this.reservation = reservation;
            reservation.setRoom(this);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber.equals(room.roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber='" + roomNumber + '\'' +
                '}';
    }
}
