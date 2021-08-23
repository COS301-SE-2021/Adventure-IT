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
import java.util.List;
import java.util.UUID;

class ItineraryServiceUnitTests {
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
    void createItineraryValid_ReturnString() throws Exception {
        Assertions.assertEquals("Itinerary successfully created", sut.createItinerary("Mock","Mock",validAdventureID,validUserID));
    }

    @Test
    @Description("Ensure a user can create an itinerary")
    void createItineraryNullField_ThrowException() throws Exception {
        Assertions.assertThrows(Exception.class, ()->{
            sut.createItinerary(null,"Mock",validAdventureID,validUserID);
        });
    }

    @Test
    @Description("addItineraryEntry will throw an exception if a field is null")
    void addEntryNullField_ThrowException() throws Exception {
        Assertions.assertThrows(Exception.class, ()->{
            sut.addItineraryEntry("Mock", "Mock",null, null, null);
        });
    }

    @Test
    @Description("Ensuring a user can remove an entry")
    void removeEntryValid_ReturnString() throws Exception {
        Mockito.when(mockItineraryRepository.findItineraryById(validItineraryID1)).thenReturn(mockItinerary1);
        Mockito.when(mockItineraryEntryRepository.findItineraryEntryById(validEntryID1)).thenReturn(mockEntry1);
        Assertions.assertEquals("Itinerary Entry successfully removed", sut.removeItineraryEntry(validEntryID1));
    }

    @Test
    @Description("removeItineraryEntry will throw an exception if a Itinerary entry does not exist")
    void removeEntryInvalidID_ThrowException() throws Exception {
        Mockito.when(mockItineraryRepository.findItineraryById(validItineraryID1)).thenReturn(mockItinerary1);
        Assertions.assertThrows(Exception.class, ()->{
            sut.removeItineraryEntry(UUID.randomUUID());
        });
    }

    @Test
    @Description("removeItineraryEntry will throw an exception if a field is null")
    void removeEntryNullField_ThrowException() throws Exception {
        Assertions.assertThrows(Exception.class, ()->{
            sut.removeItineraryEntry(null);
        });
    }

    @Test
    @Description("Ensuring a user can softDelete an itinerary")
    void softDeleteItineraryValid() throws Exception {
        Mockito.when(mockItineraryRepository.findItineraryByIdAndDeleted(validItineraryID1,false)).thenReturn(mockItinerary1);
        Assertions.assertEquals("Itinerary moved to bin", sut.softDelete(validItineraryID1,validUserID));
    }

    @Test
    @Description("Ensuring a only an owner can softDelete an itinerary")
    void softDeleteItineraryInvalid() throws Exception {
        Mockito.when(mockItineraryRepository.findItineraryByIdAndDeleted(validItineraryID1,false)).thenReturn(mockItinerary1);
        Assertions.assertThrows(Exception.class, ()->{
            sut.softDelete(validItineraryID1,UUID.randomUUID());
        });
    }

    @Test
    @Description("Ensuring a user can hardDelete a Itinerary")
    void hardDeleteItineraryValid() throws Exception {
        Mockito.when(mockItineraryRepository.findItineraryByIdAndDeleted(validItineraryID1,true)).thenReturn(mockItinerary1);
        Assertions.assertEquals("Itinerary deleted", sut.hardDelete(validItineraryID1,validUserID));
    }

    @Test
    @Description("Ensuring a only an owner can hardDelete a Itinerary")
    void hardDeleteItineraryInvalid() throws Exception {
        Mockito.when(mockItineraryRepository.findItineraryByIdAndDeleted(validItineraryID1,true)).thenReturn(mockItinerary1);
        Assertions.assertThrows(Exception.class, ()->{
            sut.hardDelete(validItineraryID1,UUID.randomUUID());
        });
    }

    @Test
    @Description("Ensuring a user can view the trash")
    void viewTrashValid_ReturnsList() throws Exception {
        mockItinerary1.setDeleted(true);
        Mockito.when(mockItineraryRepository.findAllByAdventureID(validAdventureID)).thenReturn(List.of(mockItinerary1));
        List<ItineraryResponseDTO> list = sut.viewTrash(validAdventureID);
        Assertions.assertTrue(!list.isEmpty());
    }

    @Test
    @Description("Ensuring a user can restore a Itinerary")
    void restoreItineraryValid_ReturnsString() throws Exception {
        Mockito.when(mockItineraryRepository.findItineraryById(validItineraryID1)).thenReturn(mockItinerary1);
        Assertions.assertEquals("Itinerary was restored", sut.restoreItinerary(validItineraryID1,validUserID));
    }

    @Test
    @Description("Ensuring a user can get a Itinerary by ID")
    void getItineraryByIDValid_ReturnsString() throws Exception {
        Mockito.when(mockItineraryRepository.getItineraryById(validItineraryID1)).thenReturn(mockItinerary1);
        Assertions.assertNotNull(sut.getItineraryById(validItineraryID1));
    }
}
