//package com.kcbooking.kchomebooking;
//
//import com.kcbooking.kchomebooking.models.Address;
//import com.kcbooking.kchomebooking.models.Hotel;
//import com.kcbooking.kchomebooking.models.Room;
//import com.kcbooking.kchomebooking.models.RoomType;
//import com.kcbooking.kchomebooking.repository.HotelRepository;
//import com.kcbooking.kchomebooking.services.HotelService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.time.LocalTime;
//@Component
//@Profile({"!test", "!integration"})
//public class simpleData {
//    @Autowired
//    private final HotelService hotelService;
//
//    @Autowired
//    private final HotelRepository hotelRepository;
//
//    public simpleData(HotelService hotelService, HotelRepository hotelRepository) {
//        this.hotelService = hotelService;
//        this.hotelRepository = hotelRepository;
//    }
//
//
//    @Bean
//    public CommandLineRunner insertTestData() {
//        return args -> {
//            System.out.println("-------------CommandLiner , insserting sample data ");
//            createHotel1();
//
//        };
//    }
//
//
//
//    private void createHotel1() {
//        LocalTime earliestCheckInTime = LocalTime.of(9, 0);
//        LocalTime latestCheckInTime = LocalTime.of(20, 0);
//        LocalTime earliestCheckOutTime = LocalTime.of(12, 0);
//        LocalTime latestCheckOutTime = LocalTime.of(14, 0);
//        BigDecimal lateCheckOutFee = BigDecimal.valueOf(45.60);
//
//        Address address1 = new Address("3 rue girdle", "166 Albert Road",
//                "nantes", 44000, "france");
//
//        Hotel grandHotel = new Hotel(null,
//                null,
//                "grand hotel",
//                " hotel@gmail.com",
//                "0660124578",
//                address1,
//                earliestCheckInTime,
//                latestCheckInTime,
//                earliestCheckOutTime,
//                latestCheckOutTime,
//                lateCheckOutFee
//
//
//                );
//
//
//        Room room1 = new Room(null, grandHotel, "G1", RoomType.Economy, 1, null, BigDecimal.valueOf(65.12));
////        Room room2 = new Room("G2",2, RoomType.Business, 2, BigDecimal.valueOf(105.45));
////        Room room3 = new Room("G3", 1,RoomType.Luxury, 2,BigDecimal.valueOf(205.66));
////        Room room4 = new Room("G4", 4,RoomType.Economy, 2,BigDecimal.valueOf(35.40));
//
//        grandHotel.addRoom(room1);
////        grandHotel.addRoom(room2);
////        grandHotel.addRoom(room3);
////        grandHotel.addRoom(room4);
//
//        hotelService.addHotel(grandHotel);
//
//    }
//
//}
