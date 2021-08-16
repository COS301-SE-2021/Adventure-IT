//package com.adventureit.itinerary;
//
//import com.adventureit.itinerary.Entity.Itinerary;
//import com.adventureit.itinerary.Entity.ItineraryEntry;
//import com.adventureit.itinerary.Repository.ItineraryEntryRepository;
//import com.adventureit.itinerary.Repository.ItineraryRepository;
//import com.adventureit.itinerary.Responses.ItineraryEntryResponseDTO;
//import com.adventureit.itinerary.Service.ItineraryServiceImplementation;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.UUID;
//
//@SpringBootTest
//public class LocalTests {
//    @Autowired
//    ItineraryEntryRepository itineraryEntryRepository;
//
//    @Autowired
//    ItineraryRepository itineraryRepository;
//
//    @Autowired
//    ItineraryServiceImplementation itineraryServiceImplementation;
//
//    @Test
//    public void addItineraries(){
//
//
//        Itinerary mockItinerary1 = new Itinerary("Mock Itinerary 1", "Mock", UUID.randomUUID(), UUID.fromString("1b28689d-0dbd-49b4-9959-f838d4918e0d"), UUID.randomUUID());
//        Itinerary mockItinerary2 = new Itinerary("Mock Itinerary 2", "Mock", UUID.randomUUID(), UUID.fromString("1b28689d-0dbd-49b4-9959-f838d4918e0d"), UUID.randomUUID());
//        Itinerary mockItinerary3 = new Itinerary("Mock Itinerary 3", "Mock", UUID.randomUUID(), UUID.fromString("1b28689d-0dbd-49b4-9959-f838d4918e0d"), UUID.randomUUID());
//
//        itineraryRepository.save(mockItinerary1);
//        itineraryRepository.save(mockItinerary2);
//        itineraryRepository.save(mockItinerary3);
//
//    }
//
//    @Test
//    public void addEntries(){
//        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//
//        ItineraryEntry mockEntry1 = new ItineraryEntry("Mock Entry 1","Mock",UUID.randomUUID(),UUID.fromString("13e15c5a-53b6-47a0-8888-df948283c17f"),UUID.randomUUID(), LocalDateTime.parse("2021-10-08 12:30",format));
//
//        itineraryEntryRepository.save(mockEntry1);
//    }
//
//    @Test
//    public void getNext() throws Exception {
//        ItineraryEntryResponseDTO responseDTO = itineraryServiceImplementation.nextItem(UUID.fromString("948f3e05-4bca-49ba-8955-fb936992fe02"));
//        System.out.println(responseDTO.getTimestamp());
//        System.out.println(responseDTO.getTitle());
//    }
//
//    @Test
//    public void remove() throws Exception {
//        itineraryServiceImplementation.removeItineraryEntry(UUID.fromString("f730efb2-adbd-42c9-941e-d491146b44e3"));
//    }
//
//    @Test
//    public void addLocations(){
//        ItineraryEntry entry;
//        entry = itineraryEntryRepository.findItineraryEntryById(UUID.fromString("ccef54cc-7418-4ab7-bbde-4850dd4778a0"));
//        entry.setLocation(UUID.fromString("8aa99666-647a-4dd0-a41c-4780442b61f2"));
//        itineraryEntryRepository.save(entry);
//
//        entry = itineraryEntryRepository.findItineraryEntryById(UUID.fromString("96d2201d-69b6-4eb5-b9c2-cdcdc9b577e1"));
//        entry.setLocation(UUID.fromString("c4c13905-6fee-4cf4-871f-7643efc90f2a"));
//        itineraryEntryRepository.save(entry);
//
//        entry = itineraryEntryRepository.findItineraryEntryById(UUID.fromString("2d5fc8a8-68d8-4616-8c0f-084376e4566c"));
//        entry.setLocation(UUID.fromString("2e074420-9889-499e-845c-0250fecde662"));
//        itineraryEntryRepository.save(entry);
//
//    }
//
//}
