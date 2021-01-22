package com.example.cashflowapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

public class RealEstateSellPage extends AppCompatActivity {

    private TextView realestatetypetv, owedtobanklabeltv, cashflowlabeltv, cashearnedtv;
    private EditText sellingpricetv;
    private Button sellRealEstateBtn, sellRealEstateCancelBtn;

    DatabaseHelper databaseHelper;
    RealEstatesRecord realEstateSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_estate_sell_page);
        databaseHelper = new DatabaseHelper(this);
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF51214D"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("  CashFlow Apps");
        actionBar.setIcon(R.drawable.cash_2);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        realestatetypetv = findViewById(R.id.sellRealEstateTypeTV);
        sellingpricetv = findViewById(R.id.realEstateSellingPriceTV);
        owedtobanklabeltv = findViewById(R.id.realEstateOwedToBankLabelTV);
        cashflowlabeltv = findViewById(R.id.realEstateCashFlowTV);
        cashearnedtv = findViewById(R.id.realEstateCashEarnLabelTV);

        List<RealEstatesRecord> realEstatesRecordList =  databaseHelper.getAllPurchasedRealEstates();
        String[] realEstatesPurchasedArr = new String[realEstatesRecordList.size()];
        for (int i = 0; i < realEstatesRecordList.size(); i++)
            realEstatesPurchasedArr[i] = realEstatesRecordList.get(i).getRealEstateType();

        if (realEstatesPurchasedArr.length > 0) {
            realestatetypetv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ListView listView = new ListView(RealEstateSellPage.this);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(RealEstateSellPage.this, android.R.layout.simple_list_item_1, realEstatesPurchasedArr);
                    listView.setAdapter(adapter);
                    AlertDialog.Builder prodialog = new AlertDialog.Builder(RealEstateSellPage.this);
                    prodialog.setCancelable(true);
                    prodialog.setView(listView);
                    final AlertDialog dialog = prodialog.create();

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selectedRealEstateType = adapter.getItem(position);
                            realestatetypetv.setText(selectedRealEstateType);
                            realEstateSelected = realEstatesRecordList.get(position);
                            int mortgageAmount = realEstateSelected.getCost() - realEstateSelected.getDownPayment();
                            owedtobanklabeltv.setHint("Owed to Bank: $"+String.format("%,d", mortgageAmount));
                            cashflowlabeltv.setHint("Cash Flow: $"+String.format("%,d", realEstateSelected.getCashFlow()));
                            Toast.makeText(RealEstateSellPage.this, "Real Estate type selected is: " + selectedRealEstateType, Toast.LENGTH_LONG).show();
                            dialog.dismiss();

                        }
                    });

                    dialog.show();

                }
            });
        } else {
            realestatetypetv.setHint("No Real Estate to Sell");
        }

        sellingpricetv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    int sellingPrice = 0;
                    int totalCashEarn = 0;
                    if (sellingpricetv.getText().toString().isEmpty()) {
                        sellingPrice = 0;
                    } else {
                        sellingPrice = Integer.parseInt(sellingpricetv.getText().toString());
                        if (realEstateSelected.getRealEstateType().equals("Duplex")) {
                            totalCashEarn = (sellingPrice * 2) - (realEstateSelected.getCost() - realEstateSelected.getDownPayment());
                        } else if (realEstateSelected.getRealEstateType().equals("4-plex")) {
                            totalCashEarn = (sellingPrice * 4) - (realEstateSelected.getCost() - realEstateSelected.getDownPayment());
                        } else if (realEstateSelected.getRealEstateType().equals("8-plex")) {
                            totalCashEarn = (sellingPrice * 8) - (realEstateSelected.getCost() - realEstateSelected.getDownPayment());
                        } else if (realEstateSelected.getRealEstateType().equals("Apartment Complex")) {
                            totalCashEarn = (sellingPrice * realEstateSelected.getNumberOfUnits()) - (realEstateSelected.getCost() - realEstateSelected.getDownPayment());
                        } else {
                            totalCashEarn = sellingPrice - (realEstateSelected.getCost() - realEstateSelected.getDownPayment());
                        }
                    }
                    cashearnedtv.setHint("Cash to be earned: $"+String.format("%,d", totalCashEarn));
                }
            }
        });

        //        Cancel Button
        sellRealEstateCancelBtn = findViewById(R.id.realEstate_sell_cancel);
        sellRealEstateCancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RealEstateSellPage.this, MainPage.class);
                startActivity(intent);

            }
        });

//        Button Sell Real Estate

        sellRealEstateBtn = findViewById(R.id.realEstate_sell_button);
        sellRealEstateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sellRealEstateFunc(v);
                Intent intent = new Intent(RealEstateSellPage.this, MainPage.class);
                startActivity(intent);
            }
        });
    }


    public void sellRealEstateFunc(View v) {
        int sellingPrice = 0;
        int totalCashEarn = 0;
        if (sellingpricetv.getText().toString().isEmpty()) {
            sellingPrice = 0;
        } else {
            sellingPrice = Integer.parseInt(sellingpricetv.getText().toString());
            if (realEstateSelected.getRealEstateType().equals("Duplex")) {
                totalCashEarn = (sellingPrice * 2) - (realEstateSelected.getCost() - realEstateSelected.getDownPayment());
            } else if (realEstateSelected.getRealEstateType().equals("4-plex")) {
                totalCashEarn = (sellingPrice * 4) - (realEstateSelected.getCost() - realEstateSelected.getDownPayment());
            } else if (realEstateSelected.getRealEstateType().equals("8-plex")) {
                totalCashEarn = (sellingPrice * 8) - (realEstateSelected.getCost() - realEstateSelected.getDownPayment());
            } else if (realEstateSelected.getRealEstateType().equals("Apartment Complex")) {
                totalCashEarn = (sellingPrice * realEstateSelected.getNumberOfUnits()) - (realEstateSelected.getCost() - realEstateSelected.getDownPayment());
            } else {
                totalCashEarn = sellingPrice - (realEstateSelected.getCost() - realEstateSelected.getDownPayment());
            }
        }
        DatabaseHelper dataSource = new DatabaseHelper(this);
        dataSource.deleteRealEstate(realEstateSelected.getId());
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