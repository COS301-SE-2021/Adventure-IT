package com.adventureit.budgetservice;

import com.adventureit.adventureservice.Entity.Adventure;
import com.adventureit.adventureservice.Repository.AdventureRepository;
import com.adventureit.adventureservice.Service.AdventureServiceImplementation;
import com.adventureit.budgetservice.Entity.Budget;
import com.adventureit.budgetservice.Entity.BudgetEntry;
import com.adventureit.budgetservice.Entity.Expense;
import com.adventureit.budgetservice.Entity.Income;
import com.adventureit.budgetservice.Repository.BudgetEntryRepository;
import com.adventureit.budgetservice.Repository.BudgetRepository;
import com.adventureit.budgetservice.Requests.CreateBudgetRequest;
import com.adventureit.budgetservice.Responses.CreateBudgetResponse;
import com.adventureit.budgetservice.Service.BudgetService;
import com.adventureit.budgetservice.Service.BudgetServiceImplementation;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;

public class BudgetServiceUnitTests {

        private AutoCloseable autoCloseable;
        private final BudgetRepository budgetRepository = Mockito.mock(BudgetRepository.class);
        private final BudgetEntryRepository budgetEntryRepository = Mockito.mock(BudgetEntryRepository.class);
        private final BudgetServiceImplementation budgetServiceImplementation = new BudgetServiceImplementation(budgetRepository,budgetEntryRepository);

        final UUID validBudgetID1 = UUID.randomUUID();
        final UUID validEntryID1 = UUID.randomUUID();
        final UUID validAdventureID1 = UUID.randomUUID();

        BudgetEntry mockEntry1 = new Income(UUID.randomUUID(),20.0,"Mock Entry 1","Mock Income");
        BudgetEntry mockEntry2 = new Expense(UUID.randomUUID(),20.0,"Mock Entry 2","Mock Expense");
        ArrayList<BudgetEntry> trans = new ArrayList<>(Arrays.asList(mockEntry1,mockEntry2));
        Budget mockBudget1 = new Budget(validBudgetID1,validAdventureID1,trans);

        @BeforeEach
        void setup() {
                autoCloseable = MockitoAnnotations.openMocks(this);
                budgetRepository.save(mockBudget1);
        }

        @AfterEach
        void tearDown() throws Exception {
                autoCloseable.close();
        }

        @Test
//        @Description("Ensuring that a user can create an adventure")
        public void createAdventure_ReturnSuccessTrue() throws Exception {
                Mockito.when(budgetRepository.findBudgetById(validBudgetID1)).thenReturn(mockBudget1);
                CreateBudgetRequest createBudgetRequest = new CreateBudgetRequest(UUID.randomUUID(),UUID.randomUUID(),new ArrayList<BudgetEntry>());
                CreateBudgetResponse createBudgetResponse = budgetServiceImplementation.createBudget(createBudgetRequest);
                Assertions.assertEquals(createBudgetResponse.isSuccess(),true);
        }

        @Test
        public void createBudget_ThrowException() throws Exception {
                Mockito.when(budgetRepository.findBudgetById(validBudgetID1)).thenReturn(mockBudget1);
                CreateBudgetRequest createBudgetRequest = new CreateBudgetRequest(validBudgetID1,UUID.randomUUID(),trans);
                Assertions.assertThrows(Exception.class, ()->{
                        CreateBudgetResponse createBudgetResponse = budgetServiceImplementation.createBudget(createBudgetRequest);
                });
        }

//        @Test
//        public void addEntry_ThrowException() throws Exception {
//                Mockito.when(budgetRepository.findBudgetById(validBudgetID1)).thenReturn(mockBudget1);
//                CreateBudgetRequest createBudgetRequest = new CreateBudgetRequest(validBudgetID1,UUID.randomUUID(),trans);
//                Assertions.assertThrows(Exception.class, ()->{
//                        CreateBudgetResponse createBudgetResponse = budgetServiceImplementation.createBudget(createBudgetRequest);
//                });
//        }



//        @Test
//        @Description("Ensuring that a user who has not created any adventures cannot view any adventures")
//        public void creatorNoAdventures_ReturnNoAdventureFound(){
//            Assertions.assertThrows(AdventureNotFoundException.class, ()->{
//                GetAdventuresByUserUUIDResponse res = adventureService.getAdventureByOwnerUUID(validUserID2);
//            });
//        }
//
//        @Test
//        @Description("Ensuring that an attendee of multiple adventures can view these adventures")
//        public void attendeeExistingAdventures_ReturnAdventures(){
//            Mockito.when(adventureRepository.findByAttendees(validUserID2)).thenReturn(List.of(mockAdventure1, mockAdventure2));
//
//            GetAdventuresByUserUUIDResponse res = adventureService.getAdventureByAttendeeUUID(validUserID2);
//            Assertions.assertEquals(res.getAdventures().size(), 2);
//        }
//
//        @Test
//        @Description("Ensuring that an attendee of multiple adventures can view these adventures")
//        public void attendeeExistingAdventures_ReturnNoAdventureFound(){
//            Assertions.assertThrows(AdventureNotFoundException.class, ()->{
//                GetAdventuresByUserUUIDResponse res = adventureService.getAdventureByAttendeeUUID(validUserID2);
//            });
//        }
}
