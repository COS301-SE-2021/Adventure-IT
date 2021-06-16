package com.adventureit.budgetservice.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.UUID;

@Entity
public class Budget {
    @Id
    private UUID id;
    @OneToMany
    ArrayList<BudgetEntry> transactions;

    
}
