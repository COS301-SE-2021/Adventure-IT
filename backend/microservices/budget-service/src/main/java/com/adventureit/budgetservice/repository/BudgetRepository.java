package com.adventureit.budgetservice.repository;


import com.adventureit.budgetservice.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface BudgetRepository extends JpaRepository<Budget,UUID> {
        Budget findBudgetByBudgetID(UUID id);
        Budget findBudgetByBudgetIDAndDeletedEquals(UUID id,boolean deleted);
        ArrayList<Budget> findAllByDeletedEquals(boolean deleted);
        ArrayList<Budget> findAllByAdventureID(UUID id);
        ArrayList<Budget> findAllByAdventureIDAndDeletedEquals(UUID id,boolean deleted);
}

