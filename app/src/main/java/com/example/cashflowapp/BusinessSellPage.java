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

public class BusinessSellPage extends AppCompatActivity {

    private TextView businesstypetv, owedtobanklabeltv, cashflowlabeltv, cashearnedtv;
    private EditText sellingpricetv;
    private Button sellBusinessBtn;

    DatabaseHelper databaseHelper;
    BusinessRecord businessSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_sell_page);
        databaseHelper = new DatabaseHelper(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("  CashFlow Apps");
        actionBar.setIcon(R.drawable.cash_2);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        businesstypetv = findViewById(R.id.sellBusinessTypeTV);
        sellingpricetv = findViewById(R.id.businessSellingPriceTV);
        owedtobanklabeltv = findViewById(R.id.businessOwedToBankLabelTV);
        cashflowlabeltv = findViewById(R.id.businessCashFlowTV);
        cashearnedtv = findViewById(R.id.businessCashEarnLabelTV);

        List<BusinessRecord> businessRecordList =  databaseHelper.getAllPurchasedBusinesses();
        String[] businessPurchasedArr = new String[businessRecordList.size()];
        for (int i = 0; i < businessRecordList.size(); i++)
            businessPurchasedArr[i] = businessRecordList.get(i).getBusinessType();

        if (businessPurchasedArr.length > 0) {
            businesstypetv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ListView listView = new ListView(BusinessSellPage.this);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(BusinessSellPage.this, android.R.layout.simple_list_item_1, businessPurchasedArr);
                    listView.setAdapter(adapter);
                    AlertDialog.Builder prodialog = new AlertDialog.Builder(BusinessSellPage.this);
                    prodialog.setCancelable(true);
                    prodialog.setView(listView);
                    final AlertDialog dialog = prodialog.create();

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selectedBusinessType = adapter.getItem(position);
                            businesstypetv.setText(selectedBusinessType);
                            businessSelected = businessRecordList.get(position);
                            int mortgageAmount = businessSelected.getCost() - businessSelected.getDownPayment();
                            owedtobanklabeltv.setHint("Owed to Bank: $"+String.format("%,d", mortgageAmount));
                            cashflowlabeltv.setHint("Cash Flow: $"+String.format("%,d", businessSelected.getCashFlow()));
                            Toast.makeText(BusinessSellPage.this, "Business type selected is: " + selectedBusinessType, Toast.LENGTH_LONG).show();
                            dialog.dismiss();

                        }
                    });

                    dialog.show();

                }
            });
        } else {
            businesstypetv.setHint("No Business to Sell");
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
                        totalCashEarn = sellingPrice - (businessSelected.getCost() - businessSelected.getDownPayment());
                    }
                    cashearnedtv.setHint("Cash to be earned: $"+String.format("%,d", totalCashEarn));
                }
            }
        });

//        Button Sell Business

        sellBusinessBtn = findViewById(R.id.business_sell_button);
        sellBusinessBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sellBusinessFunc(v);
                Intent intent = new Intent(BusinessSellPage.this, MainPage.class);
                startActivity(intent);
            }
        });
    }


    public void sellBusinessFunc(View v) {
        int sellingPrice = 0;
        int totalCashEarn = 0;
        if (sellingpricetv.getText().toString().isEmpty()) {
            sellingPrice = 0;
        } else {
            sellingPrice = Integer.parseInt(sellingpricetv.getText().toString());
            totalCashEarn = sellingPrice - (businessSelected.getCost() - businessSelected.getDownPayment());
        }
        DatabaseHelper dataSource = new DatabaseHelper(this);
        dataSource.deleteBusiness(businessSelected.getId());
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