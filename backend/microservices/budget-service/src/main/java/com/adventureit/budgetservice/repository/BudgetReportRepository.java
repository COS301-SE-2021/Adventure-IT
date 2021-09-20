package com.adventureit.budgetservice.repository;

import com.adventureit.budgetservice.entity.BudgetEntry;
import com.adventureit.budgetservice.entity.ReportBudgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.UUID;

public interface BudgetReportRepository extends JpaRepository<ReportBudgetEntity, UUID> {
    List<ReportBudgetEntity> findBudgetEntryByEntryContainerID(UUID budgetId);
    ReportBudgetEntity findBudgetEntryByBudgetEntryID(UUID id);
    void removeBudgetEntryByBudgetEntryID(UUID id);
}
