package com.example.cashflowapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LiabilitiesPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liabilities_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("  CashFlow Apps");
        actionBar.setIcon(R.drawable.cash_2);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //        Initialize and assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

//        Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.menuLiabilities);

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
                        startActivity(new Intent(getApplicationContext(), MainPage.class));
                        overridePendingTransition(0,0);
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
                        return true;
                }
                return false;
            }
        });
    }
}