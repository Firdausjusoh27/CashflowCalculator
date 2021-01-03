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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RealEstateBuyPage extends AppCompatActivity {

    private TextView realestatetypetv, costtv, downpaymenttv, cashflowtv, numberofunitstv, numofunitlabeltv, cashonhandtv;
    private Button buyRealEstateBtn;
    static String[] realEstateTypeList = {"Condo 2Br/1Ba", "House 3Br/2Ba", "Duplex", "4-plex", "8-plex", "Apartment Complex", "Land - 10 Acres", "Land - 20 Acres"};

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_estate_buy_page);
        databaseHelper = new DatabaseHelper(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("  CashFlow Apps");
        actionBar.setIcon(R.drawable.cash_2);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        realestatetypetv = findViewById(R.id.realEstateTypeTV);
        costtv = findViewById(R.id.realEstateCostTV);
        downpaymenttv = findViewById(R.id.realEstateDownPaymentTV);
        cashflowtv = findViewById(R.id.realEstateCashFlowTV);
        numberofunitstv = findViewById(R.id.realEstateNumOfUnitsTV);
        numofunitlabeltv = findViewById(R.id.numberOfUnitLabelView);
        cashonhandtv = findViewById(R.id.realEstateCashOnHandLabel);

        int cashOnHand = databaseHelper.getPlayerInfo().getCashOnHand();
        cashonhandtv.setHint("Cash on Hand: $"+cashOnHand);

        //        Start -- Alert Dialog for Profession TextView

        realestatetypetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ListView listView = new ListView(RealEstateBuyPage.this);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(RealEstateBuyPage.this, android.R.layout.simple_list_item_1, realEstateTypeList);
                listView.setAdapter(adapter);
                AlertDialog.Builder prodialog = new AlertDialog.Builder(RealEstateBuyPage.this);
                prodialog.setCancelable(true);
                prodialog.setView(listView);
                final AlertDialog dialog = prodialog.create();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedRealEstateType = adapter.getItem(position);
                        realestatetypetv.setText(selectedRealEstateType);
                        if (selectedRealEstateType == "Apartment Complex") {
                            numberofunitstv.setVisibility(TextView.VISIBLE);
                            numofunitlabeltv.setVisibility(TextView.VISIBLE);
                        } else {
                            numberofunitstv.setVisibility(TextView.GONE);
                            numofunitlabeltv.setVisibility(TextView.GONE);
                        }
                        Toast.makeText(RealEstateBuyPage.this, "Real Estate type selected is: " + selectedRealEstateType, Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                    }
                });

                dialog.show();

            }
        });

//        Button Buy Real Estate

        buyRealEstateBtn = findViewById(R.id.realEstate_buy_button);
        buyRealEstateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (cashOnHand >= Integer.parseInt(downpaymenttv.getText().toString())) {
                    saveRealEstate(v);
                    Intent intent = new Intent(RealEstateBuyPage.this, MainPage.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RealEstateBuyPage.this, "Cash on hand not enough", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void saveRealEstate(View v) {
        String realEstateType;
        int cost, downPayment, cashFlow;
        int numberOfUnits = 0;
        realEstateType = realestatetypetv.getText().toString();
        cost = Integer.parseInt(costtv.getText().toString());
        downPayment = Integer.parseInt(downpaymenttv.getText().toString());
        cashFlow = Integer.parseInt(cashflowtv.getText().toString());
        if (realEstateType.equals("Apartment Complex")) {
            numberOfUnits = Integer.parseInt(numberofunitstv.getText().toString());
        }

        RealEstatesRecord realEstatesRecord = new RealEstatesRecord();
        realEstatesRecord.setRealEstateType(realEstateType);
        realEstatesRecord.setCost(cost);
        realEstatesRecord.setDownPayment(downPayment);
        realEstatesRecord.setCashFlow(cashFlow);
        realEstatesRecord.setNumberOfUnits(numberOfUnits);

        DatabaseHelper dataSource = new DatabaseHelper(this);
        dataSource.insertRealEstate(realEstatesRecord);
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