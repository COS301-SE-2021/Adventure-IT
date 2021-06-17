package com.adventureit.budgetservice.Repository;

import com.adventureit.budgetservice.Entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BudgetRepository extends JpaRepository<Budget,Long> {
        Budget findByID(UUID id);
}

