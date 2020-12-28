package com.example.cashflowapp;

import android.provider.BaseColumns;

public class RealEstates {
    public RealEstates() {}

    public static abstract class RealEstate implements BaseColumns {
        public static final String TABLE_NAME = "real_estate";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_REAL_ESTATE_TYPE = "real_estate_type";
        public static final String COLUMN_COST = "cost";
        public static final String COLUMN_DOWN_PAYMENT = "down_payment";
        public static final String COLUMN_CASH_FLOW = "cash_flow";
        public static final String COLUMN_NUM_OF_UNITS = "number_of_units";
    }
}
