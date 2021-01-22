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

import java.util.List;

public class PayOffLoanPage extends AppCompatActivity {

    private TextView payoffloantypetv, cashonhandtv, owetobanktv, monthlypaymenttv;
    private EditText payloanamounttv;
    private Button payOffLoanBtn, payOffLoanCancelBtn;

    DatabaseHelper databaseHelper;
    LiabilityRecord liabilitySelected;
    ExpensesRecord expensesSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_off_loan_page);
        databaseHelper = new DatabaseHelper(this);
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF51214D"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("  CashFlow Apps");
        actionBar.setIcon(R.drawable.cash_2);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        payoffloantypetv = findViewById(R.id.payOffLoanTypeTV);
        payloanamounttv = findViewById(R.id.payLoanAmountTV);
        cashonhandtv = findViewById(R.id.payOffLoanCashOnHandLabel);
        owetobanktv = findViewById(R.id.payOffLoanOweToBankLabel);
        monthlypaymenttv = findViewById(R.id.payOffLoanMonthlyPaymentLabel);

        int cashOnHand = databaseHelper.getPlayerInfo().getCashOnHand();
        cashonhandtv.setHint("Cash on Hand: $"+String.format("%,d", cashOnHand));

        List<LiabilityRecord> liabilityRecordList = databaseHelper.getAllLoansToPay();
        String[] loanAllowedToPay = new String[liabilityRecordList.size()];
        for (int i = 0; i < liabilityRecordList.size(); i++)
            loanAllowedToPay[i] = liabilityRecordList.get(i).getLoanType();


        payoffloantypetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ListView listView = new ListView(PayOffLoanPage.this);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PayOffLoanPage.this, android.R.layout.simple_list_item_1, loanAllowedToPay);
                listView.setAdapter(adapter);
                AlertDialog.Builder prodialog = new AlertDialog.Builder(PayOffLoanPage.this);
                prodialog.setCancelable(true);
                prodialog.setView(listView);
                final AlertDialog dialog = prodialog.create();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedLoanType = adapter.getItem(position);
                        payoffloantypetv.setText(selectedLoanType);
                        liabilitySelected = liabilityRecordList.get(position);
                        if (!selectedLoanType.equals("Bank Loan")) {
                            int loanAmountToPay = liabilitySelected.getLoanAmount();
                            payloanamounttv.setText(String.valueOf(loanAmountToPay));
                        }
                        owetobanktv.setHint("Owed to Bank: $"+String.format("%,d", liabilitySelected.getLoanAmount()));
                        expensesSelected = databaseHelper.getExpensesByExpensesType(liabilitySelected.getLoanType());
                        monthlypaymenttv.setHint("Monthly Payment: $"+String.format("%,d", expensesSelected.getExpensesAmount()));
                        Toast.makeText(PayOffLoanPage.this, "Loan type selected is: " + selectedLoanType, Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                    }
                });

                dialog.show();

            }
        });

        //        Cancel Button
        payOffLoanCancelBtn = findViewById(R.id.pay_off_loan_cancel);
        payOffLoanCancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayOffLoanPage.this, MainPage.class);
                startActivity(intent);

            }
        });

//        Button Pay Off Loan

        payOffLoanBtn = findViewById(R.id.pay_off_loan_button);
        payOffLoanBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (cashOnHand >= Integer.parseInt(payloanamounttv.getText().toString())) {
                    savePayOffLoan(v);
                    Intent intent = new Intent(PayOffLoanPage.this, MainPage.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(PayOffLoanPage.this, "Cash on hand not enough", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void savePayOffLoan(View v) {
        int payOffLoanAmount;
        payOffLoanAmount = Integer.parseInt(payloanamounttv.getText().toString());

        DatabaseHelper dataSource = new DatabaseHelper(this);

        if (!liabilitySelected.getLoanType().equals("Bank Loan")) {
            dataSource.deleteLiability(liabilitySelected.getId());
            dataSource.deleteExpenses(liabilitySelected.getLoanType());
        } else {
            int newLoanAmount = liabilitySelected.getLoanAmount() - payOffLoanAmount;
            LiabilityRecord liabilityRecord = new LiabilityRecord();
            liabilityRecord.setId(liabilitySelected.getId());
            liabilityRecord.setLoanType(liabilitySelected.getLoanType());
            liabilityRecord.setLoanAmount(newLoanAmount);
            dataSource.updateLiability(liabilityRecord);

            int bankLoanExpensesToDeductAmount = (int)(Double.valueOf(String.valueOf(payOffLoanAmount)) * 0.1);
            ExpensesRecord bankLoanExpenses = dataSource.getBankLoanExpenses();
            bankLoanExpenses.setExpensesAmount(bankLoanExpenses.getExpensesAmount() - bankLoanExpensesToDeductAmount);
            dataSource.updateExpenses(bankLoanExpenses);
        }

        updatePlayerInfo(v, payOffLoanAmount);

        this.finish();
    }

    public void updatePlayerInfo(View v, int payOffLoanAmount) {
        DatabaseHelper dataSource = new DatabaseHelper(this);
        PlayerInfoRecord playerInfoRecord = dataSource.getPlayerInfo();
        int currentCash = playerInfoRecord.getCashOnHand();
        int newCashOnHand = currentCash - payOffLoanAmount;
        playerInfoRecord.setCashOnHand(newCashOnHand);
        dataSource.updatePlayerInfo(playerInfoRecord);
    }
}