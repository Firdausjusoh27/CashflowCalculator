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

public class BusinessBuyPage extends AppCompatActivity {

    private TextView businesstypetv, cashonhandtv;
    private EditText costtv, downpaymenttv, cashflowtv;
    private Button buyBusinessBtn, buyBusinessCancelBtn;
    static String[] businessTypeList = {"Auto Dealer", "Auto Wash", "Bed and Breakfast", "Car Wash", "Coin Telephone", "Doctor Office", "Laundromat", "Pinball Machines",
    "Pizza Chain", "Pizza Franchise", "Sandwich Shop", "Shopping Mall", "Software Company", "Widget Company"};

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_buy_page);
        databaseHelper = new DatabaseHelper(this);
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF51214D"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("  CashFlow Apps");
        actionBar.setIcon(R.drawable.cash_2);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        businesstypetv = findViewById(R.id.businessTypeTV);
        costtv = findViewById(R.id.businessCostTV);
        downpaymenttv = findViewById(R.id.businessDownPaymentTV);
        cashflowtv = findViewById(R.id.businessCashFlowTV);
        cashonhandtv = findViewById(R.id.businessCashOnHandLabel);

        int cashOnHand = databaseHelper.getPlayerInfo().getCashOnHand();
        cashonhandtv.setHint("Cash on Hand: $"+String.format("%,d", cashOnHand));

        businesstypetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ListView listView = new ListView(BusinessBuyPage.this);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(BusinessBuyPage.this, android.R.layout.simple_list_item_1, businessTypeList);
                listView.setAdapter(adapter);
                AlertDialog.Builder prodialog = new AlertDialog.Builder(BusinessBuyPage.this);
                prodialog.setCancelable(true);
                prodialog.setView(listView);
                final AlertDialog dialog = prodialog.create();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedBusinessType = adapter.getItem(position);
                        businesstypetv.setText(selectedBusinessType);
                        Toast.makeText(BusinessBuyPage.this, "Business type selected is: " + selectedBusinessType, Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                    }
                });

                dialog.show();

            }
        });

        //        Cancel Button
        buyBusinessCancelBtn = findViewById(R.id.business_buy_cancel);
        buyBusinessCancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BusinessBuyPage.this, MainPage.class);
                startActivity(intent);

            }
        });

//        Button Buy Business

        buyBusinessBtn = findViewById(R.id.business_buy_button);
        buyBusinessBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (cashOnHand >= Integer.parseInt(downpaymenttv.getText().toString())) {
                    saveBusiness(v);
                    Intent intent = new Intent(BusinessBuyPage.this, MainPage.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(BusinessBuyPage.this, "Cash on hand not enough", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void saveBusiness(View v) {
        String businessType;
        int cost, downPayment, cashFlow;
        businessType = businesstypetv.getText().toString();
        cost = Integer.parseInt(costtv.getText().toString());
        downPayment = Integer.parseInt(downpaymenttv.getText().toString());
        cashFlow = Integer.parseInt(cashflowtv.getText().toString());

        BusinessRecord businessRecord = new BusinessRecord();
        businessRecord.setBusinessType(businessType);
        businessRecord.setCost(cost);
        businessRecord.setDownPayment(downPayment);
        businessRecord.setCashFlow(cashFlow);

        DatabaseHelper dataSource = new DatabaseHelper(this);
        dataSource.insertBusiness(businessRecord);

        LiabilityRecord liabilityRecord = new LiabilityRecord();
        liabilityRecord.setLoanType(businessType);
        liabilityRecord.setLoanAmount((cost - downPayment));
        dataSource.insertLiability(liabilityRecord);

        updatePlayerInfo(v, downPayment);

        this.finish();
    }

    public void updatePlayerInfo(View v, int downPayment) {
        DatabaseHelper dataSource = new DatabaseHelper(this);
        PlayerInfoRecord playerInfoRecord = dataSource.getPlayerInfo();
        int currentCash = playerInfoRecord.getCashOnHand();
        int newCashOnHand = currentCash - downPayment;
        playerInfoRecord.setCashOnHand(newCashOnHand);
        dataSource.updatePlayerInfo(playerInfoRecord);
    }
}