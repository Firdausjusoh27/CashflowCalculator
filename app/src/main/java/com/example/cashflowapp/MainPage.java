package com.example.cashflowapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainPage extends AppCompatActivity {
    private ProgressBar cashPB;
    private TextView textViewProfession, textViewCashOnHand, textViewCashFlow, textViewExpenses, textViewSalary, textViewPayDay;
    private Button buyBtn, sellBtn, loanBtn, marketActionBtn, payBtn, collectBtn;
    static String[] assetTypeList = {"Stock", "Real Estate", "Business", "Gold"};
    static String[] collectTypeList = {"Collect Money", "PAYDAY"};
    static String[] paymentTypeList = {"Charity", "Downsized", "Lend / Pay Money"};
    static String[] loanActionList = {"Take Out Loans", "Pay Off Loans"};

    private static boolean containElement(String[] marketActionArr, String toCheckValue) {
        boolean hasElement;
        hasElement = Arrays.asList(marketActionArr).contains(toCheckValue);
        return hasElement;
    }

    protected static String[] addElementToArray(int n, String arr[], String x)
    {
        int i;

        // create a new array of size n+1
        String newarr[] = new String[n + 1];

        // insert the elements from
        // the old array into the new array
        // insert all elements till n
        // then insert x at n+1
        for (i = 0; i < n; i++)
            newarr[i] = arr[i];

        newarr[n] = x;

        return newarr;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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

        DatabaseHelper dataSource = new DatabaseHelper(this);

        PlayerInfoRecord playerInfoRecord = dataSource.getPlayerInfo();

        int initTotalExpenses = dataSource.getSumOfExpenses();
        int totalCashFlow = dataSource.getCashFlow();

        int progressPercentage = (int)((Double.valueOf(String.valueOf(totalCashFlow)) / Double.valueOf(String.valueOf(initTotalExpenses))) * 100);
        if (progressPercentage > 100)
            progressPercentage = 100;
        cashPB.setProgress(progressPercentage, true);

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

        marketActionBtn = findViewById(R.id.marketActionButton);
        marketActionBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final ListView listView = new ListView(MainPage.this);

                String[] marketActionList = {};

                List<BusinessRecord> businessRecordList = dataSource.getAllPurchasedBusinesses();
                boolean hasLimitedPartnership = false;
                boolean hasSmallBusiness = false;
                boolean hasRealEstate = false;
                boolean hasStock = false;
                List<BusinessRecord> limitedPartnershipBusiness = new ArrayList<>();
                List<BusinessRecord> smallBusinesses = new ArrayList<>();
                for (int i = 0; i < businessRecordList.size(); i++) {
                    String bizType = businessRecordList.get(i).getBusinessType();
                    if(bizType.equals("Auto Dealer") || bizType.equals("Doctor Office") || bizType.equals("Pizza Chain") || bizType.equals("Shopping Mall") || bizType.equals("Sandwich Shop")) {
                        hasLimitedPartnership = true;
                        limitedPartnershipBusiness.add(businessRecordList.get(i));
                    }
                    if(bizType.equals("Widget Company") || bizType.equals("Software Company")) {
                        hasSmallBusiness = true;
                        smallBusinesses.add(businessRecordList.get(i));
                    }
                }
                List<RealEstatesRecord> realEstatesRecordList = dataSource.getAllPurchasedRealEstates();
                if (realEstatesRecordList.size() > 0)
                    hasRealEstate = true;
                List<StockMutualFundCODRecord> stockMutualFundCODRecordList = dataSource.getAllPurchasedStocks();
                if (stockMutualFundCODRecordList.size() > 0)
                    hasStock = true;
                if (hasLimitedPartnership && !containElement(marketActionList, "Limited Partnership Sold"))
                    marketActionList = addElementToArray(marketActionList.length, marketActionList, "Limited Partnership Sold");
                if (hasSmallBusiness && !containElement(marketActionList, "Business Improvement"))
                    marketActionList = addElementToArray(marketActionList.length, marketActionList, "Business Improvement");
                if (hasRealEstate && !containElement(marketActionList, "Real Estate Repair"))
                    marketActionList = addElementToArray(marketActionList.length, marketActionList, "Real Estate Repair");
                if (hasStock) {
                    if (!containElement(marketActionList, "Reverse Stock Split"))
                        marketActionList = addElementToArray(marketActionList.length, marketActionList, "Reverse Stock Split");
                    if (!containElement(marketActionList, "Stock Split"))
                        marketActionList = addElementToArray(marketActionList.length, marketActionList, "Stock Split");
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainPage.this, android.R.layout.simple_list_item_1, marketActionList);
                listView.setAdapter(adapter);
                AlertDialog.Builder prodialog = new AlertDialog.Builder(MainPage.this);
                prodialog.setCancelable(true);
                prodialog.setView(listView);
                final AlertDialog dialog = prodialog.create();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedMarketActionType = adapter.getItem(position);
                        if (selectedMarketActionType.equals("Limited Partnership Sold")) {
                            AlertDialog.Builder marketActionDialog = new AlertDialog.Builder(MainPage.this);
                            marketActionDialog.setTitle("Please enter value increase (i.e: 2x):");
                            final EditText valueIncreaseInput = new EditText(MainPage.this);
                            valueIncreaseInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                            marketActionDialog.setView(valueIncreaseInput);

                            marketActionDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    int valueIncrease = Integer.parseInt(valueIncreaseInput.getText().toString());
                                    int totalCashIncrease = 0;
                                    for (int i = 0; i < limitedPartnershipBusiness.size(); i++) {
                                        totalCashIncrease += (limitedPartnershipBusiness.get(i).getCost() * valueIncrease);
                                        dataSource.deleteBusiness(limitedPartnershipBusiness.get(i).getId());
                                    }
                                    int newCashOnHand = playerInfoRecord.getCashOnHand() + totalCashIncrease;
                                    playerInfoRecord.setCashOnHand(newCashOnHand);
                                    dataSource.updatePlayerInfo(playerInfoRecord);
                                    textViewCashOnHand.setHint("Cash On Hand: $" + String.format("%,d", playerInfoRecord.getCashOnHand()));
                                    int newTotalCashFlow = dataSource.getCashFlow();
                                    textViewCashFlow = (TextView)findViewById(R.id.cashFlowTV);
                                    textViewCashFlow.setHint("CashFlow: $" + String.format("%,d", newTotalCashFlow));
                                    textViewSalary = (TextView)findViewById(R.id.TotalIncomeTV);
                                    textViewSalary.setHint("Total Income: $" + String.format("%,d", (playerInfoRecord.getSalary() + newTotalCashFlow)));
                                    textViewPayDay = (TextView)findViewById(R.id.PaydayTV);
                                    textViewPayDay.setHint("PAYDAY: $" + String.format("%,d", (playerInfoRecord.getSalary() - initTotalExpenses + newTotalCashFlow)));
                                }
                            });
                            marketActionDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    dialogInterface.cancel();
                                }
                            });

                            marketActionDialog.show();
                        } else if (selectedMarketActionType.equals("Business Improvement")) {
                            AlertDialog.Builder marketActionDialog = new AlertDialog.Builder(MainPage.this);
                            marketActionDialog.setTitle("Please enter Cash Flow increase amount:");
                            final EditText cashFlowIncreaseInput = new EditText(MainPage.this);
                            cashFlowIncreaseInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                            marketActionDialog.setView(cashFlowIncreaseInput);

                            marketActionDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    int cashFlowIncrease = Integer.parseInt(cashFlowIncreaseInput.getText().toString());
                                    for (int i = 0; i < smallBusinesses.size(); i++) {
                                        BusinessRecord smallBusinessRecord = smallBusinesses.get(i);
                                        int currentBizCashFlow = smallBusinessRecord.getCashFlow();
                                        smallBusinessRecord.setCashFlow(currentBizCashFlow + cashFlowIncrease);
                                        dataSource.updateBusiness(smallBusinessRecord);
                                    }
                                    int newTotalCashFlow = dataSource.getCashFlow();
                                    textViewCashFlow = (TextView)findViewById(R.id.cashFlowTV);
                                    textViewCashFlow.setHint("CashFlow: $" + String.format("%,d", newTotalCashFlow));
                                    textViewSalary = (TextView)findViewById(R.id.TotalIncomeTV);
                                    textViewSalary.setHint("Total Income: $" + String.format("%,d", (playerInfoRecord.getSalary() + newTotalCashFlow)));
                                    textViewPayDay = (TextView)findViewById(R.id.PaydayTV);
                                    textViewPayDay.setHint("PAYDAY: $" + String.format("%,d", (playerInfoRecord.getSalary() - initTotalExpenses + newTotalCashFlow)));
                                }
                            });
                            marketActionDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    dialogInterface.cancel();
                                }
                            });

                            marketActionDialog.show();
                        } else if (selectedMarketActionType.equals("Real Estate Repair")) {
                            AlertDialog.Builder marketActionDialog = new AlertDialog.Builder(MainPage.this);
                            marketActionDialog.setTitle("Please enter repair costs:");
                            final EditText repairCostInput = new EditText(MainPage.this);
                            repairCostInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                            marketActionDialog.setView(repairCostInput);

                            marketActionDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    int repairCost = Integer.parseInt(repairCostInput.getText().toString());
                                    int newCashOnHand = playerInfoRecord.getCashOnHand() - repairCost;
                                    playerInfoRecord.setCashOnHand(newCashOnHand);
                                    dataSource.updatePlayerInfo(playerInfoRecord);
                                    textViewCashOnHand.setHint("Cash On Hand: $" + String.format("%,d", playerInfoRecord.getCashOnHand()));
                                }
                            });
                            marketActionDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    dialogInterface.cancel();
                                }
                            });

                            marketActionDialog.show();
                        } else if (selectedMarketActionType.equals("Reverse Stock Split")) {
                            Intent intent = new Intent(MainPage.this, StockReverseSplitPage.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(MainPage.this, StockSplitPage.class);
                            startActivity(intent);
                        }

                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });

        payBtn = findViewById(R.id.payButton);
        payBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final ListView listView = new ListView(MainPage.this);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainPage.this, android.R.layout.simple_list_item_1, paymentTypeList);
                listView.setAdapter(adapter);
                AlertDialog.Builder prodialog = new AlertDialog.Builder(MainPage.this);
                prodialog.setCancelable(true);
                prodialog.setView(listView);
                final AlertDialog dialog = prodialog.create();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedPaymentType = adapter.getItem(position);
                        if (selectedPaymentType.equals("Charity")) {
                            int charityAmount = (int)((playerInfoRecord.getSalary() + totalCashFlow) * 0.1);
                            if (playerInfoRecord.getCashOnHand() >= charityAmount) {
                                int newCashOnHand = playerInfoRecord.getCashOnHand() - charityAmount;
                                playerInfoRecord.setCashOnHand(newCashOnHand);
                                dataSource.updatePlayerInfo(playerInfoRecord);
                                textViewCashOnHand.setHint("Cash On Hand: $" + String.format("%,d", playerInfoRecord.getCashOnHand()));
                            } else {
                                Toast.makeText(MainPage.this, "Cash on Hand not enough", Toast.LENGTH_LONG).show();
                            }
                        } else if (selectedPaymentType.equals("Downsized")) {
                            if (playerInfoRecord.getCashOnHand() >= initTotalExpenses) {
                                int newCashOnHand = playerInfoRecord.getCashOnHand() - initTotalExpenses;
                                playerInfoRecord.setCashOnHand(newCashOnHand);
                                dataSource.updatePlayerInfo(playerInfoRecord);
                                textViewCashOnHand.setHint("Cash On Hand: $" + String.format("%,d", playerInfoRecord.getCashOnHand()));
                            } else {
                                Toast.makeText(MainPage.this, "Cash on Hand not enough", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            AlertDialog.Builder collectCashDialog = new AlertDialog.Builder(MainPage.this);
                            collectCashDialog.setTitle("Please enter amount to pay");
                            final EditText amountToPayInput = new EditText(MainPage.this);
                            amountToPayInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                            collectCashDialog.setView(amountToPayInput);

                            collectCashDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    int cashToPay = Integer.parseInt(amountToPayInput.getText().toString());
                                    if (playerInfoRecord.getCashOnHand() >= cashToPay) {
                                        int newCashOnHand = playerInfoRecord.getCashOnHand() - cashToPay;
                                        playerInfoRecord.setCashOnHand(newCashOnHand);
                                        dataSource.updatePlayerInfo(playerInfoRecord);
                                        textViewCashOnHand.setHint("Cash On Hand: $" + String.format("%,d", playerInfoRecord.getCashOnHand()));
                                    } else {
                                        Toast.makeText(MainPage.this, "Cash on Hand not enough", Toast.LENGTH_LONG).show();
                                    }
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout){
            Toast.makeText(this,"Reset Game", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainPage.this, Occupation.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}