package com.adventureit.itinerary;

import com.adventureit.itinerary.Entity.Itinerary;
import com.adventureit.itinerary.Entity.ItineraryEntry;
import com.adventureit.itinerary.Repository.ItineraryEntryRepository;
import com.adventureit.itinerary.Repository.ItineraryRepository;
import com.adventureit.itinerary.Responses.ItineraryResponseDTO;
import com.adventureit.itinerary.Service.ItineraryServiceImplementation;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItineraryServiceUnitTests {
    private final ItineraryRepository mockItineraryRepository = Mockito.mock(ItineraryRepository.class);
    private final ItineraryEntryRepository mockItineraryEntryRepository = Mockito.mock(ItineraryEntryRepository.class);
    private final ItineraryServiceImplementation sut = new ItineraryServiceImplementation(mockItineraryRepository,mockItineraryEntryRepository);

    final UUID validItineraryID1 = UUID.randomUUID();
    final UUID validAdventureID = UUID.randomUUID();
    final UUID validUserID = UUID.randomUUID();
    final UUID validEntryID1 = UUID.randomUUID();
    final UUID validEntryID2 = UUID.randomUUID();

    ItineraryEntry mockEntry1 = new ItineraryEntry("Mock Entry 1","Mock Itinerary Entry",validEntryID1,validItineraryID1,UUID.randomUUID(), LocalDateTime.now());
    ItineraryEntry mockEntry2 = new ItineraryEntry("Mock Entry 2","Mock Itinerary Entry",validEntryID2,validItineraryID1,UUID.randomUUID(),LocalDateTime.now());
    Itinerary mockItinerary1 = new Itinerary("Mock Itinerary 1","Mock Itinerary",validItineraryID1,validAdventureID,validUserID);

    @Test
    @Description("Ensure a user can create an itinerary")
    public void createItineraryValid_ReturnString() throws Exception {
        Assertions.assertEquals(sut.createItinerary("Mock","Mock",validAdventureID,validUserID),"Itinerary successfully created");
    }

    @Test
    @Description("Ensure a user can create an itinerary")
    public void createItineraryNullField_ThrowException() throws Exception {
        Assertions.assertThrows(Exception.class, ()->{
            sut.createItinerary(null,"Mock",validAdventureID,validUserID);
        });
    }

    @Test
    @Description("addItineraryEntry will throw an exception if a field is null")
    public void addEntryNullField_ThrowException() throws Exception {
        Assertions.assertThrows(Exception.class, ()->{
            sut.addItineraryEntry("Mock", "Mock",null, null, null);
        });
    }

    @Test
    @Description("Ensuring a user can remove an entry")
    public void removeEntryValid_ReturnString() throws Exception {
        Mockito.when(mockItineraryRepository.findItineraryById(validItineraryID1)).thenReturn(mockItinerary1);
        Mockito.when(mockItineraryEntryRepository.findItineraryEntryById(validEntryID1)).thenReturn(mockEntry1);
        Assertions.assertEquals(sut.removeItineraryEntry(validEntryID1),"Itinerary Entry successfully removed");
    }

    @Test
    @Description("removeItineraryEntry will throw an exception if a Itinerary entry does not exist")
    public void removeEntryInvalidID_ThrowException() throws Exception {
        Mockito.when(mockItineraryRepository.findItineraryById(validItineraryID1)).thenReturn(mockItinerary1);
        Assertions.assertThrows(Exception.class, ()->{
            sut.removeItineraryEntry(UUID.randomUUID());
        });
    }

    @Test
    @Description("removeItineraryEntry will throw an exception if a field is null")
    public void removeEntryNullField_ThrowException() throws Exception {
        Assertions.assertThrows(Exception.class, ()->{
            sut.removeItineraryEntry(null);
        });
    }

    @Test
    @Description("Ensuring a user can softDelete an itinerary")
    public void softDeleteItineraryValid() throws Exception {
        Mockito.when(mockItineraryRepository.findItineraryByIdAndDeleted(validItineraryID1,false)).thenReturn(mockItinerary1);
        Assertions.assertEquals(sut.softDelete(validItineraryID1,validUserID),"Itinerary moved to bin");
    }

    @Test
    @Description("Ensuring a only an owner can softDelete an itinerary")
    public void softDeleteItineraryInvalid() throws Exception {
        Mockito.when(mockItineraryRepository.findItineraryByIdAndDeleted(validItineraryID1,false)).thenReturn(mockItinerary1);
        Assertions.assertThrows(Exception.class, ()->{
            sut.softDelete(validItineraryID1,UUID.randomUUID());
        });
    }

    @Test
    @Description("Ensuring a user can hardDelete a Itinerary")
    public void hardDeleteItineraryValid() throws Exception {
        Mockito.when(mockItineraryRepository.findItineraryByIdAndDeleted(validItineraryID1,true)).thenReturn(mockItinerary1);
        Assertions.assertEquals(sut.hardDelete(validItineraryID1,validUserID),"Itinerary deleted");
    }

    @Test
    @Description("Ensuring a only an owner can hardDelete a Itinerary")
    public void hardDeleteItineraryInvalid() throws Exception {
        Mockito.when(mockItineraryRepository.findItineraryByIdAndDeleted(validItineraryID1,true)).thenReturn(mockItinerary1);
        Assertions.assertThrows(Exception.class, ()->{
            sut.hardDelete(validItineraryID1,UUID.randomUUID());
        });
    }

    @Test
    @Description("Ensuring a user can view the trash")
    public void viewTrashValid_ReturnsList() throws Exception {
        mockItinerary1.setDeleted(true);
        Mockito.when(mockItineraryRepository.findAllByAdventureID(validAdventureID)).thenReturn(List.of(mockItinerary1));
        List<ItineraryResponseDTO> list = sut.viewTrash(validAdventureID);
        Assertions.assertTrue(!list.isEmpty());
    }

    @Test
    @Description("Ensuring a user can restore a Itinerary")
    public void restoreItineraryValid_ReturnsString() throws Exception {
        Mockito.when(mockItineraryRepository.findItineraryById(validItineraryID1)).thenReturn(mockItinerary1);
        Assertions.assertEquals(sut.restoreItinerary(validItineraryID1,validUserID),"Itinerary was restored");
    }

    @Test
    @Description("Ensuring a user can get a Itinerary by ID")
    public void getItineraryByIDValid_ReturnsString() throws Exception {
        Mockito.when(mockItineraryRepository.findItineraryById(validItineraryID1)).thenReturn(mockItinerary1);
        Assertions.assertTrue(sut.getItineraryById(validItineraryID1) != null);
    }
}
