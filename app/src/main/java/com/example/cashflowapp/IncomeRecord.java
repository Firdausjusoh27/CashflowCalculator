package com.example.cashflowapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class IncomeRecord extends AppCompatActivity {
    private String assetType;
    private String cashFlowAmount;

    public String getAssetType() { return assetType; }

    public void setAssetType(String assetType) { this.assetType = assetType; }

    public String getCashFlowAmount() { return cashFlowAmount; }

    public void setCashFlowAmount(String cashFlowAmount) { this.cashFlowAmount = cashFlowAmount; }
}