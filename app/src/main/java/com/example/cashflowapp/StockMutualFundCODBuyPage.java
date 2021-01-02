package com.example.cashflowapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StockMutualFundCODBuyPage extends AppCompatActivity {

    private TextView stocktypetv, buyingpricetv, numberofsharetv, monthlydividendtv, monthlyinteresttv, dividendlabeltv, interestlabeltv, totalpricetv;
    private Button buyStockBtn;
    static String[] stockTypeList = {"2BIG", "Certificate of Deposit", "GRO4US", "OK4U", "ON2U", "MYT4U"};

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_mutual_fund_c_o_d_buy_page);
        databaseHelper = new DatabaseHelper(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("  CashFlow Apps");
        actionBar.setIcon(R.drawable.cash_2);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        stocktypetv = findViewById(R.id.stockTypeTV);
        buyingpricetv = findViewById(R.id.buyingPriceTV);
        numberofsharetv = findViewById(R.id.numOfSharesTV);
        monthlydividendtv = findViewById(R.id.monthlyDividendTV);
        monthlyinteresttv = findViewById(R.id.monthlyInterestTV);
        dividendlabeltv = findViewById(R.id.dividendLabelView);
        interestlabeltv = findViewById(R.id.interestLabelView);
        totalpricetv = findViewById(R.id.totalPriceLabel);


        //        Start -- Alert Dialog for Profession TextView

        stocktypetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ListView listView = new ListView(StockMutualFundCODBuyPage.this);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(StockMutualFundCODBuyPage.this, android.R.layout.simple_list_item_1, stockTypeList);
                listView.setAdapter(adapter);
                AlertDialog.Builder prodialog = new AlertDialog.Builder(StockMutualFundCODBuyPage.this);
                prodialog.setCancelable(true);
                prodialog.setView(listView);
                final AlertDialog dialog = prodialog.create();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedStockType = adapter.getItem(position);
                        stocktypetv.setText(selectedStockType);
                        if (selectedStockType == "2BIG") {
                            monthlydividendtv.setVisibility(TextView.VISIBLE);
                            dividendlabeltv.setVisibility(TextView.VISIBLE);
                        } else if (selectedStockType == "Certificate of Deposit") {
                            monthlyinteresttv.setVisibility(TextView.VISIBLE);
                            interestlabeltv.setVisibility(TextView.VISIBLE);
                        } else {
                            monthlydividendtv.setVisibility(TextView.GONE);
                            monthlyinteresttv.setVisibility(TextView.GONE);
                            dividendlabeltv.setVisibility(TextView.GONE);
                            interestlabeltv.setVisibility(TextView.GONE);
                        }
                        Toast.makeText(StockMutualFundCODBuyPage.this, "Stock type selected is: " + selectedStockType, Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                    }
                });

                dialog.show();

            }
        });

        buyingpricetv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    int buyingPrice = 0;
                    int numberOfShares = 0;
                    int totalPrice;
                    if (buyingpricetv.getText().toString().isEmpty() || numberofsharetv.getText().toString().isEmpty()) {
                        buyingPrice = 0;
                        numberOfShares = 0;
                    } else {
                        buyingPrice = Integer.parseInt(buyingpricetv.getText().toString());
                        numberOfShares = Integer.parseInt(numberofsharetv.getText().toString());
                    }
                    totalPrice = buyingPrice * numberOfShares;
                    totalpricetv.setHint("Total Price: $"+totalPrice);
                }
            }
        });

        numberofsharetv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    int buyingPrice = 0;
                    int numberOfShares = 0;
                    int totalPrice;
                    if (buyingpricetv.getText().toString().isEmpty() || numberofsharetv.getText().toString().isEmpty()) {
                        buyingPrice = 0;
                        numberOfShares = 0;
                    } else {
                        buyingPrice = Integer.parseInt(buyingpricetv.getText().toString());
                        numberOfShares = Integer.parseInt(numberofsharetv.getText().toString());
                    }
                    totalPrice = buyingPrice * numberOfShares;
                    totalpricetv.setHint("Total Price: $"+totalPrice);
                }
            }
        });

//        Button Buy Stock

        buyStockBtn = findViewById(R.id.buy_button);
        buyStockBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                saveStock(v);
                Intent intent = new Intent(StockMutualFundCODBuyPage.this, MainPage.class);
                startActivity(intent);
            }
        });
    }


    public void saveStock(View v) {
        String stockType;
        int buyingprice, numberOfShares;
        int monthlyDivOrInterestPerShare = 0;
        int monthlyDiv = 0;
        int monthlyInterest = 0;
        stockType = stocktypetv.getText().toString();
        buyingprice = Integer.parseInt(buyingpricetv.getText().toString());
        numberOfShares = Integer.parseInt(numberofsharetv.getText().toString());
        if (stockType.equals("2BIG")) {
            monthlyDivOrInterestPerShare = Integer.parseInt(monthlydividendtv.getText().toString());
            monthlyDiv = monthlyDivOrInterestPerShare * numberOfShares;
        }
        if (stockType.equals("Certificate of Deposit")) {
            monthlyDivOrInterestPerShare = Integer.parseInt(monthlyinteresttv.getText().toString());
            monthlyInterest = monthlyDivOrInterestPerShare * numberOfShares;
        }

        StockMutualFundCODRecord stockMutualFundCODRecord = new StockMutualFundCODRecord();
        stockMutualFundCODRecord.setStockType(stockType);
        stockMutualFundCODRecord.setBuyingPrice(buyingprice);
        stockMutualFundCODRecord.setNumOfShares(numberOfShares);
        stockMutualFundCODRecord.setMonthlyDividends(monthlyDiv);
        stockMutualFundCODRecord.setMonthlyInterest(monthlyInterest);

        DatabaseHelper dataSource = new DatabaseHelper(this);
        dataSource.insertStockMutualFundCOD(stockMutualFundCODRecord);
        int totalPrice = buyingprice * numberOfShares;
        updatePlayerInfo(v, totalPrice);
//        dataSource.updateStockMutualFundCOD(stockMutualFundCODRecord);
//        dataSource.deleteStockMutualFundCOD(4);
//        dataSource.deleteAllStockMutualFundCOD();

        this.finish();
    }

    public void updatePlayerInfo(View v, int totalPrice) {
        DatabaseHelper dataSource = new DatabaseHelper(this);
        PlayerInfoRecord playerInfoRecord = dataSource.getPlayerInfo();
        int currentCash = playerInfoRecord.getCashOnHand();
        int newCashOnHand = currentCash - totalPrice;
        playerInfoRecord.setCashOnHand(newCashOnHand);
        dataSource.updatePlayerInfo(playerInfoRecord);
    }
}