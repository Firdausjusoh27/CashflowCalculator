package com.example.cashflowapp;

public class StockMutualFundCODRecord {
    private int id;
    private String stockType;
    private int buyingPrice;
    private int numOfShares;
    private int monthlyDividends;
    private int monthlyInterest;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getStockType() { return stockType; }

    public void setStockType(String stockType) { this.stockType = stockType; }

    public int getBuyingPrice() { return buyingPrice; }

    public void setBuyingPrice(int buyingPrice) { this.buyingPrice = buyingPrice; }

    public int getNumOfShares() { return numOfShares; }

    public void setNumOfShares(int numOfShares) { this.numOfShares = numOfShares; }

    public int getMonthlyDividends() { return monthlyDividends; }

    public void setMonthlyDividends(int monthlyDividends) { this.monthlyDividends = monthlyDividends; }

    public int getMonthlyInterest() { return monthlyInterest; }

    public void setMonthlyInterest(int monthlyInterest) { this.monthlyInterest = monthlyInterest; }
}
