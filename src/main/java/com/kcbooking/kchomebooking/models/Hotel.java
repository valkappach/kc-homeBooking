package com.kcbooking.kchomebooking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Entity
public class Hotel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column(nullable = false , updatable = false)
//    private String hotelCode;
    private String name;
    @Column(nullable = false)
    private String email;
    private String phone;
    @Column(nullable = false)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "streetLine1", column = @Column(name = "address_street_line1")),
            @AttributeOverride( name = "streetLine2", column = @Column(name = "address_street_line2")),
            @AttributeOverride( name = "city", column = @Column(name = "address_city")),
            @AttributeOverride( name = "postCode", column = @Column(name = "address_poste_Code")),
            @AttributeOverride( name = "country", column = @Column(name = "address_country"))
    })
    private Address address;

    @Column(nullable = false)
    private LocalTime earliestCheckInTime; //première heure d’enregistrement
    @Column(nullable = false)
    private LocalTime latestCheckInTime; //dernière heure d'enregistrement
    @Column(nullable = false)
    private LocalTime standardCheckOutTime; //Heure de sortie standard
    @Column(nullable = false)
    private LocalTime latestCheckOutTime; //dernière heure de départ

     @Column(nullable = false)
    private BigDecimal lateCheckOutFee; //dernière heure de départ
    @OneToMany(mappedBy = "hotel",cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<Room> rooms;

//    @Column(nullable = false)
//    private LocalTime earliestCheckInTime;
//
//    @Column(nullable = false)
//    private LocalTime latestCheckInTime;
//
//    @Column(nullable = false)
//    private LocalTime standardCheckOutTime;
//
//    @Column(nullable = false)
//    private LocalTime latestCheckOutTime;
//
//    @Column(nullable = false)
//    private BigDecimal lateCheckoutFee;


    private final static LocalTime DEFAULT_EARLIEST_CHECK_IN = LocalTime.of(7, 0);
    private final static LocalTime DEFAULT_LATEST_CHECK_IN = LocalTime.of(22, 0);
    private final static LocalTime DEFAULT_STANDARD_CHECKOUT = LocalTime.of(11, 0);
    private final static LocalTime DEFAULT_LATEST_CHECKOUT = LocalTime.of(22, 0);
    private final static BigDecimal DEFAULT_LATE_CHECKOUT_FEE = BigDecimal.valueOf(15.95);


//
//    public Hotel(String name, Address address, String email, String phone,
//                 LocalTime DEFAULT_EARLIEST_CHECK_IN,
//                 LocalTime DEFAULT_LATEST_CHECK_IN,
//                 LocalTime DEFAULT_STANDARD_CHECKOUT,
//                 LocalTime DEFAULT_LATEST_CHECKOUT,
//                 BigDecimal DEFAULT_LATE_CHECKOUT_FEE) {
//        this(name, address, email, phone,
//                DEFAULT_EARLIEST_CHECK_IN,
//                DEFAULT_LATEST_CHECK_IN,
//                DEFAULT_STANDARD_CHECKOUT,
//                DEFAULT_LATEST_CHECKOUT,
//                DEFAULT_LATE_CHECKOUT_FEE);
//    }
//



    public Hotel() {
    }


    public Hotel(Long id, String hotelCode,
                 String name, String email,
                 String phone, Address address,
                 LocalTime earliestCheckInTime,
                 LocalTime latestCheckInTime,
                 LocalTime standardCheckOutTime,
                 LocalTime latestCheckOutTime,
                 BigDecimal lateCheckOutFee
                 //Set<Room> rooms
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.earliestCheckInTime = earliestCheckInTime;
        this.latestCheckInTime = latestCheckInTime;
        this.standardCheckOutTime = standardCheckOutTime;
        this.latestCheckOutTime = latestCheckOutTime;
        this.lateCheckOutFee = lateCheckOutFee;
       // this.rooms = rooms;
    }

    /**
     * Generates range of allowable check in times spanning from the earliest to latest times for this hotel.
     * <p>This provides an estimated check in time to help staff organise the room.</p>
     * */

    public void addRoom(Room room) {
        rooms.add(room);
        room.setHotel(this);

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalTime getEarliestCheckInTime() {
        return earliestCheckInTime;
    }

    public void setEarliestCheckInTime(LocalTime earliestCheckInTime) {
        this.earliestCheckInTime = earliestCheckInTime;
    }

    public LocalTime getLatestCheckInTime() {
        return latestCheckInTime;
    }

    public void setLatestCheckInTime(LocalTime latestCheckInTime) {
        this.latestCheckInTime = latestCheckInTime;
    }

    public LocalTime getStandardCheckOutTime() {
        return standardCheckOutTime;
    }

    public void setStandardCheckOutTime(LocalTime standardCheckOutTime) {
        this.standardCheckOutTime = standardCheckOutTime;
    }

    public LocalTime getLatestCheckOutTime() {
        return latestCheckOutTime;
    }

    public void setLatestCheckOutTime(LocalTime latestCheckOutTime) {
        this.latestCheckOutTime = latestCheckOutTime;
    }

    public BigDecimal getLateCheckOutFee() {
        return lateCheckOutFee;
    }

    public void setLateCheckOutFee(BigDecimal lateCheckOutFee) {
        this.lateCheckOutFee = lateCheckOutFee;
    }

//    public Collection<Room> getRooms() {
//        return rooms;
//    }
//
//    public void setRooms(Collection<Room> rooms) {
//        this.rooms = rooms;
//    }

    /**
     * Generates range of allowable check in times spanning from the earliest to latest times for this hotel.
     * <p>This provides an estimated check in time to help staff organise the room.</p>
     */
    public List<LocalTime> allowableCheckInTimes() {
        long span = ChronoUnit.HOURS.between(earliestCheckInTime, latestCheckInTime) + 1;

        return Stream.iterate(earliestCheckInTime, time -> time.plusHours(1))
                .limit(span)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return Objects.equals(address, hotel.address);

    }

    @Override
    public int hashCode() {

        return Objects.hash(address);
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address=" + address +
//                ", earliestCheckInTime=" + earliestCheckInTime +
//                ", latestCheckInTime=" + latestCheckInTime +
//                ", standardCheckOutTime=" + standardCheckOutTime +
//                ", latestCheckOutTime=" + latestCheckOutTime +
//                ", lateCheckOutFee=" + lateCheckOutFee +
//                ", rooms=" + rooms +
                '}';
    }


}
