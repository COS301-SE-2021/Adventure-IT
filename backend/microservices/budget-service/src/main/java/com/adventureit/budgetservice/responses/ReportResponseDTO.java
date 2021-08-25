package com.adventureit.budgetservice.responses;

public class ReportResponseDTO {
    String payeeName;
    double amount;

    public ReportResponseDTO(String payeeName, double amount){
        this.amount = amount;
        this.payeeName = payeeName;
    }
}
