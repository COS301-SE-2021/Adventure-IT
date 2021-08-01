package com.adventureit.itinerary;

import com.adventureit.itinerary.Entity.Itinerary;
import com.adventureit.itinerary.Entity.ItineraryEntry;
import com.adventureit.itinerary.Repository.ItineraryEntryRepository;
import com.adventureit.itinerary.Repository.ItineraryRepository;
import com.adventureit.itinerary.Responses.ItineraryEntryResponseDTO;
import com.adventureit.itinerary.Service.ItineraryServiceImplementation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@SpringBootTest
public class LocalTests {
    @Autowired
    ItineraryEntryRepository itineraryEntryRepository;

    @Autowired
    ItineraryRepository itineraryRepository;

    @Autowired
    ItineraryServiceImplementation itineraryServiceImplementation;

    @Test
    public void addItineraries(){


        Itinerary mockItinerary1 = new Itinerary("Mock Itinerary 1", "Mock", UUID.randomUUID(), UUID.fromString("1b28689d-0dbd-49b4-9959-f838d4918e0d"), UUID.randomUUID());
        Itinerary mockItinerary2 = new Itinerary("Mock Itinerary 2", "Mock", UUID.randomUUID(), UUID.fromString("1b28689d-0dbd-49b4-9959-f838d4918e0d"), UUID.randomUUID());
        Itinerary mockItinerary3 = new Itinerary("Mock Itinerary 3", "Mock", UUID.randomUUID(), UUID.fromString("1b28689d-0dbd-49b4-9959-f838d4918e0d"), UUID.randomUUID());

        itineraryRepository.save(mockItinerary1);
        itineraryRepository.save(mockItinerary2);
        itineraryRepository.save(mockItinerary3);

    }

    @Test
    public void addEntries(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        ItineraryEntry mockEntry1 = new ItineraryEntry("Mock Entry 1","Mock", UUID.randomUUID(),UUID.fromString("4d29e8bd-429a-45e3-864c-a9709581414d"),"Location 1", LocalDateTime.parse("1986-04-08 12:30",format));
        ItineraryEntry mockEntry2 = new ItineraryEntry("Mock Entry 2","Mock",UUID.randomUUID(),UUID.fromString("4d29e8bd-429a-45e3-864c-a9709581414d"),"Location 2",LocalDateTime.parse("2021-08-01 12:30",format));
        ItineraryEntry mockEntry3 = new ItineraryEntry("Mock Entry 3","Mock",UUID.randomUUID(),UUID.fromString("3638992e-23c1-474c-af52-e2e5a162e6bc"),"Location 3",LocalDateTime.parse("2021-08-02 20:00",format));
        ItineraryEntry mockEntry4 = new ItineraryEntry("Mock Entry 3","Mock",UUID.randomUUID(),UUID.fromString("e311281c-6e9b-4c32-88eb-53dc10f44089"),"Location 3",LocalDateTime.parse("2021-08-01 20:30",format));
        ItineraryEntry mockEntry5 = new ItineraryEntry("Mock Entry 3","Mock",UUID.randomUUID(),UUID.fromString("31358b18-9a7a-44da-af3f-e6d10fd501ed"),"Location 3",LocalDateTime.parse("2021-08-08 22:30",format));

        itineraryEntryRepository.save(mockEntry1);
        itineraryEntryRepository.save(mockEntry2);
        itineraryEntryRepository.save(mockEntry3);
        itineraryEntryRepository.save(mockEntry4);
        itineraryEntryRepository.save(mockEntry5);
    }

    @Test
    public void getNext(){
        ItineraryEntryResponseDTO responseDTO = itineraryServiceImplementation.nextItem(UUID.fromString("1b28689d-0dbd-49b4-9959-f838d4918e0d"));
        System.out.println(responseDTO.getTimestamp());
    }
}
