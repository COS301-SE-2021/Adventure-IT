package com.adventureit.budgetservice;

import com.adventureit.budgetservice.Entity.Budget;
import com.adventureit.budgetservice.Entity.BudgetEntry;
import com.adventureit.budgetservice.Entity.Expense;
import com.adventureit.budgetservice.Entity.Income;
import com.adventureit.budgetservice.Repository.BudgetEntryRepository;
import com.adventureit.budgetservice.Repository.BudgetRepository;
import com.adventureit.budgetservice.Requests.*;
import com.adventureit.budgetservice.Responses.*;
import com.adventureit.budgetservice.Service.BudgetServiceImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class BudgetServiceUnitTests {

//       /* private final BudgetRepository mockBudgetRepository = Mockito.mock(BudgetRepository.class);
//        private final BudgetEntryRepository mockBudgetEntryRepository = Mockito.mock(BudgetEntryRepository.class);
//        private final BudgetServiceImplementation sut = new BudgetServiceImplementation(mockBudgetRepository,mockBudgetEntryRepository);
//
//        final UUID validBudgetID1 = UUID.randomUUID();
//        final UUID validBudgetID2 = UUID.randomUUID();
//        final UUID validEntryID1 = UUID.randomUUID();
//        final UUID validEntryID2 = UUID.randomUUID();
//
//        BudgetEntry mockEntry1 = new Income(validEntryID1,validBudgetID1,20.0,"Mock Entry 1","Mock Income");
//        BudgetEntry mockEntry2 = new Expense(validEntryID2,validBudgetID2,20.0,"Mock Entry 2","Mock Expense");
//        ArrayList<UUID> trans = new ArrayList<>(Arrays.asList(validEntryID1));
//        ArrayList<UUID> trans2 = new ArrayList<>(Arrays.asList(validEntryID2));
//        Budget mockBudget1 = new Budget(validBudgetID1,"Mock Budget 1", "Mock budget 1 description",UUID.randomUUID(),UUID.randomUUID(),trans,1000000);
//        Budget mockBudget2 = new Budget(validBudgetID2,"Mock Budget 2", "Mock budget 2 description",UUID.randomUUID(),UUID.randomUUID(),trans2,10000000);
//
//        @Test
//        /* Ensuring a user can create a budget */
//        public void createBudgetValid_ReturnTrue() throws Exception {
//                CreateBudgetResponse createBudgetResponse = sut.createBudget(UUID.randomUUID(),"Mock Name", "Mock description",UUID.randomUUID(),UUID.randomUUID(),5000);
//                Assertions.assertTrue(createBudgetResponse.isSuccess());
//        }
//
//        @Test
//        /* createBudget will throw an exception if a budget with the same ID already exists */
//        public void createBudgetExistingID_ThrowException() throws Exception {
//                Mockito.when(mockBudgetRepository.findBudgetById(validBudgetID1)).thenReturn(mockBudget1);
//                Assertions.assertThrows(Exception.class, ()->{
//                    sut.createBudget(validBudgetID1,"Mock Name", "Mock description",UUID.randomUUID(),UUID.randomUUID(),10000);
//                });
//        }
//
//        @Test
//        /* createBudget will throw an exception if any of the request object fields are null */
//        public void createBudgetNullField_ThrowException() throws Exception {
//                Assertions.assertThrows(Exception.class, ()->{
//                    sut.createBudget(null,"Mock Name", "Mock description",UUID.randomUUID(),UUID.randomUUID(),15000);
//                });
//        }
//
//        @Test
//        /* Users should be able to add an Income Entry into an Existing Budget */
//        public void addIncomeEntryValid_ReturnTrue() throws Exception {
//                Mockito.when(mockBudgetRepository.findBudgetById(validBudgetID1)).thenReturn(mockBudget1);
//                AddIncomeEntryResponse response = sut.addIncomeEntry(UUID.randomUUID(),validBudgetID1,200.00,"Mock Income Entry","Mock Description");
//                Assertions.assertTrue(response.isSuccess());
//        }
//
//        @Test
//        /* Add Income entry method will throw an exception if an Entry with the same ID already exists */
//        public void addIncomeEntryExistingID_ThrowsException() throws Exception {
//                Mockito.when(mockBudgetRepository.findBudgetById(validBudgetID1)).thenReturn(mockBudget1);
//                Assertions.assertThrows(Exception.class, ()->{
//                        sut.addIncomeEntry(validEntryID1,validBudgetID1,200.00,"Mock Income Entry","Mock Description");
//                });
//        }
//
//        @Test
//        /* Add Income entry method will throw an exception if the budget does not exist */
//        public void addIncomeEntryInvalidBudget_ThrowsException() throws Exception {
//                Assertions.assertThrows(Exception.class, ()->{
//                        sut.addIncomeEntry(UUID.randomUUID(),UUID.randomUUID(),200.00,"Mock Income Entry","Mock Description");
//                });
//        }
//
//        @Test
//        /* Add Income entry method will throw an exception if any of the fields in the request object is null */
//        public void addIncomeEntryNullFields_ThrowsException() throws Exception {
//                Assertions.assertThrows(Exception.class, ()->{
//                        sut.addIncomeEntry(null,UUID.randomUUID(),200.00,"Mock Income Entry","Mock Description");
//                });
//        }
//
//        @Test
//        /* Users should be able to add an Expense Entry into an Existing Budget */
//        public void addExpenseEntryValid_ReturnTrue() throws Exception {
//                Mockito.when(mockBudgetRepository.findBudgetById(validBudgetID1)).thenReturn(mockBudget1);
//                AddExpenseEntryResponse response = sut.addExpenseEntry(UUID.randomUUID(),validBudgetID1,200.00,"Mock Expense Entry","Mock Description");
//                Assertions.assertTrue(response.isSuccess());
//        }
//
//        @Test
//        /* Add Expense entry method will throw an exception if an Entry with the same ID already exists */
//        public void addExpenseEntryExistingID_ThrowsException() throws Exception {
//                Mockito.when(mockBudgetRepository.findBudgetById(validBudgetID2)).thenReturn(mockBudget2);
//                Assertions.assertThrows(Exception.class, ()->{
//                        sut.addExpenseEntry(validEntryID2,validBudgetID1,200.00,"Mock Expense Entry","Mock Description");
//                });
//        }
//
//        @Test
//        /* Add Expense entry method will throw an exception if the budget does not exist */
//        public void addExpenseEntryInvalidBudget_ThrowsException() throws Exception {
//                Assertions.assertThrows(Exception.class, ()->{
//                        sut.addExpenseEntry(UUID.randomUUID(),UUID.randomUUID(),200.00,"Mock Expense Entry","Mock Description");
//                });
//        }
//
//        @Test
//        /* Add Expense entry method will throw an exception if any of the fields in the request object is null */
//        public void addExpenseEntryNullFields_ThrowsException() throws Exception {
//                Assertions.assertThrows(Exception.class, ()->{
//                        sut.addExpenseEntry(null,UUID.randomUUID(),200.00,"Mock Expense Entry","Mock Description");
//                });
//        }
//
//        @Test
//        /* Remove an existing Entry */
//        public void removeEntryValid_ReturnsTrue() throws Exception {
//                Mockito.when(mockBudgetRepository.findBudgetById(validBudgetID1)).thenReturn(mockBudget1);
//                RemoveEntryResponse response = sut.removeEntry(validEntryID1,validBudgetID1);
//                Assertions.assertTrue(response.isSuccess());
//        }
//
//        @Test
//        /* Remove Entry method throws exception if the Entry doesn't exist */
//        public void removeEntryInvalidEntry_ThrowsException() throws Exception {
//                Assertions.assertThrows(Exception.class, ()->{
//                        sut.removeEntry(UUID.randomUUID(),validBudgetID1);
//                });
//        }
//
//        @Test
//        /* Remove Entry method throws exception if the Budget does not exist*/
//        public void removeEntryInvalidBudget_ThrowsException() throws Exception {
//                Assertions.assertThrows(Exception.class, ()->{
//                        sut.removeEntry(validEntryID2,UUID.randomUUID());
//                });
//        }
//
//        @Test
//        /* Remove Entry method throws exception if any of the fields are null*/
//        public void removeEntryNullField_ThrowsException() throws Exception {
//                Assertions.assertThrows(Exception.class, ()->{
//                        sut.removeEntry(validEntryID2,null);
//                });
//        }
//
//        @Test
//        /* View an existing budget*/
//        public void viewBudgetValid_ReturnsTrue() throws Exception {
//                Mockito.when(mockBudgetRepository.findBudgetById(validBudgetID1)).thenReturn(mockBudget1);
//                Mockito.when(mockBudgetRepository.findBudgetByIdAndDeletedEquals(validBudgetID1,false)).thenReturn(mockBudget1);
//                BudgetResponseDTO response = sut.viewBudget(validBudgetID1);
//                Assertions.assertTrue(response != null);
//        }
//
//        @Test
//        /* View budget method will throw an exception if the budget does not exist */
//        public void viewBudgetInvalidBudget_ThrowsException() throws Exception {
//                Mockito.when(mockBudgetRepository.findBudgetById(validBudgetID1)).thenReturn(mockBudget1);
//                Mockito.when(mockBudgetRepository.findBudgetByIdAndDeletedEquals(validBudgetID1,false)).thenReturn(mockBudget1);
//                Assertions.assertThrows(Exception.class, ()->{
//                        sut.viewBudget(UUID.randomUUID());
//                });
//        }
//
//        @Test
//        /* View budget method will throw an exception if the budget ID is null */
//        public void viewBudgetNullBudgetID_ThrowsException() throws Exception {
//                Assertions.assertThrows(Exception.class, ()->{
//                        sut.viewBudget(null);
//                });
//        }
//
//        @Test
//        /* Edit an existing entry */
//        public void editBudgetValid_ReturnsTrue() throws Exception {
//                Mockito.when(mockBudgetRepository.findBudgetById(validBudgetID1)).thenReturn(mockBudget1);
//                Mockito.when(mockBudgetEntryRepository.findBudgetEntryById(validEntryID1)).thenReturn(mockEntry1);
//                EditBudgetRequest request = new EditBudgetRequest(validEntryID1,validBudgetID1,20.0,"Mock Edit1","Mocking editBudget()");
//                EditBudgetResponse response = sut.editBudget(request);
//                Assertions.assertTrue(response.isSuccess());
//        }
//
//        @Test
//        /* Edit budget method will throw an exception if the entry does not exist */
//        public void editBudgetInvalidEntry_ThrowException() throws Exception {
//                Mockito.when(mockBudgetRepository.findBudgetById(validBudgetID1)).thenReturn(mockBudget1);
//                Mockito.when(mockBudgetEntryRepository.findBudgetEntryById(validEntryID1)).thenReturn(mockEntry1);
//                EditBudgetRequest request = new EditBudgetRequest(UUID.randomUUID(),validBudgetID1,20.0,"Mock Edit1","Mocking editBudget()");
//                Assertions.assertThrows(Exception.class, ()->{
//                        sut.editBudget(request);
//                });
//        }
//
//        @Test
//        /* Edit budget method will throw an exception if the budget does not exist */
//        public void editBudgetInvalidBudget_ThrowException() throws Exception {
//                Mockito.when(mockBudgetRepository.findBudgetById(validBudgetID1)).thenReturn(mockBudget1);
//                Mockito.when(mockBudgetEntryRepository.findBudgetEntryById(validEntryID1)).thenReturn(mockEntry1);
//                EditBudgetRequest request = new EditBudgetRequest(validEntryID1,UUID.randomUUID(),20.0,"Mock Edit1","Mocking editBudget()");
//                Assertions.assertThrows(Exception.class, ()->{
//                        sut.editBudget(request);
//                });
//        }
//
//        @Test
//        /* Edit budget method will throw an exception if any of the fields is null */
//        public void editBudgetNullField_ThrowException() throws Exception {
//                Mockito.when(mockBudgetRepository.findBudgetById(validBudgetID1)).thenReturn(mockBudget1);
//                Mockito.when(mockBudgetEntryRepository.findBudgetEntryById(validEntryID1)).thenReturn(mockEntry1);
//                EditBudgetRequest request = new EditBudgetRequest(null,validBudgetID1,20.0,"Mock Edit1","Mocking editBudget()");
//                Assertions.assertThrows(Exception.class, ()->{
//                        sut.editBudget(request);
//                });
//        }
//
//        @Test
//        /* Move budgets to the recycle bin */
//        public void softDeleteValid_ReturnTrue() throws Exception {
//                Mockito.when(mockBudgetRepository.findBudgetByIdAndDeletedEquals(validBudgetID1,false)).thenReturn(mockBudget1);
//                SoftDeleteRequest request = new SoftDeleteRequest(validBudgetID1);
//                SoftDeleteResponse response = sut.softDelete(request);
//                Assertions.assertTrue(response.isSuccess());
//        }
//
//        @Test
//        /* Soft delete method will throw an exception if the budget does not exist */
//        public void softDeleteInvalidBudget_ThrowException() throws Exception {
//                Mockito.when(mockBudgetRepository.findBudgetByIdAndDeletedEquals(validBudgetID1,false)).thenReturn(mockBudget1);
//                SoftDeleteRequest request = new SoftDeleteRequest(UUID.randomUUID());
//                Assertions.assertThrows(Exception.class, ()->{
//                        sut.softDelete(request);
//                });
//        }
//
//        @Test
//        /* Soft delete method will throw an exception if the budget ID is null */
//        public void softDeleteNullBudgetID_ThrowException() throws Exception {
//                Mockito.when(mockBudgetRepository.findBudgetByIdAndDeletedEquals(validBudgetID1,false)).thenReturn(mockBudget1);
//                SoftDeleteRequest request = new SoftDeleteRequest(null);
//                Assertions.assertThrows(Exception.class, ()->{
//                        sut.softDelete(request);
//                });
//        }
//
//        @Test
//        /* Permanently delete budgets in the bin */
//        public void hardDeleteValid_ReturnTrue() throws Exception {
//                Mockito.when(mockBudgetRepository.findBudgetByIdAndDeletedEquals(validBudgetID2,true)).thenReturn(mockBudget2);
//                HardDeleteRequest request = new HardDeleteRequest(validBudgetID2);
//                HardDeleteResponse response = sut.hardDelete(request);
//                Assertions.assertTrue(response.isSuccess());
//        }
//
//        @Test
//        /* hard delete method will throw an exception if the budget does not exist */
//        public void hardDeleteInvalidBudget_ThrowException() throws Exception {
//                Mockito.when(mockBudgetRepository.findBudgetByIdAndDeletedEquals(validBudgetID2,true)).thenReturn(mockBudget2);
//                HardDeleteRequest request = new HardDeleteRequest(UUID.randomUUID());
//                Assertions.assertThrows(Exception.class, ()->{
//                        sut.hardDelete(request);
//                });
//        }
//
//        @Test
//        /* hard delete method will throw an exception if the budget ID is null */
//        public void hardDeleteNullBudgetID_ThrowException() throws Exception {
//                Mockito.when(mockBudgetRepository.findBudgetByIdAndDeletedEquals(validBudgetID2,true)).thenReturn(mockBudget2);
//                HardDeleteRequest request = new HardDeleteRequest(null);
//                Assertions.assertThrows(Exception.class, ()->{
//                        sut.hardDelete(request);
//                });
//        }
}
