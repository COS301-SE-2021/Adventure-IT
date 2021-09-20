package com.adventureit.budgetservice.repository;


import com.adventureit.budgetservice.entity.ReportBudgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public interface BudgetReportRepository extends JpaRepository<ReportBudgetEntity, UUID> {
    List<ReportBudgetEntity> findReportBudgetEntityByEntryContainerID(UUID budgetId);
    ReportBudgetEntity findReportBudgetEntityByBudgetEntryID(UUID id);
    void removeReportBudgetEntityByBudgetEntryID(UUID id);
}
