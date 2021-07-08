//package com.adventureit.itinerary;
//
//import com.adventureit.adventureservice.Entity.Entry;
//import com.adventureit.itinerary.Entity.Itinerary;
//import com.adventureit.itinerary.Entity.ItineraryEntry;
//import com.adventureit.itinerary.Repository.ItineraryEntryRepository;
//import com.adventureit.itinerary.Repository.ItineraryRepository;
//import com.adventureit.itinerary.Service.ItineraryServiceImplementation;
//import jdk.jfr.Description;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.UUID;
//
//public class ItineraryServiceUnitTests {
//    private final ItineraryRepository mockItineraryRepository = Mockito.mock(ItineraryRepository.class);
//    private final ItineraryEntryRepository mockItineraryEntryRepository = Mockito.mock(ItineraryEntryRepository.class);
//    private final ItineraryServiceImplementation sut = new ItineraryServiceImplementation(mockItineraryRepository,mockItineraryEntryRepository);
//
//    final UUID validItineraryID1 = UUID.randomUUID();
//    final UUID validEntryID1 = UUID.randomUUID();
//    final UUID validEntryID2 = UUID.randomUUID();
//
//    ItineraryEntry mockEntry1 = new ItineraryEntry("Mock Entry 1","Mock Itinerary Entry",validEntryID1,validItineraryID1);
//    ItineraryEntry mockEntry2 = new ItineraryEntry("Mock Entry 2","Mock Itinerary Entry",validEntryID2,validItineraryID1);
//    ArrayList<Entry> entries = new ArrayList<>(Arrays.asList(mockEntry1,mockEntry2));
//    Itinerary mockItinerary1 = new Itinerary("Mock Itinerary 1","Mock Itinerary",validItineraryID1,UUID.randomUUID(),UUID.randomUUID(),entries);
//
//    @Test
//    @Description("Ensuring a user can create a itinerary")
//    public void createItineraryValid_ReturnString() throws Exception {
//        Assertions.assertEquals(sut.createItinerary("Mock 1", "Mock Itinerary",UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID()),"Itinerary successfully created");
//    }
//
//    @Test
//    @Description("createItinerary will throw an exception if an Itinerary with the same ID already exists")
//    public void createItineraryExistingID_ThrowException() throws Exception {
//        Mockito.when(mockItineraryRepository.findItineraryById(validItineraryID1)).thenReturn(mockItinerary1);
//        Assertions.assertThrows(Exception.class, ()->{
//            sut.createItinerary("Mock","Mock",validItineraryID1,UUID.randomUUID(),UUID.randomUUID());
//        });
//    }
//
//    @Test
//    @Description("createItinerary will throw an exception if any of the fields are null")
//    public void createItineraryNullField_ThrowException() throws Exception {
//        Assertions.assertThrows(Exception.class, ()->{
//            sut.createItinerary("Mock","Mock",null,UUID.randomUUID(),UUID.randomUUID());
//        });
//    }
//
//    @Test
//    @Description("Ensuring a user can remove a Itinerary")
//    public void removeItineraryValid_ReturnString() throws Exception {
//        Mockito.when(mockItineraryRepository.findItineraryById(validItineraryID1)).thenReturn(mockItinerary1);
//        Assertions.assertEquals(sut.removeItinerary(validItineraryID1),"Itinerary successfully removed");
//    }
//
//    @Test
//    @Description("removeItinerary will throw an exception if a Itinerary does not exist")
//    public void removeItineraryInvalidID_ThrowException() throws Exception {
//        Assertions.assertThrows(Exception.class, ()->{
//            sut.removeItinerary(UUID.randomUUID());
//        });
//    }
//
//    @Test
//    @Description("removeItinerary will throw an exception if ID is null")
//    public void removeItineraryNullID_ThrowException() throws Exception {
//        Assertions.assertThrows(Exception.class, ()->{
//            sut.removeItinerary(null);
//        });
//    }
//
//    @Test
//    @Description("Ensuring a user can add an entry")
//    public void addEntryValid_ReturnString() throws Exception {
//        Mockito.when(mockItineraryRepository.findItineraryById(validItineraryID1)).thenReturn(mockItinerary1);
//        Assertions.assertEquals(sut.addItineraryEntry("Mock", "Mock",UUID.randomUUID(),validItineraryID1),"Itinerary Entry successfully added");
//    }
//
//    @Test
//    @Description("addItineraryEntry will throw an exception if a Itinerary entry with the same ID already exists")
//    public void addEntryExistingID_ThrowException() throws Exception {
//        Mockito.when(mockItineraryRepository.findItineraryById(validItineraryID1)).thenReturn(mockItinerary1);
//        Assertions.assertThrows(Exception.class, ()->{
//            sut.addItineraryEntry("Mock","Mock", validEntryID1,validItineraryID1);
//        });
//    }
//
//    @Test
//    @Description("addItineraryEntry will throw an exception if a field is null")
//    public void addEntryNullField_ThrowException() throws Exception {
//        Assertions.assertThrows(Exception.class, ()->{
//            sut.addItineraryEntry("Mock", "Mock", validEntryID1,null);
//        });
//    }
//
//    @Test
//    @Description("Ensuring a user can remove an entry")
//    public void removeEntryValid_ReturnString() throws Exception {
//        Mockito.when(mockItineraryRepository.findItineraryById(validItineraryID1)).thenReturn(mockItinerary1);
//        Assertions.assertEquals(sut.removeItineraryEntry(validEntryID1,validItineraryID1),"Itinerary Entry successfully removed");
//    }
//
//    @Test
//    @Description("removeItineraryEntry will throw an exception if a Itinerary entry does not exist")
//    public void removeEntryInvalidID_ThrowException() throws Exception {
//        Mockito.when(mockItineraryRepository.findItineraryById(validItineraryID1)).thenReturn(mockItinerary1);
//        Assertions.assertThrows(Exception.class, ()->{
//            sut.removeItineraryEntry(UUID.randomUUID(),validItineraryID1);
//        });
//    }
//
//    @Test
//    @Description("removeItineraryEntry will throw an exception if a field is null")
//    public void removeEntryNullField_ThrowException() throws Exception {
//        Assertions.assertThrows(Exception.class, ()->{
//            sut.removeItineraryEntry(null,validItineraryID1);
//        });
//    }
//
//
//}
