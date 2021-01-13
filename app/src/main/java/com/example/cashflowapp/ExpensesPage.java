package com.example.cashflowapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ExpensesPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("  CashFlow Apps");
        actionBar.setIcon(R.drawable.cash_2);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //        Initialize and assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

//        Set Expenses Selected
        bottomNavigationView.setSelectedItemId(R.id.menuExpenses);

//        Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.menuExpenses:
                        return true;

                    case R.id.menuIncome:
                        startActivity(new Intent(getApplicationContext(), IncomePage.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.menuHome:
                        startActivity(new Intent(getApplicationContext(), MainPage.class));
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

    }
}