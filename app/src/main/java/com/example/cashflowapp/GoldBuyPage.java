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

public class GoldBuyPage extends AppCompatActivity {

    private TextView goldtypetv, cashonhandtv;
    private Button buyGoldBtn;
    static String[] goldTypeList = {"Spanish Gold", "Krugerrands"};

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_buy_page);
        databaseHelper = new DatabaseHelper(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("  CashFlow Apps");
        actionBar.setIcon(R.drawable.cash_2);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        goldtypetv = findViewById(R.id.goldTypeTV);
        cashonhandtv = findViewById(R.id.goldCashOnHandLabel);

        int cashOnHand = databaseHelper.getPlayerInfo().getCashOnHand();
        cashonhandtv.setHint("Cash on Hand: $"+String.format("%,d", cashOnHand));

        goldtypetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ListView listView = new ListView(GoldBuyPage.this);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(GoldBuyPage.this, android.R.layout.simple_list_item_1, goldTypeList);
                listView.setAdapter(adapter);
                AlertDialog.Builder prodialog = new AlertDialog.Builder(GoldBuyPage.this);
                prodialog.setCancelable(true);
                prodialog.setView(listView);
                final AlertDialog dialog = prodialog.create();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedGoldType = adapter.getItem(position);
                        goldtypetv.setText(selectedGoldType);
                        Toast.makeText(GoldBuyPage.this, "Gold type selected is: " + selectedGoldType, Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                    }
                });

                dialog.show();

            }
        });

//        Button Buy Gold

        buyGoldBtn = findViewById(R.id.gold_buy_button);
        buyGoldBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int cost = 0;
                if (goldtypetv.getText().toString().equals("Spanish Gold")) {
                    cost = 500;
                } else {
                    cost = 3000;
                }
                if (cashOnHand >= cost) {
                    saveGold(v, cost);
                    Intent intent = new Intent(GoldBuyPage.this, MainPage.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(GoldBuyPage.this, "Cash on hand not enough", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void saveGold(View v, int cost) {
        String goldType;
        goldType = goldtypetv.getText().toString();

        GoldRecord goldRecord = new GoldRecord();
        goldRecord.setGoldType(goldType);
        if (goldType.equals("Spanish Gold")) {
            goldRecord.setGoldAmount(1);
        } else {
            goldRecord.setGoldAmount(10);
        }

        DatabaseHelper dataSource = new DatabaseHelper(this);
        dataSource.insertGold(goldRecord);
        updatePlayerInfo(v, cost);

        this.finish();
    }

    public void updatePlayerInfo(View v, int cost) {
        DatabaseHelper dataSource = new DatabaseHelper(this);
        PlayerInfoRecord playerInfoRecord = dataSource.getPlayerInfo();
        int currentCash = playerInfoRecord.getCashOnHand();
        int newCashOnHand = currentCash - cost;
        playerInfoRecord.setCashOnHand(newCashOnHand);
        dataSource.updatePlayerInfo(playerInfoRecord);
    }
}