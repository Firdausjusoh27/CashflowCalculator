package com.example.cashflowapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AssetRecordAdapter extends ArrayAdapter<AssetRecord> {

    public AssetRecordAdapter(Activity context, int resource, List<AssetRecord> list) {
        super(context, resource, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AssetRecord assetRecord = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_asset_record_adapter, parent, false);
        }

        TextView textViewAssetType, textViewAmount;

        textViewAssetType = (TextView)convertView.findViewById(R.id.textViewAssetType);
        textViewAmount = (TextView)convertView.findViewById(R.id.textViewAmount);

        textViewAssetType.setText(assetRecord.getAssetType());
        textViewAmount.setText(""+assetRecord.getCost());

        return convertView;
    }
}