package com.example.cashflowapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class LiabilitiesPage extends AppCompatActivity {
    DatabaseHelper dbHelper;
    ListView listViewRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liabilities_page);
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF51214D"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("  CashFlow Apps");
        actionBar.setIcon(R.drawable.cash_2);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        listViewRecords = (ListView)findViewById(R.id.listViewLiabilityRecords);

        //        Initialize and assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

//        Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.menuLiabilities);

//        Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuIncome:
                        startActivity(new Intent(getApplicationContext(), IncomePage.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.menuHome:
                        startActivity(new Intent(getApplicationContext(), MainPage.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.menuExpenses:
                        startActivity(new Intent(getApplicationContext(), ExpensesPage.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.menuAssets:
                        startActivity(new Intent(getApplicationContext(), AssetsPage.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.menuLiabilities:
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    private void updateList() {
        dbHelper = new DatabaseHelper(this);
        List<AssetRecord> assetRecords = new ArrayList<>();

        final List<LiabilityRecord> liabilityRecords = dbHelper.getAllLoansToPay();
        if (liabilityRecords.size() > 0) {
            AssetRecord basicLoanLabel = new AssetRecord();
            basicLoanLabel.setAssetType("<Credit / Loans>");
            basicLoanLabel.setCost("");
            assetRecords.add(basicLoanLabel);
        }
        for (int i = 0; i < liabilityRecords.size(); i++) {
            AssetRecord assetRecord = new AssetRecord();
            assetRecord.setAssetType(liabilityRecords.get(i).getLoanType());
            assetRecord.setCost("$"+String.format("%,d", liabilityRecords.get(i).getLoanAmount()));
            assetRecords.add(assetRecord);
        }

        AssetRecord blank = new AssetRecord();
        blank.setAssetType("");
        blank.setCost("");
        assetRecords.add(blank);

        final List<RealEstatesRecord> realEstatesRecords = dbHelper.getAllPurchasedRealEstates();
        if (realEstatesRecords.size() > 0) {
            AssetRecord realEstateLabel = new AssetRecord();
            realEstateLabel.setAssetType("<Real Estate>");
            realEstateLabel.setCost("");
            assetRecords.add(realEstateLabel);
        }
        for (int i = 0; i < realEstatesRecords.size(); i++) {
            AssetRecord assetRecord = new AssetRecord();
            assetRecord.setAssetType(realEstatesRecords.get(i).getRealEstateType());
            assetRecord.setCost("$"+String.format("%,d", (realEstatesRecords.get(i).getCost() - realEstatesRecords.get(i).getDownPayment())));
            assetRecords.add(assetRecord);
        }

        assetRecords.add(blank);

        final List<BusinessRecord> businessRecords = dbHelper.getAllPurchasedBusinesses();
        if (businessRecords.size() > 0) {
            AssetRecord businessLabel = new AssetRecord();
            businessLabel.setAssetType("<Business>");
            businessLabel.setCost("");
            assetRecords.add(businessLabel);
        }
        for (int i = 0; i < businessRecords.size(); i++) {
            AssetRecord assetRecord = new AssetRecord();
            assetRecord.setAssetType(businessRecords.get(i).getBusinessType());
            assetRecord.setCost("$"+String.format("%,d", (businessRecords.get(i).getCost() - businessRecords.get(i).getDownPayment())));
            assetRecords.add(assetRecord);
        }

        assetRecords.add(blank);
        assetRecords.add(blank);

        if(assetRecords.isEmpty()) {
            Toast.makeText(getApplicationContext(), "No record", Toast.LENGTH_SHORT).show();
        }
        AssetRecordAdapter adapter = new AssetRecordAdapter(this,
                R.layout.activity_liability_record_adapter, assetRecords);

        listViewRecords.setAdapter(adapter);
    }
}