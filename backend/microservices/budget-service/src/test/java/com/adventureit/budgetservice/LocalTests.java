package com.adventureit.budgetservice;

import com.adventureit.budgetservice.Entity.Budget;
import com.adventureit.budgetservice.Entity.Category;
import com.adventureit.budgetservice.Entity.UTOExpense;
import com.adventureit.budgetservice.Entity.UTUExpense;
import com.adventureit.budgetservice.Repository.BudgetEntryRepository;
import com.adventureit.budgetservice.Repository.BudgetRepository;
import com.adventureit.budgetservice.Service.BudgetServiceImplementation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class LocalTests {
    @Autowired
    private BudgetServiceImplementation budgetServiceImplementation;
    @Autowired
    BudgetRepository budgetRepository;
    @Autowired
    BudgetEntryRepository budgetEntryRepository;

    UUID mockID1 = UUID.fromString("8b276cf3-a3b0-4e37-9d08-a454ea0010a3");
    UUID mockID2 = UUID.fromString("08fd3d65-8583-42bc-93d9-e240af3f7785");
    UUID mockID3 = UUID.fromString("792f8277-8eb1-448f-b790-7f174599a581");
    UUID mockID4 = UUID.fromString("c90e3b11-2878-4d11-a237-a6bce4d5177c");
    UUID budgetID = UUID.fromString("917eab70-398f-459a-80eb-0ee1ef47cf9b");

    @Test
    public void populate(){
        budgetRepository.save(new Budget(UUID.randomUUID(), "B1", "Mock", mockID1, UUID.randomUUID()));
    }

    @Test
    public void addEntries(){
        budgetEntryRepository.save(new UTOExpense(UUID.randomUUID(), budgetID, 500.0,  "Mock 6",  "Mock UTO", Category.Accommodation, new ArrayList<UUID>(List.of(mockID1)), "Hotel"));
        budgetEntryRepository.save(new UTOExpense(UUID.randomUUID(), budgetID, 1000.0,  "Mock 7",  "Mock UTO", Category.Transport, new ArrayList<UUID>(List.of(mockID1,mockID2)), "Car Service"));
        budgetEntryRepository.save(new UTUExpense(UUID.randomUUID(), budgetID, 200.0,  "Mock 8",  "Mock UTU", Category.Other, new ArrayList<UUID>(List.of(mockID1,mockID2)), mockID3));
        budgetEntryRepository.save(new UTUExpense(UUID.randomUUID(), budgetID, 1000.0,  "Mock 9",  "Mock UTU", Category.Other, new ArrayList<UUID>(List.of(mockID4,mockID2)), mockID1));
    }

    @Test
    public void calculate() throws Exception {
//        System.out.println(budgetServiceImplementation.calculateExpensesPerUser(budgetID, mockID1));
        System.out.println(budgetServiceImplementation.getEntriesPerCategory(budgetID));
    }

}
