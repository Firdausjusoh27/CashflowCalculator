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

public class GoldSellPage extends AppCompatActivity {

    private TextView goldtypetv, purchasepricelabeltv, ownnumberlabeltv, cashearnedtv;
    private EditText sellingpricetv, amounttoselltv;
    private Button sellGoldBtn;

    DatabaseHelper databaseHelper;
    GoldRecord goldSelected;
    private int amountOwned = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_sell_page);
        databaseHelper = new DatabaseHelper(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("  CashFlow Apps");
        actionBar.setIcon(R.drawable.cash_2);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        goldtypetv = findViewById(R.id.sellGoldTypeTV);
        sellingpricetv = findViewById(R.id.goldSellingPriceTV);
        amounttoselltv = findViewById(R.id.amountOfGoldToSellTV);
        purchasepricelabeltv = findViewById(R.id.goldPurchasePriceLabelTV);
        ownnumberlabeltv = findViewById(R.id.goldOwnedNumTV);
        cashearnedtv = findViewById(R.id.goldCashEarnLabelTV);

        List<GoldRecord> goldRecordList =  databaseHelper.getAllPurchasedGolds();
        String[] goldPurchasedArr = new String[goldRecordList.size()];
        for (int i = 0; i < goldRecordList.size(); i++)
            goldPurchasedArr[i] = goldRecordList.get(i).getGoldType();

        if (goldPurchasedArr.length > 0) {
            goldtypetv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ListView listView = new ListView(GoldSellPage.this);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(GoldSellPage.this, android.R.layout.simple_list_item_1, goldPurchasedArr);
                    listView.setAdapter(adapter);
                    AlertDialog.Builder prodialog = new AlertDialog.Builder(GoldSellPage.this);
                    prodialog.setCancelable(true);
                    prodialog.setView(listView);
                    final AlertDialog dialog = prodialog.create();

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selectedGoldType = adapter.getItem(position);
                            goldtypetv.setText(selectedGoldType);
                            goldSelected = goldRecordList.get(position);
                            int buyingPrice = 0;
                            if (goldSelected.getGoldType().equals("Spanish Gold")) {
                                buyingPrice = 500;
                            } else {
                                buyingPrice = 300;
                            }
                            purchasepricelabeltv.setHint("Purchase Price: $"+String.format("%,d", buyingPrice));
                            ownnumberlabeltv.setHint("Owned: "+goldSelected.getGoldAmount());
                            amountOwned = goldSelected.getGoldAmount();
                            Toast.makeText(GoldSellPage.this, "Gold type selected is: " + selectedGoldType, Toast.LENGTH_LONG).show();
                            dialog.dismiss();

                        }
                    });

                    dialog.show();

                }
            });
        } else {
            goldtypetv.setHint("No Gold to Sell");
        }

        sellingpricetv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    int sellingPrice = 0;
                    int amountToSell = 0;
                    int totalCashEarn;
                    if (sellingpricetv.getText().toString().isEmpty() || amounttoselltv.getText().toString().isEmpty()) {
                        sellingPrice = 0;
                        amountToSell = 0;
                    } else {
                        sellingPrice = Integer.parseInt(sellingpricetv.getText().toString());
                        amountToSell = Integer.parseInt(amounttoselltv.getText().toString());
                    }
                    totalCashEarn = sellingPrice * amountToSell;
                    cashearnedtv.setHint("Cash to be earned: $"+String.format("%,d", totalCashEarn));
                }
            }
        });

        amounttoselltv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    int sellingPrice = 0;
                    int amountToSell = 0;
                    int totalCashEarn;
                    if (sellingpricetv.getText().toString().isEmpty() || amounttoselltv.getText().toString().isEmpty()) {
                        sellingPrice = 0;
                        amountToSell = 0;
                    } else {
                        sellingPrice = Integer.parseInt(sellingpricetv.getText().toString());
                        amountToSell = Integer.parseInt(amounttoselltv.getText().toString());
                    }
                    totalCashEarn = sellingPrice * amountToSell;
                    cashearnedtv.setHint("Cash to be earned: $"+String.format("%,d", totalCashEarn));
                }
            }
        });

//        Button Sell Gold

        sellGoldBtn = findViewById(R.id.gold_sell_button);
        sellGoldBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int sellingPrice = 0;
                int amountToSell = 0;
                int totalCashEarn;
                if (sellingpricetv.getText().toString().isEmpty() || amounttoselltv.getText().toString().isEmpty()) {
                    sellingPrice = 0;
                    amountToSell = 0;
                } else {
                    sellingPrice = Integer.parseInt(sellingpricetv.getText().toString());
                    amountToSell = Integer.parseInt(amounttoselltv.getText().toString());
                }
                totalCashEarn = sellingPrice * amountToSell;
                if (amountOwned >= amountToSell) {
                    sellGoldFunc(v);
                    Intent intent = new Intent(GoldSellPage.this, MainPage.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(GoldSellPage.this, "Amount of Gold owned not enough", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void sellGoldFunc(View v) {
        String goldType;
        int sellingPrice, amountToSell;
        goldType = goldtypetv.getText().toString();
        sellingPrice = Integer.parseInt(sellingpricetv.getText().toString());
        amountToSell = Integer.parseInt(amounttoselltv.getText().toString());
        DatabaseHelper dataSource = new DatabaseHelper(this);
        if (amountToSell == amountOwned) {
            dataSource.deleteGold(goldSelected.getId());
        } else {
            GoldRecord goldRecord = new GoldRecord();
            goldRecord.setId(goldSelected.getId());
            goldRecord.setGoldType(goldType);
            goldRecord.setGoldAmount(amountOwned - amountToSell);

            dataSource.updateGold(goldRecord);
        }

        int totalCashEarn = sellingPrice * amountToSell;
        updatePlayerInfo(v, totalCashEarn);

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