package com.example.cashflowapp;

import android.provider.BaseColumns;

public class Golds {
    public Golds() {}

    public static abstract class Gold implements BaseColumns {
        public static final String TABLE_NAME = "gold";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_GOLD_TYPE = "gold_type";
    }
}
