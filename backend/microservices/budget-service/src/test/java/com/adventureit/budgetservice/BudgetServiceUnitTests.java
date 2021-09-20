package com.adventureit.budgetservice;

import com.adventureit.budgetservice.entity.*;
import com.adventureit.budgetservice.repository.BudgetEntryRepository;
import com.adventureit.budgetservice.repository.BudgetReportRepository;
import com.adventureit.budgetservice.repository.BudgetRepository;
import com.adventureit.shareddtos.budget.Category;
import com.adventureit.shareddtos.budget.requests.EditBudgetRequest;
import com.adventureit.shareddtos.budget.requests.SoftDeleteRequest;
import com.adventureit.budgetservice.service.BudgetServiceImplementation;
import com.adventureit.shareddtos.budget.responses.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

class BudgetServiceUnitTests {

        private final BudgetRepository mockBudgetRepository = Mockito.mock(BudgetRepository.class);
        private final BudgetEntryRepository mockBudgetEntryRepository = Mockito.mock(BudgetEntryRepository.class);
        private final BudgetReportRepository reportRepo = Mockito.mock(BudgetReportRepository.class);
        private final BudgetServiceImplementation sut = new BudgetServiceImplementation(mockBudgetRepository,mockBudgetEntryRepository, reportRepo);

        final UUID validBudgetID1 = UUID.randomUUID();
        final UUID validBudgetID2 = UUID.randomUUID();
        final UUID validEntryID1 = UUID.randomUUID();
        final UUID validAdventureID1 = UUID.randomUUID();
        final UUID validUserID1 = UUID.randomUUID();


        BudgetEntry mockEntry1 = new UTOExpense(validEntryID1,validBudgetID1,200.0,"Mock Entry 1","Mock UTO Entry", Category.ACCOMMODATION,"User1","Shop1");
        Budget mockBudget1 = new Budget(validBudgetID1,"Mock Budget 1", "Mock budget 1 description",validUserID1,validAdventureID1);
        Budget mockBudget2 = new Budget(validBudgetID2,"Mock Budget 2", "Mock budget 2 description",validUserID1,validAdventureID1);


        @Test
        /* Ensuring a user can create a budget */
        void createBudgetValid_ReturnTrue() throws Exception {
                CreateBudgetResponse createBudgetResponse = sut.createBudget("Mock Name", "Mock description",UUID.randomUUID(),UUID.randomUUID());
                Assertions.assertTrue(createBudgetResponse.isSuccess());
        }

        @Test
        /* createBudget will throw an exception if any of the request object fields are null */
        void createBudgetNullField_ThrowException() throws Exception {
                Assertions.assertThrows(Exception.class, ()->{
                    sut.createBudget(null,"Mock description",UUID.randomUUID(),UUID.randomUUID());
                });
        }

        @Test
        /* Users should be able to add an Income Entry into an Existing Budget */
        void addUTOEntryValid_ReturnTrue() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetID(validBudgetID1)).thenReturn(mockBudget1);
                AddUTOExpenseEntryResponse response = sut.addUTOExpenseEntry(validBudgetID1,200.00,"Mock Income Entry","Mock Description",Category.ACCOMMODATION,"User1","Shop1");
                Assertions.assertTrue(response.isSuccess());
        }

        @Test
        /* Add Income entry method will throw an exception if the budget does not exist */
        void addUTOEntryInvalidBudget_ThrowsException() throws Exception {
                Assertions.assertThrows(Exception.class, ()->{
                        sut.addUTOExpenseEntry(UUID.randomUUID(),200.00,"Mock Income Entry","Mock Description",Category.ACCOMMODATION,"User1","Shop1");
                });
        }

        @Test
        /* Add Income entry method will throw an exception if any of the fields in the request object is null */
        void addUTOEntryNullFields_ThrowsException() throws Exception {
                Assertions.assertThrows(Exception.class, ()->{
                        sut.addUTOExpenseEntry(null,200.00,"Mock Income Entry","Mock Description",Category.ACCOMMODATION,"User1","Shop1");
                });
        }

        @Test
        /* Users should be able to add an Expense Entry into an Existing Budget */
        void addUTUEntryValid_ReturnTrue() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetID(validBudgetID1)).thenReturn(mockBudget1);
                AddUTUExpenseEntryResponse response = sut.addUTUExpenseEntry(validBudgetID1,200.00,"Mock Expense Entry","Mock Description",Category.OTHER,"User1","User2");
                Assertions.assertTrue(response.isSuccess());
        }

        @Test
        /* Add Expense entry method will throw an exception if the budget does not exist */
        void addUTUEntryInvalidBudget_ThrowsException() throws Exception {
                Assertions.assertThrows(Exception.class, ()->{
                        sut.addUTUExpenseEntry(UUID.randomUUID(),200.00,"Mock Expense Entry","Mock Description",Category.OTHER,"User1","User2");
                });
        }

        @Test
        /* Add Expense entry method will throw an exception if any of the fields in the request object is null */
        void addUTUEntryNullFields_ThrowsException() throws Exception {
                Assertions.assertThrows(Exception.class, ()->{
                        sut.addUTUExpenseEntry(null,200.00,"Mock Expense Entry","Mock Description",Category.OTHER,"User1","User2");
                });
        }

        @Test
        /* Remove an existing Entry */
        void removeEntryValid_ReturnsTrue() throws Exception {
                Mockito.when(mockBudgetEntryRepository.findBudgetEntryByBudgetEntryID(validEntryID1)).thenReturn(mockEntry1);
                RemoveEntryResponse response = sut.removeEntry(validEntryID1);
                Assertions.assertTrue(response.isSuccess());
        }

        @Test
        /* Remove Entry method throws exception if the Entry doesn't exist */
        void removeEntryInvalidEntry_ThrowsException() throws Exception {
                Assertions.assertThrows(Exception.class, ()->{
                        sut.removeEntry(UUID.randomUUID());
                });
        }

        @Test
        /* Remove Entry method throws exception if any of the fields are null*/
        void removeEntryNullField_ThrowsException() throws Exception {
                Assertions.assertThrows(Exception.class, ()->{
                        sut.removeEntry(null);
                });
        }

        @Test
        /* View an existing budget*/
        void viewBudgetValid_ReturnsResponse() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetID(validBudgetID1)).thenReturn(mockBudget1);
                Mockito.when(mockBudgetRepository.findBudgetByBudgetIDAndDeletedEquals(validBudgetID1,false)).thenReturn(mockBudget1);
                List<ViewBudgetResponse> response = sut.viewBudget(validBudgetID1);
                Assertions.assertNotNull(response);
        }

        @Test
        /* View budget method will throw an exception if the budget does not exist */
        void viewBudgetInvalidBudget_ThrowsException() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetID(validBudgetID1)).thenReturn(mockBudget1);
                Mockito.when(mockBudgetRepository.findBudgetByBudgetIDAndDeletedEquals(validBudgetID1,false)).thenReturn(mockBudget1);
                Assertions.assertThrows(Exception.class, ()->{
                        sut.viewBudget(UUID.randomUUID());
                });
        }

        @Test
        /* View budget method will throw an exception if the budget ID is null */
        void viewBudgetNullBudgetID_ThrowsException() throws Exception {
                Assertions.assertThrows(Exception.class, ()->{
                        sut.viewBudget(null);
                });
        }

        @Test
        /* Edit an existing entry */
        void editBudgetValid_ReturnsTrue() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetID(validBudgetID1)).thenReturn(mockBudget1);
                Mockito.when(mockBudgetEntryRepository.findBudgetEntryByBudgetEntryID(validEntryID1)).thenReturn(mockEntry1);
                EditBudgetRequest request = new EditBudgetRequest(validEntryID1,validUserID1,validBudgetID1,20.0,"Mock Edit1","Mocking editBudget()","OTHER","User2","Shop3");
                EditBudgetResponse response = sut.editBudget(request);
                Assertions.assertTrue(response.isSuccess());
        }

        @Test
        /* Edit budget method will throw an exception if the entry does not exist */
        void editBudgetInvalidEntry_ThrowException() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetID(validBudgetID1)).thenReturn(mockBudget1);
                Mockito.when(mockBudgetEntryRepository.findBudgetEntryByBudgetEntryID(validEntryID1)).thenReturn(mockEntry1);
                EditBudgetRequest request = new EditBudgetRequest(UUID.randomUUID(),validUserID1,validBudgetID1,20.0,"Mock Edit1","Mocking editBudget()","OTHER","User2","Shop3");
                Assertions.assertThrows(Exception.class, ()->{
                        sut.editBudget(request);
                });
        }

        @Test
        /* Edit budget method will throw an exception if the budget does not exist */
        void editBudgetInvalidBudget_ThrowException() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetID(validBudgetID1)).thenReturn(mockBudget1);
                Mockito.when(mockBudgetEntryRepository.findBudgetEntryByBudgetEntryID(validEntryID1)).thenReturn(mockEntry1);
                EditBudgetRequest request = new EditBudgetRequest(validEntryID1,validUserID1,UUID.randomUUID(),20.0,"Mock Edit1","Mocking editBudget()","OTHER","User2","Shop3");
                Assertions.assertThrows(Exception.class, ()->{
                        sut.editBudget(request);
                });
        }

        @Test
        /* Edit budget method will throw an exception if any of the fields is null */
        void editBudgetNullField_ThrowException() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetID(validBudgetID1)).thenReturn(mockBudget1);
                Mockito.when(mockBudgetEntryRepository.findBudgetEntryByBudgetEntryID(validEntryID1)).thenReturn(mockEntry1);
                EditBudgetRequest request = new EditBudgetRequest(null,validUserID1,validBudgetID1,20.0,"Mock Edit1","Mocking editBudget()","OTHER","User2","Shop3");
                Assertions.assertThrows(Exception.class, ()->{
                        sut.editBudget(request);
                });
        }

        @Test
        /* Soft delete method will throw an exception if the budget does not exist */
        void softDeleteInvalidBudget_ThrowException() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetIDAndDeletedEquals(validBudgetID1,false)).thenReturn(mockBudget1);
                SoftDeleteRequest request = new SoftDeleteRequest(UUID.randomUUID(),UUID.randomUUID());
                Assertions.assertThrows(Exception.class, ()->{
                        sut.softDelete(request);
                });
        }

        @Test
        /* Soft delete method will throw an exception if the budget ID is null */
        void softDeleteNullBudgetID_ThrowException() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetIDAndDeletedEquals(validBudgetID1,false)).thenReturn(mockBudget1);
                SoftDeleteRequest request = new SoftDeleteRequest(null,null);
                Assertions.assertThrows(Exception.class, ()->{
                        sut.softDelete(request);
                });
        }

        @Test
        /* hard delete method will throw an exception if the budget does not exist */
        void hardDeleteInvalidBudget_ThrowException() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetIDAndDeletedEquals(validBudgetID2,true)).thenReturn(mockBudget2);
                Assertions.assertThrows(Exception.class, ()->{
                        sut.hardDelete(UUID.randomUUID(),UUID.randomUUID());
                });
        }

        @Test
        /* hard delete method will throw an exception if the budget ID is null */
        void hardDeleteNullBudgetID_ThrowException() throws Exception {
                Mockito.when(mockBudgetRepository.findBudgetByBudgetIDAndDeletedEquals(validBudgetID2,true)).thenReturn(mockBudget2);
                Assertions.assertThrows(Exception.class, ()->{
                        sut.hardDelete(null,null);
                });
        }
}
