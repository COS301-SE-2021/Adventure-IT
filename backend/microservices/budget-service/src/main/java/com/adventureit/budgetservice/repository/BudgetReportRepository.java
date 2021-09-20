package com.adventureit.budgetservice.repository;

import com.adventureit.budgetservice.entity.BudgetEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BudgetReportRepository extends JpaRepository<BudgetEntry, UUID> {
    List<BudgetEntry> findBudgetEntryByEntryContainerID(UUID budgetId);
    BudgetEntry findBudgetEntryByBudgetEntryID(UUID id);
    void removeBudgetEntryByBudgetEntryID(UUID id);
}
