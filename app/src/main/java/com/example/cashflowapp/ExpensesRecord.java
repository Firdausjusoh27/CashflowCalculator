package com.example.cashflowapp;

public class ExpensesRecord {
    private int id;
    private String expensesType;
    private int expensesAmount;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getExpensesType() { return expensesType; }

    public void setExpensesType(String expensesType) { this.expensesType = expensesType; }

    public int getExpensesAmount() { return expensesAmount; }

    public void setExpensesAmount(int expensesAmount) { this.expensesAmount = expensesAmount; }
}
