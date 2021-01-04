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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class StockReverseSplitPage extends AppCompatActivity {
    private TextView stocktypetoreversesplittv;
    private Button reverseSplitStockBtn;

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
        setContentView(R.layout.activity_stock_reverse_split_page);
        databaseHelper = new DatabaseHelper(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("  CashFlow Apps");
        actionBar.setIcon(R.drawable.cash_2);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        stocktypetoreversesplittv = findViewById(R.id.stockTypeToReverseSplitTV);

        List<StockMutualFundCODRecord> stockMutualFundCODRecordList =  databaseHelper.getAllPurchasedStocks();

        stocktypetoreversesplittv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ListView listView = new ListView(StockReverseSplitPage.this);
                String[] stockPurchasedArr = {};
                for (int i = 0; i < stockMutualFundCODRecordList.size(); i++) {
                    String stockType = stockMutualFundCODRecordList.get(i).getStockType();
                    if (!containElement(stockPurchasedArr, stockType)) {
                        stockPurchasedArr = addElementToArray(stockPurchasedArr.length, stockPurchasedArr, stockType);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(StockReverseSplitPage.this, android.R.layout.simple_list_item_1, stockPurchasedArr);
                listView.setAdapter(adapter);
                AlertDialog.Builder prodialog = new AlertDialog.Builder(StockReverseSplitPage.this);
                prodialog.setCancelable(true);
                prodialog.setView(listView);
                final AlertDialog dialog = prodialog.create();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedStockType = adapter.getItem(position);
                        stocktypetoreversesplittv.setText(selectedStockType);
                        Toast.makeText(StockReverseSplitPage.this, "Stock type selected is: " + selectedStockType, Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                    }
                });

                dialog.show();

            }
        });

//        Button Split Stock

        reverseSplitStockBtn = findViewById(R.id.stock_reverse_split_button);
        reverseSplitStockBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (stocktypetoreversesplittv.getText().toString().isEmpty()) {
                    Toast.makeText(StockReverseSplitPage.this, "Please Select Stock Type First", Toast.LENGTH_LONG).show();
                } else {
                    reverseSplitStockFunc(v);
                    Intent intent = new Intent(StockReverseSplitPage.this, MainPage.class);
                    startActivity(intent);
                }
            }
        });
    }


    public void reverseSplitStockFunc(View v) {
        String selectedStockType = stocktypetoreversesplittv.getText().toString();
        List<StockMutualFundCODRecord> selectedStockMutualFundCODRecordList = databaseHelper.getPurchasedStocksByType(selectedStockType);
        for (int i = 0; i < selectedStockMutualFundCODRecordList.size(); i++) {
            StockMutualFundCODRecord selectedStockRecord = selectedStockMutualFundCODRecordList.get(i);
            int currentNumOfShares = selectedStockRecord.getNumOfShares();
            selectedStockRecord.setNumOfShares(currentNumOfShares / 2);
            databaseHelper.updateStockMutualFundCOD(selectedStockRecord);
        }
        this.finish();
    }
}