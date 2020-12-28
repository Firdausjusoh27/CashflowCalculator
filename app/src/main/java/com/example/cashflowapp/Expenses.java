package com.example.cashflowapp;

import android.provider.BaseColumns;

public class Expenses {
    public Expenses() {}

    public static abstract class Expense implements BaseColumns {
        public static final String TABLE_NAME = "expenses";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_EXPENSES_TYPE = "expenses_type";
        public static final String COLUMN_EXPENSES_AMOUNT = "expenses_amount";
    }
}
