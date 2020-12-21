package com.example.cashflowapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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
    static String[] professions = {"Doctor", "Lawyer", "Mechanic", "Nurse", "Police", "Secretary"};
    static String[] dreams = {"Buy a Forest", "Be a Jet-Setter", "African Photo Safari", "Gift of Faith"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occupation);
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
                Intent intent = new Intent(Occupation.this, MainPage.class);
                startActivity(intent);
            }
        });
    }


}