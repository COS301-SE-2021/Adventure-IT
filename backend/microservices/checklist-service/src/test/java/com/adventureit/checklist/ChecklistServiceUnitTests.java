package com.adventureit.checklist;

import com.adventureit.checklist.entity.Checklist;
import com.adventureit.checklist.entity.ChecklistEntry;
import com.adventureit.checklist.repository.ChecklistEntryRepository;
import com.adventureit.checklist.repository.ChecklistRepository;
import com.adventureit.shareddtos.checklist.responses.ChecklistResponseDTO;
import com.adventureit.checklist.service.ChecklistServiceImplementation;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

class ChecklistServiceUnitTests {

    private final ChecklistRepository mockChecklistRepository = Mockito.mock(ChecklistRepository.class);
    private final ChecklistEntryRepository mockChecklistEntryRepository = Mockito.mock(ChecklistEntryRepository.class);
    private final ChecklistServiceImplementation sut = new ChecklistServiceImplementation(mockChecklistRepository,mockChecklistEntryRepository);

    final UUID validChecklistID1 = UUID.randomUUID();
    final UUID validAdventureID = UUID.randomUUID();
    final UUID validUserID = UUID.randomUUID();
    final UUID validEntryID1 = UUID.randomUUID();
    final UUID validEntryID2 = UUID.randomUUID();

    ChecklistEntry mockEntry1 = new ChecklistEntry("Mock Entry 1",validEntryID1,validChecklistID1);
    Checklist mockChecklist1 = new Checklist("Mock Checklist 1","Mock Checklist",validChecklistID1,validUserID,validAdventureID);

    @Test
    @Description("Ensuring a user can add a checklist")
    void addChecklistValid_ReturnString(){
        Assertions.assertEquals("Checklist successfully created", sut.createChecklist("Mock","Mock",UUID.randomUUID(),UUID.randomUUID()));
    }

    @Test
    @Description("Ensuring a user can add an entry")
    void addEntryValid_ReturnString(){
        Mockito.when(mockChecklistRepository.findChecklistById(validChecklistID1)).thenReturn(mockChecklist1);
        Assertions.assertEquals("Checklist Entry successfully added", sut.addChecklistEntry("Mock", validChecklistID1));
    }

    @Test
    @Description("addChecklistEntry will throw an exception if a field is null")
    void addEntryNullField_ThrowException(){
        Assertions.assertThrows(Exception.class, ()->{
            sut.addChecklistEntry("Mock",null);
        });
    }

    @Test
    @Description("Ensuring a user can remove an entry")
    void removeEntryValid_ReturnString(){
        Mockito.when(mockChecklistRepository.findChecklistById(validChecklistID1)).thenReturn(mockChecklist1);
        Mockito.when(mockChecklistEntryRepository.findChecklistEntryById(validEntryID1)).thenReturn(mockEntry1);
        Assertions.assertEquals("Checklist Entry successfully removed", sut.removeChecklistEntry(validEntryID1));
    }

    @Test
    @Description("removeChecklistEntry will throw an exception if a checklist entry does not exist")
    void removeEntryInvalidID_ThrowException(){
        Assertions.assertThrows(Exception.class, ()->{
            sut.removeChecklistEntry(UUID.randomUUID());
        });
    }

    @Test
    @Description("removeChecklistEntry will throw an exception if a field is null")
    void removeEntryNullField_ThrowException(){
        Assertions.assertThrows(Exception.class, ()->{
            sut.removeChecklistEntry(null);
        });
    }

    @Test
    @Description("Ensuring a user can mark a checklist entry")
    void markChecklistEntryValid(){
        Mockito.when(mockChecklistEntryRepository.findChecklistEntryById(validEntryID1)).thenReturn(mockEntry1);
        sut.markChecklistEntry(validEntryID1);
    }

    @Test
    @Description("Ensuring a user can softDelete a checklist")
    void softDeleteChecklistValid(){
        Mockito.when(mockChecklistRepository.findChecklistByIdAndDeleted(validChecklistID1,false)).thenReturn(mockChecklist1);
        Assertions.assertEquals("Checklist moved to bin", sut.softDelete(validChecklistID1,validUserID));
    }

    @Test
    @Description("Ensuring a only an owner can softDelete a checklist")
    void softDeleteChecklistInvalid(){
        Mockito.when(mockChecklistRepository.findChecklistByIdAndDeleted(validChecklistID1,false)).thenReturn(mockChecklist1);
        Assertions.assertThrows(Exception.class, ()->{
            sut.softDelete(validChecklistID1,UUID.randomUUID());
        });
    }

    @Test
    @Description("Ensuring a user can hardDelete a checklist")
    void hardDeleteChecklistValid(){
        Mockito.when(mockChecklistRepository.findChecklistByIdAndDeleted(validChecklistID1,true)).thenReturn(mockChecklist1);
        Assertions.assertEquals("Checklist deleted", sut.hardDelete(validChecklistID1,validUserID));
    }

    @Test
    @Description("Ensuring a only an owner can hardDelete a checklist")
    void hardDeleteChecklistInvalid(){
        Mockito.when(mockChecklistRepository.findChecklistByIdAndDeleted(validChecklistID1,true)).thenReturn(mockChecklist1);
        Assertions.assertThrows(Exception.class, ()->{
            sut.hardDelete(validChecklistID1,UUID.randomUUID());
        });
    }

    @Test
    @Description("Ensuring a user can view the trash")
    void viewTrashValid_ReturnsList(){
        mockChecklist1.setDeleted(true);
        Mockito.when(mockChecklistRepository.findAllByAdventureID(validAdventureID)).thenReturn(List.of(mockChecklist1));
        List<ChecklistResponseDTO> list = sut.viewTrash(validAdventureID);
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    @Description("Ensuring a user can restore a checklist")
    void restoreChecklistValid_ReturnsString(){
        Mockito.when(mockChecklistRepository.findChecklistById(validChecklistID1)).thenReturn(mockChecklist1);
        Assertions.assertEquals("Checklist was restored", sut.restoreChecklist(validChecklistID1,validUserID));
    }

    @Test
    @Description("Ensuring a user can get a checklist by ID")
    void getChecklistByIDValid_ReturnsString(){
        Mockito.when(mockChecklistRepository.findChecklistById(validChecklistID1)).thenReturn(mockChecklist1);
        Assertions.assertNotNull(sut.getChecklistByChecklistId(validChecklistID1));
    }
}
