package com.adventureit.budgetservice;

import com.adventureit.budgetservice.Entity.*;
import com.adventureit.budgetservice.Repository.BudgetEntryRepository;
import com.adventureit.budgetservice.Repository.BudgetRepository;
import com.adventureit.budgetservice.Requests.EditBudgetRequest;
import com.adventureit.budgetservice.Requests.SoftDeleteRequest;
import com.adventureit.budgetservice.Responses.*;
import com.adventureit.budgetservice.Service.BudgetServiceImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

public class BudgetServiceUnitTests {

        private final BudgetRepository mockBudgetRepository = Mockito.mock(BudgetRepository.class);
        private final BudgetEntryRepository mockBudgetEntryRepository = Mockito.mock(BudgetEntryRepository.class);
        private final BudgetServiceImplementation sut = new BudgetServiceImplementation(mockBudgetRepository,mockBudgetEntryRepository);

        final UUID validBudgetID1 = UUID.randomUUID();
        final UUID validBudgetID2 = UUID.randomUUID();
        final UUID validEntryID1 = UUID.randomUUID();
        final UUID validAdventureID1 = UUID.randomUUID();
        final UUID validUserID1 = UUID.randomUUID();

        BudgetEntry mockEntry1 = new UTOExpense(validEntryID1,validBudgetID1,200.0,"Mock Entry 1","Mock UTO Entry", Category.Accommodation,"User1","Shop1");
        Budget mockBudget1 = new Budget(validBudgetID1,"Mock Budget 1", "Mock budget 1 description",validUserID1,validAdventureID1);
        Budget mockBudget2 = new Budget(validBudgetID2,"Mock Budget 2", "Mock budget 2 description",validUserID1,validAdventureID1);


        @Test
        /* Ensuring a user can create a budget */
        public void createBudgetValid_ReturnTrue() throws Exception {
                CreateBudgetResponse createBudgetResponse = sut.createBudget("Mock Name", "Mock description",UUID.randomUUID(),UUID.randomUUID());
                Assertions.assertTrue(createBudgetResponse.isSuccess());
        }

        @Test
        /* createBudget will throw an exception if any of the request object fields are null */
        public void createBudgetNullField_ThrowException() throws Exception {
                Assertions.assertThrows(Exception.class, ()->{
                    sut.createBudget(null,"Mock description",UUID.randomUUID(),UUID.randomUUID());
                });
        }

        @Test
        /* Users should be able to add an Income Entry into an Existing Budget */
        public void addUTOEntryValid_ReturnTrue() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetID(validBudgetID1)).thenReturn(mockBudget1);
                AddUTOExpenseEntryResponse response = sut.addUTOExpenseEntry(validBudgetID1,200.00,"Mock Income Entry","Mock Description",Category.Accommodation,"User1","Shop1");
                Assertions.assertTrue(response.isSuccess());
        }

        @Test
        /* Add Income entry method will throw an exception if the budget does not exist */
        public void addUTOEntryInvalidBudget_ThrowsException() throws Exception {
                Assertions.assertThrows(Exception.class, ()->{
                        sut.addUTOExpenseEntry(UUID.randomUUID(),200.00,"Mock Income Entry","Mock Description",Category.Accommodation,"User1","Shop1");
                });
        }

        @Test
        /* Add Income entry method will throw an exception if any of the fields in the request object is null */
        public void addUTOEntryNullFields_ThrowsException() throws Exception {
                Assertions.assertThrows(Exception.class, ()->{
                        sut.addUTOExpenseEntry(null,200.00,"Mock Income Entry","Mock Description",Category.Accommodation,"User1","Shop1");
                });
        }

        @Test
        /* Users should be able to add an Expense Entry into an Existing Budget */
        public void addUTUEntryValid_ReturnTrue() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetID(validBudgetID1)).thenReturn(mockBudget1);
                AddUTUExpenseEntryResponse response = sut.addUTUExpenseEntry(validBudgetID1,200.00,"Mock Expense Entry","Mock Description",Category.Other,"User1","User2");
                Assertions.assertTrue(response.isSuccess());
        }

        @Test
        /* Add Expense entry method will throw an exception if the budget does not exist */
        public void addUTUEntryInvalidBudget_ThrowsException() throws Exception {
                Assertions.assertThrows(Exception.class, ()->{
                        sut.addUTUExpenseEntry(UUID.randomUUID(),200.00,"Mock Expense Entry","Mock Description",Category.Other,"User1","User2");
                });
        }

        @Test
        /* Add Expense entry method will throw an exception if any of the fields in the request object is null */
        public void addUTUEntryNullFields_ThrowsException() throws Exception {
                Assertions.assertThrows(Exception.class, ()->{
                        sut.addUTUExpenseEntry(null,200.00,"Mock Expense Entry","Mock Description",Category.Other,"User1","User2");
                });
        }

        @Test
        /* Remove an existing Entry */
        public void removeEntryValid_ReturnsTrue() throws Exception {
                Mockito.when(mockBudgetEntryRepository.findBudgetEntryByBudgetEntryID(validEntryID1)).thenReturn(mockEntry1);
                RemoveEntryResponse response = sut.removeEntry(validEntryID1);
                Assertions.assertTrue(response.isSuccess());
        }

        @Test
        /* Remove Entry method throws exception if the Entry doesn't exist */
        public void removeEntryInvalidEntry_ThrowsException() throws Exception {
                Assertions.assertThrows(Exception.class, ()->{
                        sut.removeEntry(UUID.randomUUID());
                });
        }

        @Test
        /* Remove Entry method throws exception if any of the fields are null*/
        public void removeEntryNullField_ThrowsException() throws Exception {
                Assertions.assertThrows(Exception.class, ()->{
                        sut.removeEntry(null);
                });
        }

        @Test
        /* View an existing budget*/
        public void viewBudgetValid_ReturnsTrue() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetID(validBudgetID1)).thenReturn(mockBudget1);
                Mockito.when(mockBudgetRepository.findBudgetByBudgetIDAndDeletedEquals(validBudgetID1,false)).thenReturn(mockBudget1);
                List<ViewBudgetResponse> response = sut.viewBudget(validBudgetID1);
                Assertions.assertTrue(response != null);
        }

        @Test
        /* View budget method will throw an exception if the budget does not exist */
        public void viewBudgetInvalidBudget_ThrowsException() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetID(validBudgetID1)).thenReturn(mockBudget1);
                Mockito.when(mockBudgetRepository.findBudgetByBudgetIDAndDeletedEquals(validBudgetID1,false)).thenReturn(mockBudget1);
                Assertions.assertThrows(Exception.class, ()->{
                        sut.viewBudget(UUID.randomUUID());
                });
        }

        @Test
        /* View budget method will throw an exception if the budget ID is null */
        public void viewBudgetNullBudgetID_ThrowsException() throws Exception {
                Assertions.assertThrows(Exception.class, ()->{
                        sut.viewBudget(null);
                });
        }

        @Test
        /* Edit an existing entry */
        public void editBudgetValid_ReturnsTrue() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetID(validBudgetID1)).thenReturn(mockBudget1);
                Mockito.when(mockBudgetEntryRepository.findBudgetEntryByBudgetEntryID(validEntryID1)).thenReturn(mockEntry1);
                EditBudgetRequest request = new EditBudgetRequest(validEntryID1,validBudgetID1,20.0,"Mock Edit1","Mocking editBudget()","User2","Shop3");
                EditBudgetResponse response = sut.editBudget(request);
                Assertions.assertTrue(response.isSuccess());
        }

        @Test
        /* Edit budget method will throw an exception if the entry does not exist */
        public void editBudgetInvalidEntry_ThrowException() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetID(validBudgetID1)).thenReturn(mockBudget1);
                Mockito.when(mockBudgetEntryRepository.findBudgetEntryByBudgetEntryID(validEntryID1)).thenReturn(mockEntry1);
                EditBudgetRequest request = new EditBudgetRequest(UUID.randomUUID(),validBudgetID1,20.0,"Mock Edit1","Mocking editBudget()","User2","Shop3");
                Assertions.assertThrows(Exception.class, ()->{
                        sut.editBudget(request);
                });
        }

        @Test
        /* Edit budget method will throw an exception if the budget does not exist */
        public void editBudgetInvalidBudget_ThrowException() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetID(validBudgetID1)).thenReturn(mockBudget1);
                Mockito.when(mockBudgetEntryRepository.findBudgetEntryByBudgetEntryID(validEntryID1)).thenReturn(mockEntry1);
                EditBudgetRequest request = new EditBudgetRequest(validEntryID1,UUID.randomUUID(),20.0,"Mock Edit1","Mocking editBudget()","User2","Shop3");
                Assertions.assertThrows(Exception.class, ()->{
                        sut.editBudget(request);
                });
        }

        @Test
        /* Edit budget method will throw an exception if any of the fields is null */
        public void editBudgetNullField_ThrowException() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetID(validBudgetID1)).thenReturn(mockBudget1);
                Mockito.when(mockBudgetEntryRepository.findBudgetEntryByBudgetEntryID(validEntryID1)).thenReturn(mockEntry1);
                EditBudgetRequest request = new EditBudgetRequest(null,validBudgetID1,20.0,"Mock Edit1","Mocking editBudget()","User2","Shop3");
                Assertions.assertThrows(Exception.class, ()->{
                        sut.editBudget(request);
                });
        }

        @Test
        /* Soft delete method will throw an exception if the budget does not exist */
        public void softDeleteInvalidBudget_ThrowException() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetIDAndDeletedEquals(validBudgetID1,false)).thenReturn(mockBudget1);
                SoftDeleteRequest request = new SoftDeleteRequest(UUID.randomUUID(),UUID.randomUUID());
                Assertions.assertThrows(Exception.class, ()->{
                        sut.softDelete(request);
                });
        }

        @Test
        /* Soft delete method will throw an exception if the budget ID is null */
        public void softDeleteNullBudgetID_ThrowException() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetIDAndDeletedEquals(validBudgetID1,false)).thenReturn(mockBudget1);
                SoftDeleteRequest request = new SoftDeleteRequest(null,null);
                Assertions.assertThrows(Exception.class, ()->{
                        sut.softDelete(request);
                });
        }

        @Test
        /* hard delete method will throw an exception if the budget does not exist */
        public void hardDeleteInvalidBudget_ThrowException() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetIDAndDeletedEquals(validBudgetID2,true)).thenReturn(mockBudget2);
                Assertions.assertThrows(Exception.class, ()->{
                        sut.hardDelete(UUID.randomUUID(),UUID.randomUUID());
                });
        }

        @Test
        /* hard delete method will throw an exception if the budget ID is null */
        public void hardDeleteNullBudgetID_ThrowException() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetIDAndDeletedEquals(validBudgetID2,true)).thenReturn(mockBudget2);
                Assertions.assertThrows(Exception.class, ()->{
                        sut.hardDelete(null,null);
                });
        }
}
