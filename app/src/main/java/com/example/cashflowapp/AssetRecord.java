package com.example.cashflowapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AssetRecord extends AppCompatActivity {
    private String assetType;
    private String cost;

    public String getAssetType() { return assetType; }

    public void setAssetType(String assetType) { this.assetType = assetType; }

    public String getCost() { return cost; }

    public void setCost(String cost) { this.cost = cost; }

}