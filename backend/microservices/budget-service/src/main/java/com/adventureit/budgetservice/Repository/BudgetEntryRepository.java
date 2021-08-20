package com.adventureit.budgetservice.Repository;

import com.adventureit.budgetservice.Entity.BudgetEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BudgetEntryRepository extends JpaRepository<BudgetEntry,UUID> {
    List<BudgetEntry> findBudgetEntryByEntryContainerID(UUID budgetId);
    BudgetEntry findBudgetEntryByBudgetEntryID(UUID id);
}
