//package com.adventureit.budgetservice;
//
//import com.adventureit.budgetservice.Entity.Budget;
//import com.adventureit.budgetservice.Entity.Category;
//import com.adventureit.budgetservice.Entity.UTOExpense;
//import com.adventureit.budgetservice.Entity.UTUExpense;
//import com.adventureit.budgetservice.Repository.BudgetEntryRepository;
//import com.adventureit.budgetservice.Repository.BudgetRepository;
//import com.adventureit.budgetservice.Responses.ReportResponseDTO;
//import com.adventureit.budgetservice.Service.BudgetServiceImplementation;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@SpringBootTest
//public class LocalTests {
//    @Autowired
//    BudgetRepository budgetRepository;
//
//    @Autowired
//    BudgetEntryRepository budgetEntryRepository;
//
//    @Autowired
//    BudgetServiceImplementation budgetServiceImplementation;
//
//    @Test
//    public void createBudget(){
//        budgetRepository.save(new Budget(UUID.fromString("28e1bea5-aa2a-4143-9113-d045197e8f42"), "Budget 1", "Mock", UUID.randomUUID(), UUID.randomUUID()));
//    }
//
//    @Test
//    public void addEntries(){
////        budgetEntryRepository.save(new UTOExpense(UUID.randomUUID(),UUID.fromString("d53a7090-45f1-4eb2-953a-2258841949f8"),200,"Entry1","Mock", Category.Accommodation, new ArrayList<String>(List.of("User1")),"Shop1"));
////        budgetEntryRepository.save(new UTOExpense(UUID.randomUUID(),UUID.fromString("d53a7090-45f1-4eb2-953a-2258841949f8"),500,"Entry2","Mock", Category.Transport, new ArrayList<String>(List.of("User1", "User2")),"Shop2"));
////        budgetEntryRepository.save(new UTUExpense(UUID.randomUUID(),UUID.fromString("d53a7090-45f1-4eb2-953a-2258841949f8"),1000,"Entry3","Mock", Category.Other, new ArrayList<String>(List.of("User1")),"User2"));
////        budgetEntryRepository.save(new UTUExpense(UUID.randomUUID(),UUID.fromString("d53a7090-45f1-4eb2-953a-2258841949f8"),2000,"Entry4","Mock", Category.Other, new ArrayList<String>(List.of("User2","User3")),"User1"));
//    }
//
//    @Test
//    public void deleteEntries() throws Exception {
//        budgetServiceImplementation.removeEntry(UUID.fromString("4512be30-fa81-44a8-b195-fc6b07f5b3b4"));
//    }
//
//    @Test
//    public void getReport() throws Exception {
//        List<String> list = budgetServiceImplementation.getReportList(UUID.fromString("28e1bea5-aa2a-4143-9113-d045197e8f42"));
//        List<ReportResponseDTO> reports = new ArrayList<>();
//        for (String name:list) {
//            reports.addAll(budgetServiceImplementation.generateIndividualReport(name,UUID.fromString("28e1bea5-aa2a-4143-9113-d045197e8f42")));
//        }
//
//        for (ReportResponseDTO r:reports) {
//            System.out.println(r.getPayeeName() + " : " + r.getAmount());
//        }
//    }
//}
