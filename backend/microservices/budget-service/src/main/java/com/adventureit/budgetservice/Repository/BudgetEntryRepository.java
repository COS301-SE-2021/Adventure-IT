package com.adventureit.budgetservice.Repository;

import com.adventureit.budgetservice.Entity.Budget;
import com.adventureit.budgetservice.Entity.BudgetEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BudgetEntryRepository extends JpaRepository<BudgetEntry,Long> {
    public BudgetEntry findBudgetEntryById(UUID id );

}
