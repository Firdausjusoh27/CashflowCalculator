package com.example.cashflowapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class IncomeRecordAdapter extends ArrayAdapter<IncomeRecord>{

    public IncomeRecordAdapter(Activity context, int resource, List<IncomeRecord> list) {
        super(context, resource, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IncomeRecord incomeRecord = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_income_record_adapter, parent, false);
        }

        TextView textViewAssetType, textViewAmount;

        textViewAssetType = (TextView)convertView.findViewById(R.id.textViewIncomeType);
        textViewAmount = (TextView)convertView.findViewById(R.id.textViewCashFlowAmount);

        textViewAssetType.setText(incomeRecord.getAssetType());
        textViewAmount.setText(""+incomeRecord.getCashFlowAmount());

        return convertView;
    }
}