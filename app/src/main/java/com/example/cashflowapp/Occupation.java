package com.example.cashflowapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Occupation extends AppCompatActivity {

    private TextView professiontv, dreamtv, auditor;
    private Button startBtn;
    private String myText;
    static String[] professions = {"Doctor", "Lawyer", "Mechanic", "Nurse", "Police", "Secretary", "Janitor"};
    static String[] dreams = {"Buy a Forest", "Be a Jet-Setter", "African Photo Safari", "Gift of Faith"};

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occupation);
        databaseHelper = new DatabaseHelper(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("  CashFlow Apps");
        actionBar.setIcon(R.drawable.cash_2);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        professiontv = findViewById(R.id.professionTV);
        dreamtv = findViewById(R.id.dreamTV);
        auditor = findViewById(R.id.auditorTV);


        //        Start -- Alert Dialog for Profession TextView

        professiontv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ListView listView = new ListView(Occupation.this);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Occupation.this, android.R.layout.simple_list_item_1, professions);
                listView.setAdapter(adapter);
                AlertDialog.Builder prodialog = new AlertDialog.Builder(Occupation.this);
                prodialog.setCancelable(true);
                prodialog.setView(listView);
                final AlertDialog dialog = prodialog.create();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selected_profession = adapter.getItem(position);
                        professiontv.setText(selected_profession);
                        Toast.makeText(Occupation.this, "Profession is: " + selected_profession, Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                    }
                });

                dialog.show();

            }
        });

//        End --- Alert Dialog for Dream TextView


        //        Start -- Alert Dialog for Profession TextView

        dreamtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ListView listView = new ListView(Occupation.this);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Occupation.this, android.R.layout.simple_list_item_1, dreams);
                listView.setAdapter(adapter);
                AlertDialog.Builder dreamdialog = new AlertDialog.Builder(Occupation.this);
                dreamdialog.setCancelable(true);
                dreamdialog.setView(listView);
                final AlertDialog dialog = dreamdialog.create();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selected_dream = adapter.getItem(position);
                        dreamtv.setText(selected_dream);
                        Toast.makeText(Occupation.this, "Dream is: " + selected_dream, Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                    }
                });

                dialog.show();

            }
        });

//        End --- Alert Dialog for Profession TextView


//        Start -- Alert Dialog for Auditor TextView
        auditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(Occupation.this);
                mydialog.setTitle("Auditor(Person To The Right)");
                final EditText auditorInput = new EditText(Occupation.this);
                auditorInput.setInputType(InputType.TYPE_CLASS_TEXT);
                mydialog.setView(auditorInput);

                mydialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        myText = auditorInput.getText().toString();
                        auditor.setText(myText);
                        Toast.makeText(Occupation.this, "Auditor is: " + myText, Toast.LENGTH_LONG).show();

                    }
                });
                mydialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.cancel();
                    }
                });

                mydialog.show();

            }
        });
        //        End -- Alert Dialog for Dream TextView

//        Button Start Navigation

        startBtn = findViewById(R.id.start_button);
        startBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deletePlayerInfoRecord();
                savePlayerInfo(v);
                deleteExpenses();
                deleteLiabilities();
                String profession = professiontv.getText().toString();
                saveExpensesAndLiabilities(profession);
                Intent intent = new Intent(Occupation.this, MainPage.class);
                startActivity(intent);
            }
        });
    }

    public void savePlayerInfo(View v) {
        String profession, dream, auditName;
        int salary, cashOnHand;
        profession = professiontv.getText().toString();
        if(profession.isEmpty()) {
            professiontv.setError("Please Choose Profession");
            return;
        }
        dream = dreamtv.getText().toString();
        if(dream.isEmpty()) {
            dreamtv.setError("Please Choose Dream");
            return;
        }
        auditName = auditor.getText().toString();
        if(auditName.isEmpty()) {
            auditor.setError("Please enter auditor name");
            return;
        }
        switch (profession){
            case "Doctor":
                salary = 13200;
                cashOnHand = 3950;
                break;
            case "Lawyer":
                salary = 7500;
                cashOnHand = 2480;
                break;
            case "Mechanic":
                salary = 2000;
                cashOnHand = 1390;
                break;
            case "Nurse":
                salary = 3100;
                cashOnHand = 1600;
                break;
            case "Police":
                salary = 3000;
                cashOnHand = 1640;
                break;
            case "Secretary":
                salary = 2500;
                cashOnHand = 1590;
                break;
            case "Janitor":
                salary = 1600;
                cashOnHand = 1210;
                break;
            default:
                salary = 800;
                cashOnHand = 1300;
        }
        PlayerInfoRecord playerInfoRecord = new PlayerInfoRecord();
        playerInfoRecord.setProfession(profession);
        playerInfoRecord.setDream(dream);
        playerInfoRecord.setAuditName(auditName);
        playerInfoRecord.setSalary(salary);
        playerInfoRecord.setCashOnHand(cashOnHand);

        DatabaseHelper playerInfoDataSource = new DatabaseHelper(this);
        playerInfoDataSource.insertPlayerInfo(playerInfoRecord);

        this.finish();
    }

    public Integer deletePlayerInfoRecord() {
        DatabaseHelper playerInfoDataSource = new DatabaseHelper(this);
        Integer rowDeleted = playerInfoDataSource.deletePlayerInfo();

        this.finish();
        return rowDeleted;
    }

    public Integer deleteExpenses() {
        DatabaseHelper dataSource = new DatabaseHelper(this);
        Integer rowDeleted = dataSource.deleteAllExpenses();

        this.finish();
        return rowDeleted;
    }

    public Integer deleteLiabilities() {
        DatabaseHelper dataSource = new DatabaseHelper(this);
        Integer rowDeleted = dataSource.deleteAllLiabilities();

        this.finish();
        return rowDeleted;
    }

    public void saveExpensesAndLiabilities(String profession) {
        ExpensesRecord expensesRecord = new ExpensesRecord();
        LiabilityRecord liabilityRecord = new LiabilityRecord();
        DatabaseHelper dataSource = new DatabaseHelper(this);
        switch (profession){
            case "Doctor":
                expensesRecord.setExpensesType("Taxes");
                expensesRecord.setExpensesAmount(3420);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Other");
                expensesRecord.setExpensesAmount(2880);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Car Loan");
                expensesRecord.setExpensesAmount(380);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Credit Card");
                expensesRecord.setExpensesAmount(270);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Mortgage");
                expensesRecord.setExpensesAmount(1900);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Retail Debt");
                expensesRecord.setExpensesAmount(50);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("School Loan");
                expensesRecord.setExpensesAmount(750);
                dataSource.insertExpenses(expensesRecord);

                liabilityRecord.setLoanType("Car Loan");
                liabilityRecord.setLoanAmount(19000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Credit Card");
                liabilityRecord.setLoanAmount(9000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Mortgage");
                liabilityRecord.setLoanAmount(202000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Retail Debt");
                liabilityRecord.setLoanAmount(1000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("School Loan");
                liabilityRecord.setLoanAmount(150000);
                dataSource.insertLiability(liabilityRecord);
                break;
            case "Lawyer":
                expensesRecord.setExpensesType("Taxes");
                expensesRecord.setExpensesAmount(1830);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Other");
                expensesRecord.setExpensesAmount(1650);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Car Loan");
                expensesRecord.setExpensesAmount(220);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Credit Card");
                expensesRecord.setExpensesAmount(180);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Mortgage");
                expensesRecord.setExpensesAmount(1100);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Retail Debt");
                expensesRecord.setExpensesAmount(50);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("School Loan");
                expensesRecord.setExpensesAmount(390);
                dataSource.insertExpenses(expensesRecord);

                liabilityRecord.setLoanType("Car Loan");
                liabilityRecord.setLoanAmount(11000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Credit Card");
                liabilityRecord.setLoanAmount(6000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Mortgage");
                liabilityRecord.setLoanAmount(115000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Retail Debt");
                liabilityRecord.setLoanAmount(1000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("School Loan");
                liabilityRecord.setLoanAmount(78000);
                dataSource.insertLiability(liabilityRecord);
                break;
            case "Mechanic":
                expensesRecord.setExpensesType("Taxes");
                expensesRecord.setExpensesAmount(360);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Other");
                expensesRecord.setExpensesAmount(450);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Car Loan");
                expensesRecord.setExpensesAmount(60);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Credit Card");
                expensesRecord.setExpensesAmount(60);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Mortgage");
                expensesRecord.setExpensesAmount(300);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Retail Debt");
                expensesRecord.setExpensesAmount(50);
                dataSource.insertExpenses(expensesRecord);

                liabilityRecord.setLoanType("Car Loan");
                liabilityRecord.setLoanAmount(3000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Credit Card");
                liabilityRecord.setLoanAmount(2000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Mortgage");
                liabilityRecord.setLoanAmount(31000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Retail Debt");
                liabilityRecord.setLoanAmount(1000);
                dataSource.insertLiability(liabilityRecord);
                break;
            case "Nurse":
                expensesRecord.setExpensesType("Taxes");
                expensesRecord.setExpensesAmount(600);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Other");
                expensesRecord.setExpensesAmount(710);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Car Loan");
                expensesRecord.setExpensesAmount(100);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Credit Card");
                expensesRecord.setExpensesAmount(90);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Mortgage");
                expensesRecord.setExpensesAmount(400);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Retail Debt");
                expensesRecord.setExpensesAmount(50);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("School Loan");
                expensesRecord.setExpensesAmount(30);
                dataSource.insertExpenses(expensesRecord);

                liabilityRecord.setLoanType("Car Loan");
                liabilityRecord.setLoanAmount(5000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Credit Card");
                liabilityRecord.setLoanAmount(3000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Mortgage");
                liabilityRecord.setLoanAmount(47000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Retail Debt");
                liabilityRecord.setLoanAmount(1000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("School Loan");
                liabilityRecord.setLoanAmount(6000);
                dataSource.insertLiability(liabilityRecord);
                break;
            case "Police":
                expensesRecord.setExpensesType("Taxes");
                expensesRecord.setExpensesAmount(580);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Other");
                expensesRecord.setExpensesAmount(690);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Car Loan");
                expensesRecord.setExpensesAmount(100);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Credit Card");
                expensesRecord.setExpensesAmount(60);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Mortgage");
                expensesRecord.setExpensesAmount(400);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Retail Debt");
                expensesRecord.setExpensesAmount(50);
                dataSource.insertExpenses(expensesRecord);

                liabilityRecord.setLoanType("Car Loan");
                liabilityRecord.setLoanAmount(5000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Credit Card");
                liabilityRecord.setLoanAmount(2000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Mortgage");
                liabilityRecord.setLoanAmount(46000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Retail Debt");
                liabilityRecord.setLoanAmount(1000);
                dataSource.insertLiability(liabilityRecord);
                break;
            case "Secretary":
                expensesRecord.setExpensesType("Taxes");
                expensesRecord.setExpensesAmount(460);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Other");
                expensesRecord.setExpensesAmount(570);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Car Loan");
                expensesRecord.setExpensesAmount(80);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Credit Card");
                expensesRecord.setExpensesAmount(60);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Mortgage");
                expensesRecord.setExpensesAmount(400);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Retail Debt");
                expensesRecord.setExpensesAmount(50);
                dataSource.insertExpenses(expensesRecord);

                liabilityRecord.setLoanType("Car Loan");
                liabilityRecord.setLoanAmount(4000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Credit Card");
                liabilityRecord.setLoanAmount(2000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Mortgage");
                liabilityRecord.setLoanAmount(38000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Retail Debt");
                liabilityRecord.setLoanAmount(1000);
                dataSource.insertLiability(liabilityRecord);
                break;
            case "Janitor":
                expensesRecord.setExpensesType("Taxes");
                expensesRecord.setExpensesAmount(280);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Other");
                expensesRecord.setExpensesAmount(300);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Car Loan");
                expensesRecord.setExpensesAmount(60);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Credit Card");
                expensesRecord.setExpensesAmount(60);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Mortgage");
                expensesRecord.setExpensesAmount(200);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Retail Debt");
                expensesRecord.setExpensesAmount(50);
                dataSource.insertExpenses(expensesRecord);

                liabilityRecord.setLoanType("Car Loan");
                liabilityRecord.setLoanAmount(4000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Credit Card");
                liabilityRecord.setLoanAmount(2000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Mortgage");
                liabilityRecord.setLoanAmount(20000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Retail Debt");
                liabilityRecord.setLoanAmount(1000);
                dataSource.insertLiability(liabilityRecord);
                break;
            default:
                expensesRecord.setExpensesType("Taxes");
                expensesRecord.setExpensesAmount(300);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Other");
                expensesRecord.setExpensesAmount(300);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Car Loan");
                expensesRecord.setExpensesAmount(60);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Credit Card");
                expensesRecord.setExpensesAmount(60);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Mortgage");
                expensesRecord.setExpensesAmount(200);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("Retail Debt");
                expensesRecord.setExpensesAmount(50);
                dataSource.insertExpenses(expensesRecord);

                expensesRecord.setExpensesType("School Loan");
                expensesRecord.setExpensesAmount(0);
                dataSource.insertExpenses(expensesRecord);

                liabilityRecord.setLoanType("Car Loan");
                liabilityRecord.setLoanAmount(4000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Credit Card");
                liabilityRecord.setLoanAmount(2000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Mortgage");
                liabilityRecord.setLoanAmount(25000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("Retail Debt");
                liabilityRecord.setLoanAmount(1000);
                dataSource.insertLiability(liabilityRecord);

                liabilityRecord.setLoanType("School Loan");
                liabilityRecord.setLoanAmount(0);
                dataSource.insertLiability(liabilityRecord);
        }

        this.finish();
    }


}