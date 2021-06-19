package com.adventureit.budgetservice.Repository;


import com.adventureit.budgetservice.Entity.BudgetEntry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BudgetEntryRepository extends JpaRepository<BudgetEntry,Long> {
    public BudgetEntry findBudgetEntryById(UUID id );

}
