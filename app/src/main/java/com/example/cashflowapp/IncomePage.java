package com.example.cashflowapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class IncomePage extends AppCompatActivity {

//    Start : UI Team Navigation & Item Display
    DatabaseHelper dbHelper;
    ListView listViewRecords;

    private TextView salarytv, totalpassivetv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_page);
        dbHelper = new DatabaseHelper(this);
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF51214D"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("  CashFlow Apps");
        actionBar.setIcon(R.drawable.cash_2);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        listViewRecords = (ListView)findViewById(R.id.listViewIncomeRecords);
        salarytv = findViewById(R.id.salaryTV);
        totalpassivetv = findViewById(R.id.total_passiveTV);

        PlayerInfoRecord playerInfoRecord = dbHelper.getPlayerInfo();

        int salary = playerInfoRecord.getSalary();
        salarytv.setHint("SALARY: $"+String.format("%,d", salary));

        int totalPassiveIncome = dbHelper.getCashFlow();
        totalpassivetv.setHint("TOTAL PASSIVE: $"+String.format("%,d", totalPassiveIncome));


        //        Initialize and assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

//        Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.menuIncome);



//        Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuIncome:
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
                        startActivity(new Intent(getApplicationContext(), LiabilitiesPage.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    //    End : UI Team Navigation & Item Display

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    private void updateList() {
        dbHelper = new DatabaseHelper(this);
        List<IncomeRecord> incomeRecords = new ArrayList<>();

        final List<StockMutualFundCODRecord> stockMutualFundCODRecords = dbHelper.getAllPurchasedStocks();
        if (stockMutualFundCODRecords.size() > 0) {
            IncomeRecord stockLabel = new IncomeRecord();
            stockLabel.setAssetType("INTERESTS / DIVIDENDS");
            stockLabel.setCashFlowAmount("");
            incomeRecords.add(stockLabel);
        }
        for (int i = 0; i < stockMutualFundCODRecords.size(); i++) {
            IncomeRecord incomeRecord = new IncomeRecord();
            incomeRecord.setAssetType(stockMutualFundCODRecords.get(i).getStockType());
            if (stockMutualFundCODRecords.get(i).getStockType().equals("2BIG")) {
                incomeRecord.setCashFlowAmount("$"+String.format("%,d", stockMutualFundCODRecords.get(i).getMonthlyDividends()));
                incomeRecords.add(incomeRecord);
            } else if (stockMutualFundCODRecords.get(i).getStockType().equals("Certificate of Deposit")) {
                incomeRecord.setCashFlowAmount("$"+String.format("%,d", stockMutualFundCODRecords.get(i).getMonthlyInterest()));
                incomeRecords.add(incomeRecord);
            } else {
                incomeRecord.setCashFlowAmount("$0");
                incomeRecords.add(incomeRecord);
            }
        }

        IncomeRecord blank = new IncomeRecord();
        blank.setAssetType("");
        blank.setCashFlowAmount("");
        incomeRecords.add(blank);

        final List<RealEstatesRecord> realEstatesRecords = dbHelper.getAllPurchasedRealEstates();
        if (realEstatesRecords.size() > 0) {
            IncomeRecord realEstateLabel = new IncomeRecord();
            realEstateLabel.setAssetType("REAL ESTATE");
            realEstateLabel.setCashFlowAmount("");
            incomeRecords.add(realEstateLabel);
        }
        for (int i = 0; i < realEstatesRecords.size(); i++) {
            IncomeRecord incomeRecord = new IncomeRecord();
            incomeRecord.setAssetType(realEstatesRecords.get(i).getRealEstateType());
            incomeRecord.setCashFlowAmount("$"+String.format("%,d", realEstatesRecords.get(i).getCashFlow()));
            incomeRecords.add(incomeRecord);
        }

        incomeRecords.add(blank);

        final List<BusinessRecord> businessRecords = dbHelper.getAllPurchasedBusinesses();
        if (businessRecords.size() > 0) {
            IncomeRecord businessLabel = new IncomeRecord();
            businessLabel.setAssetType("BUSINESS");
            businessLabel.setCashFlowAmount("");
            incomeRecords.add(businessLabel);
        }
        for (int i = 0; i < businessRecords.size(); i++) {
            IncomeRecord incomeRecord = new IncomeRecord();
            incomeRecord.setAssetType(businessRecords.get(i).getBusinessType());
            incomeRecord.setCashFlowAmount("$"+String.format("%,d", businessRecords.get(i).getCashFlow()));
            incomeRecords.add(incomeRecord);
        }

        incomeRecords.add(blank);
        incomeRecords.add(blank);

        if(incomeRecords.isEmpty()) {
            Toast.makeText(getApplicationContext(), "No record", Toast.LENGTH_SHORT).show();
        }
        IncomeRecordAdapter adapter = new IncomeRecordAdapter(this,
                R.layout.activity_income_record_adapter, incomeRecords);

        listViewRecords.setAdapter(adapter);
    }
}