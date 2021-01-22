package com.example.cashflowapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ExpensesPage extends AppCompatActivity {
    DatabaseHelper dbHelper;
    ListView listViewRecords;
    private TextView expensestaxtv, expensesothertv, expensescarloantv, expensescreditcardtv, expensesmortgagetv,
            expensesretaildebttv, expensesschoolloantv, expensesbankloantv;
    private View schoolLoanLabelTV, bankLoanLabelTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_page);
        dbHelper = new DatabaseHelper(this);
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF51214D"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("  CashFlow Apps");
        actionBar.setIcon(R.drawable.cash_2);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        expensestaxtv = findViewById(R.id.expenses_taxesTV);
        expensesothertv = findViewById(R.id.expenses_otherTV);
        expensescarloantv = findViewById(R.id.expenses_car_loanTV);
        expensescreditcardtv = findViewById(R.id.expenses_credit_cardTV);
        expensesmortgagetv = findViewById(R.id.expenses_mortgageTV);
        expensesretaildebttv = findViewById(R.id.expenses_retail_debtTV);
        expensesschoolloantv = findViewById(R.id.expenses_school_loanTV);
        expensesbankloantv = findViewById(R.id.expenses_bank_loanTv);
        schoolLoanLabelTV = findViewById(R.id.expenses_school_loanLabelTV);
        bankLoanLabelTV = findViewById(R.id.expenses_bank_loanLabelTv);

        ExpensesRecord taxExpensesRecord = dbHelper.getExpensesByExpensesType("Taxes");
        ExpensesRecord otherExpensesRecord = dbHelper.getExpensesByExpensesType("Other");
        ExpensesRecord carLoanExpensesRecord = dbHelper.getExpensesByExpensesType("Car Loan");
        ExpensesRecord creditCardExpensesRecord = dbHelper.getExpensesByExpensesType("Credit Card");
        ExpensesRecord mortgageExpensesRecord = dbHelper.getExpensesByExpensesType("Mortgage");
        ExpensesRecord retailDebtExpensesRecord = dbHelper.getExpensesByExpensesType("Retail Debt");
        ExpensesRecord schoolLoanExpensesRecord = dbHelper.getExpensesByExpensesType("School Loan");
        ExpensesRecord bankLoanExpensesRecord = dbHelper.getExpensesByExpensesType("Bank Loan");

        expensestaxtv.setHint("Taxes: $"+String.format("%,d", taxExpensesRecord.getExpensesAmount()));
        expensesothertv.setHint("Other: $"+String.format("%,d", otherExpensesRecord.getExpensesAmount()));
        expensescarloantv.setHint("Car Loan: $"+String.format("%,d", carLoanExpensesRecord.getExpensesAmount()));
        expensescreditcardtv.setHint("Credit Card: $"+String.format("%,d", creditCardExpensesRecord.getExpensesAmount()));
        expensesmortgagetv.setHint("Mortgage: $"+String.format("%,d", mortgageExpensesRecord.getExpensesAmount()));
        expensesretaildebttv.setHint("Retail Debt: $"+String.format("%,d", retailDebtExpensesRecord.getExpensesAmount()));
        if (schoolLoanExpensesRecord.getExpensesType() == null) {
            expensesschoolloantv.setVisibility(TextView.GONE);
            schoolLoanLabelTV.setVisibility(TextView.GONE);
        } else {
            expensesschoolloantv.setHint("School Loan: $"+String.format("%,d", schoolLoanExpensesRecord.getExpensesAmount()));
        }
        if (bankLoanExpensesRecord.getExpensesType() == null) {
            expensesbankloantv.setVisibility(TextView.GONE);
            bankLoanLabelTV.setVisibility(TextView.GONE);
        } else {
            expensesbankloantv.setHint("Bank Loan: $"+String.format("%,d", bankLoanExpensesRecord.getExpensesAmount()));
        }

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