package com.adventureit.checklist;

import com.adventureit.checklist.Entity.Checklist;
import com.adventureit.checklist.Entity.ChecklistEntry;
import com.adventureit.checklist.Repository.ChecklistEntryRepository;
import com.adventureit.checklist.Repository.ChecklistRepository;
import com.adventureit.checklist.Responses.ChecklistEntryResponseDTO;
import com.adventureit.checklist.Responses.ChecklistResponseDTO;
import com.adventureit.checklist.Service.ChecklistServiceImplementation;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ChecklistServiceUnitTests {

    private final ChecklistRepository mockChecklistRepository = Mockito.mock(ChecklistRepository.class);
    private final ChecklistEntryRepository mockChecklistEntryRepository = Mockito.mock(ChecklistEntryRepository.class);
    private final ChecklistServiceImplementation sut = new ChecklistServiceImplementation(mockChecklistRepository,mockChecklistEntryRepository);

    final UUID validChecklistID1 = UUID.randomUUID();
    final UUID validAdventureID = UUID.randomUUID();
    final UUID validUserID = UUID.randomUUID();
    final UUID validEntryID1 = UUID.randomUUID();
    final UUID validEntryID2 = UUID.randomUUID();

    ChecklistEntry mockEntry1 = new ChecklistEntry("Mock Entry 1",validEntryID1,validChecklistID1);
    ChecklistEntry mockEntry2 = new ChecklistEntry("Mock Entry 2",validEntryID2,validChecklistID1);
    Checklist mockChecklist1 = new Checklist("Mock Checklist 1","Mock Checklist",validChecklistID1,validUserID,validAdventureID);

    @Test
    @Description("Ensuring a user can add a checklist")
    public void addChecklistValid_ReturnString() throws Exception {
        Assertions.assertEquals(sut.createChecklist("Mock","Mock",UUID.randomUUID(),UUID.randomUUID()),"Checklist successfully created");
    }

    @Test
    @Description("Ensuring a user can add an entry")
    public void addEntryValid_ReturnString() throws Exception {
        Mockito.when(mockChecklistRepository.findChecklistById(validChecklistID1)).thenReturn(mockChecklist1);
        Assertions.assertEquals(sut.addChecklistEntry("Mock", validChecklistID1),"Checklist Entry successfully added");
    }

    @Test
    @Description("addChecklistEntry will throw an exception if a field is null")
    public void addEntryNullField_ThrowException() throws Exception {
        Assertions.assertThrows(Exception.class, ()->{
            sut.addChecklistEntry("Mock",null);
        });
    }

    @Test
    @Description("Ensuring a user can remove an entry")
    public void removeEntryValid_ReturnString() throws Exception {
        Mockito.when(mockChecklistRepository.findChecklistById(validChecklistID1)).thenReturn(mockChecklist1);
        Mockito.when(mockChecklistEntryRepository.findChecklistEntryById(validEntryID1)).thenReturn(mockEntry1);
        Assertions.assertEquals(sut.removeChecklistEntry(validEntryID1),"Checklist Entry successfully removed");
    }

    @Test
    @Description("removeChecklistEntry will throw an exception if a checklist entry does not exist")
    public void removeEntryInvalidID_ThrowException() throws Exception {
        Assertions.assertThrows(Exception.class, ()->{
            sut.removeChecklistEntry(UUID.randomUUID());
        });
    }

    @Test
    @Description("removeChecklistEntry will throw an exception if a field is null")
    public void removeEntryNullField_ThrowException() throws Exception {
        Assertions.assertThrows(Exception.class, ()->{
            sut.removeChecklistEntry(null);
        });
    }

    @Test
    @Description("Ensuring a user can mark a checklist entry")
    public void markChecklistEntryValid() throws Exception {
        Mockito.when(mockChecklistEntryRepository.findChecklistEntryById(validEntryID1)).thenReturn(mockEntry1);
        sut.markChecklistEntry(validEntryID1);
    }

    @Test
    @Description("Ensuring a user can softDelete a checklist")
    public void softDeleteChecklistValid() throws Exception {
        Mockito.when(mockChecklistRepository.findChecklistByIdAndDeleted(validChecklistID1,false)).thenReturn(mockChecklist1);
        Assertions.assertEquals(sut.softDelete(validChecklistID1,validUserID),"Checklist moved to bin");
    }

    @Test
    @Description("Ensuring a only an owner can softDelete a checklist")
    public void softDeleteChecklistInvalid() throws Exception {
        Mockito.when(mockChecklistRepository.findChecklistByIdAndDeleted(validChecklistID1,false)).thenReturn(mockChecklist1);
        Assertions.assertThrows(Exception.class, ()->{
            sut.softDelete(validChecklistID1,UUID.randomUUID());
        });
    }

    @Test
    @Description("Ensuring a user can hardDelete a checklist")
    public void hardDeleteChecklistValid() throws Exception {
        Mockito.when(mockChecklistRepository.findChecklistByIdAndDeleted(validChecklistID1,true)).thenReturn(mockChecklist1);
        Assertions.assertEquals(sut.hardDelete(validChecklistID1,validUserID),"Checklist deleted");
    }

    @Test
    @Description("Ensuring a only an owner can hardDelete a checklist")
    public void hardDeleteChecklistInvalid() throws Exception {
        Mockito.when(mockChecklistRepository.findChecklistByIdAndDeleted(validChecklistID1,true)).thenReturn(mockChecklist1);
        Assertions.assertThrows(Exception.class, ()->{
            sut.hardDelete(validChecklistID1,UUID.randomUUID());
        });
    }

    @Test
    @Description("Ensuring a user can view the trash")
    public void viewTrashValid_ReturnsList() throws Exception {
        mockChecklist1.setDeleted(true);
        Mockito.when(mockChecklistRepository.findAllByAdventureID(validAdventureID)).thenReturn(List.of(mockChecklist1));
        List<ChecklistResponseDTO> list = sut.viewTrash(validAdventureID);
        Assertions.assertTrue(!list.isEmpty());
    }

    @Test
    @Description("Ensuring a user can restore a checklist")
    public void restoreChecklistValid_ReturnsString() throws Exception {
        Mockito.when(mockChecklistRepository.findChecklistById(validChecklistID1)).thenReturn(mockChecklist1);
        Assertions.assertEquals(sut.restoreChecklist(validChecklistID1,validUserID),"Checklist was restored");
    }

    @Test
    @Description("Ensuring a user can get a checklist by ID")
    public void getChecklistByIDValid_ReturnsString() throws Exception {
        Mockito.when(mockChecklistRepository.findChecklistById(validChecklistID1)).thenReturn(mockChecklist1);
        Assertions.assertTrue(sut.getChecklistByChecklistId(validChecklistID1) != null);
    }
}
