package com.adventureit.checklist;

import com.adventureit.adventureservice.Entity.Entry;
import com.adventureit.checklist.Entity.Checklist;
import com.adventureit.checklist.Entity.ChecklistEntry;
import com.adventureit.checklist.Repository.ChecklistEntryRepository;
import com.adventureit.checklist.Repository.ChecklistRepository;
import com.adventureit.checklist.Service.ChecklistServiceImplementation;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class ChecklistServiceUnitTests {

    private final ChecklistRepository mockChecklistRepository = Mockito.mock(ChecklistRepository.class);
    private final ChecklistEntryRepository mockChecklistEntryRepository = Mockito.mock(ChecklistEntryRepository.class);
    private final ChecklistServiceImplementation sut = new ChecklistServiceImplementation(mockChecklistRepository,mockChecklistEntryRepository);

    final UUID validChecklistID1 = UUID.randomUUID();
    final UUID validEntryID1 = UUID.randomUUID();
    final UUID validEntryID2 = UUID.randomUUID();

    ChecklistEntry mockEntry1 = new ChecklistEntry("Mock Entry 1",validEntryID1,validChecklistID1);
    ChecklistEntry mockEntry2 = new ChecklistEntry("Mock Entry 2",validEntryID2,validChecklistID1);
    ArrayList<Entry> entries = new ArrayList<>(Arrays.asList(mockEntry1,mockEntry2));
    Checklist mockChecklist1 = new Checklist("Mock Checklist 1","Mock Checklist",entries,validChecklistID1,UUID.randomUUID(),UUID.randomUUID());

    @Test
    @Description("Ensuring a user can create a checklist")
    public void createChecklistValid_ReturnString() throws Exception {
        Assertions.assertEquals(sut.createChecklist("Mock 1", "Mock Checklist",UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID()),"Checklist successfully created");
    }

    @Test
    @Description("createChecklist will throw an exception if a checklist with the same ID already exists")
    public void createChecklistExistingID_ThrowException() throws Exception {
        Mockito.when(mockChecklistRepository.findChecklistById(validChecklistID1)).thenReturn(mockChecklist1);
        Assertions.assertThrows(Exception.class, ()->{
            sut.createChecklist("Mock","Mock",validChecklistID1,UUID.randomUUID(),UUID.randomUUID());
        });
    }

    @Test
    @Description("createChecklist will throw an exception if any of the fields are null")
    public void createChecklistNullField_ThrowException() throws Exception {
        Assertions.assertThrows(Exception.class, ()->{
            sut.createChecklist("Mock","Mock",null,UUID.randomUUID(),UUID.randomUUID());
        });
    }

    @Test
    @Description("Ensuring a user can remove a checklist")
    public void removeChecklistValid_ReturnString() throws Exception {
        Mockito.when(mockChecklistRepository.findChecklistById(validChecklistID1)).thenReturn(mockChecklist1);
        Assertions.assertEquals(sut.removeChecklist(validChecklistID1),"Checklist successfully removed");
    }

    @Test
    @Description("removeChecklist will throw an exception if a checklist does not exist")
    public void removeChecklistInvalidID_ThrowException() throws Exception {
        Assertions.assertThrows(Exception.class, ()->{
            sut.removeChecklist(UUID.randomUUID());
        });
    }

    @Test
    @Description("removeChecklist will throw an exception if ID is null")
    public void removeChecklistNullID_ThrowException() throws Exception {
        Assertions.assertThrows(Exception.class, ()->{
            sut.removeChecklist(null);
        });
    }

    @Test
    @Description("Ensuring a user can add an entry")
    public void addEntryValid_ReturnString() throws Exception {
        Mockito.when(mockChecklistRepository.findChecklistById(validChecklistID1)).thenReturn(mockChecklist1);
        Assertions.assertEquals(sut.addChecklistEntry("Mock", UUID.randomUUID(),validChecklistID1),"Checklist Entry successfully added");
    }

    @Test
    @Description("addChecklistEntry will throw an exception if a checklist entry with the same ID already exists")
    public void addEntryExistingID_ThrowException() throws Exception {
        Mockito.when(mockChecklistRepository.findChecklistById(validChecklistID1)).thenReturn(mockChecklist1);
        Assertions.assertThrows(Exception.class, ()->{
            sut.addChecklistEntry("Mock", validEntryID1,validChecklistID1);
        });
    }

    @Test
    @Description("addChecklistEntry will throw an exception if a field is null")
    public void addEntryNullField_ThrowException() throws Exception {
        Assertions.assertThrows(Exception.class, ()->{
            sut.addChecklistEntry("Mock", validEntryID1,null);
        });
    }

    @Test
    @Description("Ensuring a user can remove an entry")
    public void removeEntryValid_ReturnString() throws Exception {
        Mockito.when(mockChecklistRepository.findChecklistById(validChecklistID1)).thenReturn(mockChecklist1);
        Assertions.assertEquals(sut.removeChecklistEntry(validEntryID1,validChecklistID1),"Checklist Entry successfully removed");
    }

    @Test
    @Description("removeChecklistEntry will throw an exception if a checklist entry does not exist")
    public void removeEntryInvalidID_ThrowException() throws Exception {
        Mockito.when(mockChecklistRepository.findChecklistById(validChecklistID1)).thenReturn(mockChecklist1);
        Assertions.assertThrows(Exception.class, ()->{
            sut.removeChecklistEntry(UUID.randomUUID(),validChecklistID1);
        });
    }

    @Test
    @Description("removeChecklistEntry will throw an exception if a field is null")
    public void removeEntryNullField_ThrowException() throws Exception {
        Assertions.assertThrows(Exception.class, ()->{
            sut.removeChecklistEntry(null,validChecklistID1);
        });
    }

}
