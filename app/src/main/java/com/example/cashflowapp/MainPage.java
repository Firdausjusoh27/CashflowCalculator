package com.example.cashflowapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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
    private Button buyBtn, sellBtn, loanBtn, collectBtn;
    static String[] assetTypeList = {"Stock", "Real Estate", "Business", "Gold"};
    static String[] collectTypeList = {"Collect Money", "PAYDAY"};
    static String[] loanActionList = {"Take Out Loans", "Pay Off Loans"};

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
                        if (selectedAssetType.equals("Stock")) {
                            Intent intent = new Intent(MainPage.this, StockMutualFundCODBuyPage.class);
                            startActivity(intent);
                        } else if (selectedAssetType.equals("Real Estate")) {
                            Intent intent = new Intent(MainPage.this, RealEstateBuyPage.class);
                            startActivity(intent);
                        } else if (selectedAssetType.equals("Business")) {
                            Intent intent = new Intent(MainPage.this, BusinessBuyPage.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(MainPage.this, GoldBuyPage.class);
                            startActivity(intent);
                        }
                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });

        sellBtn = findViewById(R.id.sellButton);
        sellBtn.setOnClickListener(new View.OnClickListener(){
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
                        if (selectedAssetType.equals("Stock")) {
                            Intent intent = new Intent(MainPage.this, StockMutualFundCODSellPage.class);
                            startActivity(intent);
                        } else if (selectedAssetType.equals("Real Estate")) {
                            Intent intent = new Intent(MainPage.this, RealEstateSellPage.class);
                            startActivity(intent);
                        } else if (selectedAssetType.equals("Business")) {
                            Intent intent = new Intent(MainPage.this, BusinessSellPage.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(MainPage.this, GoldSellPage.class);
                            startActivity(intent);
                        }
                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });

        loanBtn = findViewById(R.id.loanButton);
        loanBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final ListView listView = new ListView(MainPage.this);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainPage.this, android.R.layout.simple_list_item_1, loanActionList);
                listView.setAdapter(adapter);
                AlertDialog.Builder prodialog = new AlertDialog.Builder(MainPage.this);
                prodialog.setCancelable(true);
                prodialog.setView(listView);
                final AlertDialog dialog = prodialog.create();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedLoanAction = adapter.getItem(position);
                        Intent intent;
                        if (selectedLoanAction.equals("Take Out Loans")) {
                            intent = new Intent(MainPage.this, TakeOutLoanPage.class);
                        } else {
                            intent = new Intent(MainPage.this, PayOffLoanPage.class);
                        }
                        startActivity(intent);
                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });

        collectBtn = findViewById(R.id.collectButton);
        collectBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final ListView listView = new ListView(MainPage.this);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainPage.this, android.R.layout.simple_list_item_1, collectTypeList);
                listView.setAdapter(adapter);
                AlertDialog.Builder prodialog = new AlertDialog.Builder(MainPage.this);
                prodialog.setCancelable(true);
                prodialog.setView(listView);
                final AlertDialog dialog = prodialog.create();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedAssetType = adapter.getItem(position);
                        int payDay = (playerInfoRecord.getSalary() - initTotalExpenses + totalCashFlow);
                        if (selectedAssetType.equals("PAYDAY")) {
                            int newCashOnHand = playerInfoRecord.getCashOnHand() + payDay;
                            playerInfoRecord.setCashOnHand(newCashOnHand);
                            dataSource.updatePlayerInfo(playerInfoRecord);
                            textViewCashOnHand.setHint("Cash On Hand: $" + String.format("%,d", playerInfoRecord.getCashOnHand()));
                        } else {
                            AlertDialog.Builder collectCashDialog = new AlertDialog.Builder(MainPage.this);
                            collectCashDialog.setTitle("Please enter amount to collect");
                            final EditText cashInput = new EditText(MainPage.this);
                            cashInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                            collectCashDialog.setView(cashInput);

                            collectCashDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    int cashToCollect = Integer.parseInt(cashInput.getText().toString());
                                    int newCashOnHand = playerInfoRecord.getCashOnHand() + cashToCollect;
                                    playerInfoRecord.setCashOnHand(newCashOnHand);
                                    dataSource.updatePlayerInfo(playerInfoRecord);
                                    textViewCashOnHand.setHint("Cash On Hand: $" + String.format("%,d", playerInfoRecord.getCashOnHand()));
                                }
                            });
                            collectCashDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    dialogInterface.cancel();
                                }
                            });

                            collectCashDialog.show();
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