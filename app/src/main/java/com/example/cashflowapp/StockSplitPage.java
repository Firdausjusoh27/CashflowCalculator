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

import java.util.Arrays;
import java.util.List;

public class StockSplitPage extends AppCompatActivity {

    private TextView stocktypetosplittv;
    private Button splitStockBtn;

    DatabaseHelper databaseHelper;

    private static boolean containElement(String[] stockTypeArr, String toCheckValue) {
        boolean hasElement;
        hasElement = Arrays.asList(stockTypeArr).contains(toCheckValue);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_split_page);
        databaseHelper = new DatabaseHelper(this);
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF51214D"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("  CashFlow Apps");
        actionBar.setIcon(R.drawable.cash_2);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        stocktypetosplittv = findViewById(R.id.stockTypeToSplitTV);

        List<StockMutualFundCODRecord> stockMutualFundCODRecordList =  databaseHelper.getAllPurchasedStocks();
        stocktypetosplittv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ListView listView = new ListView(StockSplitPage.this);
                String[] stockPurchasedArr = {};
                for (int i = 0; i < stockMutualFundCODRecordList.size(); i++) {
                    String stockType = stockMutualFundCODRecordList.get(i).getStockType();
                    if (!containElement(stockPurchasedArr, stockType)) {
                        stockPurchasedArr = addElementToArray(stockPurchasedArr.length, stockPurchasedArr, stockType);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(StockSplitPage.this, android.R.layout.simple_list_item_1, stockPurchasedArr);
                listView.setAdapter(adapter);
                AlertDialog.Builder prodialog = new AlertDialog.Builder(StockSplitPage.this);
                prodialog.setCancelable(true);
                prodialog.setView(listView);
                final AlertDialog dialog = prodialog.create();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedStockType = adapter.getItem(position);
                        stocktypetosplittv.setText(selectedStockType);
                        Toast.makeText(StockSplitPage.this, "Stock type selected is: " + selectedStockType, Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                    }
                });

                dialog.show();

            }
        });

//        Button Split Stock

        splitStockBtn = findViewById(R.id.stock_split_button);
        splitStockBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (stocktypetosplittv.getText().toString().isEmpty()) {
                    Toast.makeText(StockSplitPage.this, "Please Select Stock Type First", Toast.LENGTH_LONG).show();
                } else {
                    splitStockFunc(v);
                    Intent intent = new Intent(StockSplitPage.this, MainPage.class);
                    startActivity(intent);
                }
            }
        });
    }


    public void splitStockFunc(View v) {
        String selectedStockType = stocktypetosplittv.getText().toString();
        List<StockMutualFundCODRecord> selectedStockMutualFundCODRecordList = databaseHelper.getPurchasedStocksByType(selectedStockType);
        for (int i = 0; i < selectedStockMutualFundCODRecordList.size(); i++) {
            StockMutualFundCODRecord selectedStockRecord = selectedStockMutualFundCODRecordList.get(i);
            int currentNumOfShares = selectedStockRecord.getNumOfShares();
            selectedStockRecord.setNumOfShares(currentNumOfShares * 2);
            databaseHelper.updateStockMutualFundCOD(selectedStockRecord);
        }
        this.finish();
    }
}