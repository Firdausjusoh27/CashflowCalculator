package com.example.cashflowapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AssetsPage extends AppCompatActivity {
//    private TextView stockTypetv, buyingPricetv, numOfSharetv, monthlyDividendtv, monthlyInteresttv;
//    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("  CashFlow Apps");
        actionBar.setIcon(R.drawable.cash_2);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        //        Initialize and assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

//        Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.menuAssets);

//        stockTypetv = findViewById(R.id.assetType);
//        buyingPricetv = findViewById(R.id.buyingPrice);
//        numOfSharetv = findViewById(R.id.numOfShares);
//        monthlyDividendtv = findViewById(R.id.monthlyDividends);

//        nextBtn = findViewById(R.id.nextButton);
//        nextBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                saveStock(v);
//                saveRealEstate(v);
//                saveBusiness(v);
//                saveGold(v);
//                saveLiability(v);
//                Intent intent = new Intent(AssetsPage.this, MainPage.class);
//                startActivity(intent);
//            }
//        });

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



//    public void saveStock(View v) {
//        String stockType;
//        int buyingprice, numberOfShares, monthlyDiv;
//        stockType = stockTypetv.getText().toString();
//        buyingprice = Integer.parseInt(buyingPricetv.getText().toString());
//        numberOfShares = Integer.parseInt(numOfSharetv.getText().toString());
//        monthlyDiv = Integer.parseInt(monthlyDividendtv.getText().toString());
//        StockMutualFundCODRecord stockMutualFundCODRecord = new StockMutualFundCODRecord();
//        stockMutualFundCODRecord.setStockType(stockType);
//        stockMutualFundCODRecord.setBuyingPrice(buyingprice);
//        stockMutualFundCODRecord.setNumOfShares(numberOfShares);
//        stockMutualFundCODRecord.setMonthlyDividends(monthlyDiv);
//        stockMutualFundCODRecord.setMonthlyInterest(0);
//        stockMutualFundCODRecord.setId(1);
//
//        DatabaseHelper dataSource = new DatabaseHelper(this);
//        dataSource.insertStockMutualFundCOD(stockMutualFundCODRecord);
////        dataSource.updateStockMutualFundCOD(stockMutualFundCODRecord);
////        dataSource.deleteStockMutualFundCOD(4);
////        dataSource.deleteAllStockMutualFundCOD();
//
//        this.finish();
//    }
//
//    public void saveRealEstate(View v) {
//        String realEstateType;
//        int cost, downPayment, cashFlow, numberOfUnits;
//        realEstateType = stockTypetv.getText().toString();
//        cost = Integer.parseInt(buyingPricetv.getText().toString());
//        downPayment = Integer.parseInt(numOfSharetv.getText().toString());
//        cashFlow = Integer.parseInt(monthlyDividendtv.getText().toString());
//        RealEstatesRecord realEstatesRecord = new RealEstatesRecord();
//        realEstatesRecord.setRealEstateType(realEstateType);
//        realEstatesRecord.setCost(cost);
//        realEstatesRecord.setDownPayment(downPayment);
//        realEstatesRecord.setCashFlow(cashFlow);
//        realEstatesRecord.setNumberOfUnits(0);
//        realEstatesRecord.setId(1);
//
//        DatabaseHelper dataSource = new DatabaseHelper(this);
//        dataSource.insertRealEstate(realEstatesRecord);
////        dataSource.updateRealEstate(realEstatesRecord);
////        dataSource.deleteRealEstate(3);
////        dataSource.deleteAllRealEstates();
//
//        this.finish();
//    }
//
//    public void saveBusiness(View v) {
//        String businessType;
//        int cost, downPayment, cashFlow;
//        businessType = stockTypetv.getText().toString();
//        cost = Integer.parseInt(buyingPricetv.getText().toString());
//        downPayment = Integer.parseInt(numOfSharetv.getText().toString());
//        cashFlow = Integer.parseInt(monthlyDividendtv.getText().toString());
//        BusinessRecord businessRecord = new BusinessRecord();
//        businessRecord.setBusinessType(businessType);
//        businessRecord.setCost(cost);
//        businessRecord.setDownPayment(downPayment);
//        businessRecord.setCashFlow(cashFlow);
//        businessRecord.setId(1);
//
//        DatabaseHelper dataSource = new DatabaseHelper(this);
//        dataSource.insertBusiness(businessRecord);
////        dataSource.updateBusiness(businessRecord);
////        dataSource.deleteBusiness(3);
////        dataSource.deleteAllBusinesses();
//
//        this.finish();
//    }
//
//    public void saveGold(View v) {
//        String goldType;
//        goldType = stockTypetv.getText().toString();
//        GoldRecord goldRecord = new GoldRecord();
//        goldRecord.setGoldType(goldType);
//
//        DatabaseHelper dataSource = new DatabaseHelper(this);
////        dataSource.insertGold(goldRecord);
////        dataSource.deleteGold(3);
//        dataSource.deleteAllGolds();
//
//        this.finish();
//    }
//
//    public void saveLiability(View v) {
//        String loanType;
//        int loanAmount;
//        loanType = stockTypetv.getText().toString();
//        loanAmount = Integer.parseInt(buyingPricetv.getText().toString());
//        LiabilityRecord liabilityRecord = new LiabilityRecord();
//        liabilityRecord.setLoanType(loanType);
//        liabilityRecord.setLoanAmount(loanAmount);
//        liabilityRecord.setId(1);
//
//        DatabaseHelper dataSource = new DatabaseHelper(this);
//        dataSource.insertLiability(liabilityRecord);
////        dataSource.updateLiability(liabilityRecord);
////        dataSource.deleteLiability(3);
////        dataSource.deleteAllLiabilities();
//
//        this.finish();
//    }
}