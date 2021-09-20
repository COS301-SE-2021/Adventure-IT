package com.adventureit.budgetservice.repository;

public interface BudgetReportRepository extends JpaRepository<BudgetEntry,UUID> {
    List<BudgetEntry> findBudgetEntryByEntryContainerID(UUID budgetId);
    BudgetEntry findBudgetEntryByBudgetEntryID(UUID id);
    void removeBudgetEntryByBudgetEntryID(UUID id);
}
