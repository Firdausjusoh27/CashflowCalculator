package com.example.cashflowapp;

import android.provider.BaseColumns;

public class Liabilities {
    public Liabilities() {}

    public static abstract class Liability implements BaseColumns {
        public static final String TABLE_NAME = "liability";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_LOAN_TYPE = "loan_type";
        public static final String COLUMN_LOAN_AMOUNT = "loan_amount";
    }
}
