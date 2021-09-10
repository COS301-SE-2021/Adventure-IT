package com.adventureit.shareddtos.budget.responses;

public class ReportResponseDTO {
    String payeeName;
    Double amount;

    public ReportResponseDTO(){}

    public ReportResponseDTO(String payeeName, Double amount){
        this.amount = amount;
        this.payeeName = payeeName;
    }

    public String getPayeeName() {
        return payeeName;
    }


    public Double getAmount() {
        return amount;
    }

}
