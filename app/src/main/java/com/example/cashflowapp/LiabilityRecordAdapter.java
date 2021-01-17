package com.example.cashflowapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class LiabilityRecordAdapter extends ArrayAdapter<AssetRecord> {

    public LiabilityRecordAdapter(Activity context, int resource, List<AssetRecord> list) {
        super(context, resource, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AssetRecord assetRecord = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_liability_record_adapter, parent, false);
        }

        TextView textViewLiabilityType, textViewLiabilityAmount;

        textViewLiabilityType = (TextView)convertView.findViewById(R.id.textViewLiabilityType);
        textViewLiabilityAmount = (TextView)convertView.findViewById(R.id.textViewLiabilityAmount);

        textViewLiabilityType.setText(assetRecord.getAssetType());
        textViewLiabilityAmount.setText(""+assetRecord.getCost());

        return convertView;
    }
}