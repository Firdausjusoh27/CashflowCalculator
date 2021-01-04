package com.example.cashflowapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class StockMutualFundCODSellPage extends AppCompatActivity {

    private TextView stocktypetv, purchasepricelabeltv, ownnumberlabeltv, cashearnedtv;
    private EditText sellingpricetv, numberofsharetv;
    private Button sellStockBtn;

    DatabaseHelper databaseHelper;
    StockMutualFundCODRecord stockSelected;
    private int numOfSharedOwned = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_mutual_fund_c_o_d_sell_page);
        databaseHelper = new DatabaseHelper(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("  CashFlow Apps");
        actionBar.setIcon(R.drawable.cash_2);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        stocktypetv = findViewById(R.id.sellStockTypeTV);
        sellingpricetv = findViewById(R.id.sellingPriceTV);
        numberofsharetv = findViewById(R.id.numOfSharesToSellTV);
        purchasepricelabeltv = findViewById(R.id.purchasePriceLabelTV);
        ownnumberlabeltv = findViewById(R.id.ownedNumTV);
        cashearnedtv = findViewById(R.id.cashEarnLabelTV);

        List<StockMutualFundCODRecord> stockMutualFundCODRecordList =  databaseHelper.getAllPurchasedStocks();
        String[] stockPurchasedArr = new String[stockMutualFundCODRecordList.size()];
        for (int i = 0; i < stockMutualFundCODRecordList.size(); i++)
            stockPurchasedArr[i] = stockMutualFundCODRecordList.get(i).getStockType();

        if (stockPurchasedArr.length > 0) {
            stocktypetv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ListView listView = new ListView(StockMutualFundCODSellPage.this);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(StockMutualFundCODSellPage.this, android.R.layout.simple_list_item_1, stockPurchasedArr);
                    listView.setAdapter(adapter);
                    AlertDialog.Builder prodialog = new AlertDialog.Builder(StockMutualFundCODSellPage.this);
                    prodialog.setCancelable(true);
                    prodialog.setView(listView);
                    final AlertDialog dialog = prodialog.create();

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selectedStockType = adapter.getItem(position);
                            stocktypetv.setText(selectedStockType);
                            stockSelected = stockMutualFundCODRecordList.get(position);
                            purchasepricelabeltv.setHint("Purchase Price: $"+String.format("%,d", stockSelected.getBuyingPrice()));
                            ownnumberlabeltv.setHint("Owned: "+stockSelected.getNumOfShares());
                            numOfSharedOwned = stockSelected.getNumOfShares();
                            Toast.makeText(StockMutualFundCODSellPage.this, "Stock type selected is: " + selectedStockType, Toast.LENGTH_LONG).show();
                            dialog.dismiss();

                        }
                    });

                    dialog.show();

                }
            });
        } else {
            stocktypetv.setHint("No Stock to Sell");
        }

        sellingpricetv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    int sellingPrice = 0;
                    int numberOfShares = 0;
                    int totalCashEarn;
                    if (sellingpricetv.getText().toString().isEmpty() || numberofsharetv.getText().toString().isEmpty()) {
                        sellingPrice = 0;
                        numberOfShares = 0;
                    } else {
                        sellingPrice = Integer.parseInt(sellingpricetv.getText().toString());
                        numberOfShares = Integer.parseInt(numberofsharetv.getText().toString());
                    }
                    totalCashEarn = sellingPrice * numberOfShares;
                    cashearnedtv.setHint("Cash to be earned: $"+String.format("%,d", totalCashEarn));
                }
            }
        });

        numberofsharetv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    int sellingPrice = 0;
                    int numberOfShares = 0;
                    int totalCashEarn;
                    if (sellingpricetv.getText().toString().isEmpty() || numberofsharetv.getText().toString().isEmpty()) {
                        sellingPrice = 0;
                        numberOfShares = 0;
                    } else {
                        sellingPrice = Integer.parseInt(sellingpricetv.getText().toString());
                        numberOfShares = Integer.parseInt(numberofsharetv.getText().toString());
                    }
                    totalCashEarn = sellingPrice * numberOfShares;
                    cashearnedtv.setHint("Cash to be earned: $"+String.format("%,d", totalCashEarn));
                }
            }
        });

//        Button Sell Stock

        sellStockBtn = findViewById(R.id.sell_button);
        sellStockBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int sellingPrice = 0;
                int numberOfShares = 0;
                int totalCashEarn;
                if (sellingpricetv.getText().toString().isEmpty() || numberofsharetv.getText().toString().isEmpty()) {
                    sellingPrice = 0;
                    numberOfShares = 0;
                } else {
                    sellingPrice = Integer.parseInt(sellingpricetv.getText().toString());
                    numberOfShares = Integer.parseInt(numberofsharetv.getText().toString());
                }
                totalCashEarn = sellingPrice * numberOfShares;
                if (numOfSharedOwned >= numberOfShares) {
                    sellStockFunc(v);
                    Intent intent = new Intent(StockMutualFundCODSellPage.this, MainPage.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(StockMutualFundCODSellPage.this, "Number of Shares owned not enough", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void sellStockFunc(View v) {
        String stockType;
        int sellingPrice, numberOfShares;
        stockType = stocktypetv.getText().toString();
        sellingPrice = Integer.parseInt(sellingpricetv.getText().toString());
        numberOfShares = Integer.parseInt(numberofsharetv.getText().toString());
        DatabaseHelper dataSource = new DatabaseHelper(this);
        if (numberOfShares == numOfSharedOwned) {
            dataSource.deleteStockMutualFundCOD(stockSelected.getId());
        } else {
            StockMutualFundCODRecord stockMutualFundCODRecord = new StockMutualFundCODRecord();
            stockMutualFundCODRecord.setId(stockSelected.getId());
            stockMutualFundCODRecord.setStockType(stockType);
            stockMutualFundCODRecord.setBuyingPrice(stockSelected.getBuyingPrice());
            stockMutualFundCODRecord.setNumOfShares((numOfSharedOwned - numberOfShares));
            stockMutualFundCODRecord.setMonthlyDividends(stockSelected.getMonthlyDividends());
            stockMutualFundCODRecord.setMonthlyInterest(stockSelected.getMonthlyInterest());

            dataSource.updateStockMutualFundCOD(stockMutualFundCODRecord);
        }

        int totalCashEarn = sellingPrice * numberOfShares;
        updatePlayerInfo(v, totalCashEarn);
//        dataSource.updateStockMutualFundCOD(stockMutualFundCODRecord);
//        dataSource.deleteStockMutualFundCOD(4);
//        dataSource.deleteAllStockMutualFundCOD();

        this.finish();
    }

    public void updatePlayerInfo(View v, int totalCashEarn) {
        DatabaseHelper dataSource = new DatabaseHelper(this);
        PlayerInfoRecord playerInfoRecord = dataSource.getPlayerInfo();
        int currentCash = playerInfoRecord.getCashOnHand();
        int newCashOnHand = currentCash + totalCashEarn;
        playerInfoRecord.setCashOnHand(newCashOnHand);
        dataSource.updatePlayerInfo(playerInfoRecord);
    }
}