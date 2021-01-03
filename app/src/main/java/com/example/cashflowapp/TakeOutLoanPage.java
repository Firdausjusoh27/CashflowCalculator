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

public class TakeOutLoanPage extends AppCompatActivity {

    private EditText takeoutloantv;
    private Button takeOutLoanBtn;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_out_loan_page);
        databaseHelper = new DatabaseHelper(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("  CashFlow Apps");
        actionBar.setIcon(R.drawable.cash_2);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        takeoutloantv = findViewById(R.id.takeOutLoanAmountTV);

//        Button Take Out Loan

        takeOutLoanBtn = findViewById(R.id.take_out_loan_button);
        takeOutLoanBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                saveTakeOutLoan(v);
                Intent intent = new Intent(TakeOutLoanPage.this, MainPage.class);
                startActivity(intent);
            }
        });
    }


    public void saveTakeOutLoan(View v) {
        int takeOutLoanAmount;
        takeOutLoanAmount = Integer.parseInt(takeoutloantv.getText().toString());

        DatabaseHelper dataSource = new DatabaseHelper(this);

        LiabilityRecord liabilityRecord = new LiabilityRecord();

        boolean hasLiabilityBankLoan = dataSource.getLiabilityBankLoanExistence();
        if (hasLiabilityBankLoan == true) {
            LiabilityRecord currentLiabilityBankLoan = dataSource.getLiabilityBankLoan();
            liabilityRecord.setId(currentLiabilityBankLoan.getId());
            liabilityRecord.setLoanType(currentLiabilityBankLoan.getLoanType());
            liabilityRecord.setLoanAmount((currentLiabilityBankLoan.getLoanAmount() + takeOutLoanAmount));
            dataSource.updateLiability(liabilityRecord);
        } else {
            liabilityRecord.setLoanType("Bank Loan");
            liabilityRecord.setLoanAmount(takeOutLoanAmount);
            dataSource.insertLiability(liabilityRecord);
        }

        ExpensesRecord expensesRecord = new ExpensesRecord();
        boolean hasBankLoan = dataSource.getBankLoanExistence();
        int newBankLoanExpensesAmount = (int)(Double.valueOf(String.valueOf(takeOutLoanAmount)) * 0.1);
        if (hasBankLoan == true) {
            ExpensesRecord currentBankLoan = dataSource.getBankLoanExpenses();
            expensesRecord.setId(currentBankLoan.getId());
            expensesRecord.setExpensesType(currentBankLoan.getExpensesType());
            expensesRecord.setExpensesAmount((currentBankLoan.getExpensesAmount() + newBankLoanExpensesAmount));
            dataSource.updateExpenses(expensesRecord);
        } else {
            expensesRecord.setExpensesType("Bank Loan");
            expensesRecord.setExpensesAmount(newBankLoanExpensesAmount);
            dataSource.insertExpenses(expensesRecord);
        }

        updatePlayerInfo(v, takeOutLoanAmount);

        this.finish();
    }

    public void updatePlayerInfo(View v, int takeOutLoanAmount) {
        DatabaseHelper dataSource = new DatabaseHelper(this);
        PlayerInfoRecord playerInfoRecord = dataSource.getPlayerInfo();
        int currentCash = playerInfoRecord.getCashOnHand();
        int newCashOnHand = currentCash + takeOutLoanAmount;
        playerInfoRecord.setCashOnHand(newCashOnHand);
        dataSource.updatePlayerInfo(playerInfoRecord);
    }
}