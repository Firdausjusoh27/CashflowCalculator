package com.example.cashflowapp;

public class LiabilityRecord {
    private int id;
    private String loanType;
    private int loanAmount;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getLoanType() { return loanType; }

    public void setLoanType(String loanType) { this.loanType = loanType; }

    public int getLoanAmount() { return loanAmount; }

    public void setLoanAmount(int loanAmount) { this.loanAmount = loanAmount; }
}
