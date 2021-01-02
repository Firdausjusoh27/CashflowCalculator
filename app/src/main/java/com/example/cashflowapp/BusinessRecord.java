package com.example.cashflowapp;

public class BusinessRecord {
    private int id;
    private String businessType;
    private int cost, downPayment, cashFlow;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getBusinessType() { return businessType; }

    public void setBusinessType(String businessType) { this.businessType = businessType; }

    public int getCost() { return cost; }

    public void setCost(int cost) { this.cost = cost; }

    public int getDownPayment() { return downPayment; }

    public void setDownPayment(int downPayment) { this.downPayment = downPayment; }

    public int getCashFlow() { return cashFlow; }

    public void setCashFlow(int cashFlow) { this.cashFlow = cashFlow; }

}
