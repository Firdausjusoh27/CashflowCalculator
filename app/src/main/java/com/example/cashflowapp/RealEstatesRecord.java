package com.example.cashflowapp;

public class RealEstatesRecord {
    private int id;
    private String realEstateType;
    private int cost;
    private int downPayment;
    private int cashFlow;
    private int numberOfUnits;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getRealEstateType() { return realEstateType; }

    public void setRealEstateType(String realEstateType) { this.realEstateType = realEstateType; }

    public int getCost() { return cost; }

    public void setCost(int cost) { this.cost = cost; }

    public int getDownPayment() { return downPayment; }

    public void setDownPayment(int downPayment) { this.downPayment = downPayment; }

    public int getCashFlow() { return cashFlow; }

    public void setCashFlow(int cashFlow) { this.cashFlow = cashFlow; }

    public int getNumberOfUnits() { return numberOfUnits; }

    public void setNumberOfUnits(int numberOfUnits) { this.numberOfUnits = numberOfUnits; }
}
