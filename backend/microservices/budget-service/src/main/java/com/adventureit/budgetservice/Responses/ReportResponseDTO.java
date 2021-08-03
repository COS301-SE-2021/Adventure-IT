package com.adventureit.budgetservice.Responses;

public class ReportResponseDTO {
    String payeeName;
    double amount;

    public ReportResponseDTO(String payeeName, double amount){
        this.amount = amount;
        this.payeeName = payeeName;
    }

    public double getAmount() {
        return amount;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }
}
