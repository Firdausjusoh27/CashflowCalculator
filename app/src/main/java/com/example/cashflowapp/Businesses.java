package com.example.cashflowapp;

import android.provider.BaseColumns;

public class Businesses {
    public Businesses() {}

    public static abstract class Business implements BaseColumns {
        public static final String TABLE_NAME = "business";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_BUSINESS_TYPE = "business_type";
        public static final String COLUMN_COST = "cost";
        public static final String COLUMN_DOWN_PAYMENT = "down_payment";
        public static final String COLUMN_CASH_FLOW = "cash_flow";
    }
}
