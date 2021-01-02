package com.example.cashflowapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPage extends AppCompatActivity {
    private ProgressBar cashPB;
    private TextView textViewProfession, textViewCashOnHand, textViewCashFlow, textViewExpenses, textViewSalary, textViewPayDay;
    private Button buyBtn;
    static String[] assetTypeList = {"Stock", "Real Estate", "Business", "Gold"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("  CashFlow Apps");
        actionBar.setIcon(R.drawable.cash_2);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        cashPB = findViewById(R.id.progressbar);
//        cashPB.setProgress(90);
        cashPB.setProgress(0);

        DatabaseHelper dataSource = new DatabaseHelper(this);
        PlayerInfoRecord playerInfoRecord = dataSource.getPlayerInfo();

        int initTotalExpenses = dataSource.getSumOfExpenses();
        int totalCashFlow = dataSource.getCashFlow();

        textViewProfession = (TextView)findViewById(R.id.ProfessionTV);
        textViewProfession.setText(playerInfoRecord.getProfession());
        textViewCashOnHand = (TextView)findViewById(R.id.CashOnHandTV);
        textViewCashOnHand.setHint("Cash On Hand: $" + String.format("%,d", playerInfoRecord.getCashOnHand()));
        textViewCashFlow = (TextView)findViewById(R.id.cashFlowTV);
        textViewCashFlow.setHint("CashFlow: $" + String.format("%,d", totalCashFlow));
        textViewSalary = (TextView)findViewById(R.id.TotalIncomeTV);
        textViewSalary.setHint("Total Income: $" + String.format("%,d", (playerInfoRecord.getSalary() + totalCashFlow)));
        textViewExpenses = (TextView)findViewById(R.id.ExpensesTV);
        textViewExpenses.setHint("Expenses: $" + String.format("%,d", initTotalExpenses));
        textViewPayDay = (TextView)findViewById(R.id.PaydayTV);
        textViewPayDay.setHint("PAYDAY: $" + String.format("%,d", (playerInfoRecord.getSalary() - initTotalExpenses + totalCashFlow)));

//        Initialize and assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

//        Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.menuHome);

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

        buyBtn = findViewById(R.id.buyButton);
        buyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final ListView listView = new ListView(MainPage.this);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainPage.this, android.R.layout.simple_list_item_1, assetTypeList);
                listView.setAdapter(adapter);
                AlertDialog.Builder prodialog = new AlertDialog.Builder(MainPage.this);
                prodialog.setCancelable(true);
                prodialog.setView(listView);
                final AlertDialog dialog = prodialog.create();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedAssetType = adapter.getItem(position);
                        if (selectedAssetType == "Stock") {
                            Intent intent = new Intent(MainPage.this, StockMutualFundCODBuyPage.class);
                            startActivity(intent);
                        }
                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });


    }

    //    Action Bar for this page
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}