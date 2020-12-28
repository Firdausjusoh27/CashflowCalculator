package com.example.cashflowapp;

import android.provider.BaseColumns;

public class PlayerInfo {
    public PlayerInfo() {}

    public static abstract class Player implements BaseColumns {
        public static final String TABLE_NAME = "player_info";
        public static final String COLUMN_PROFESSION = "profession";
        public static final String COLUMN_DREAM = "dream";
        public static final String COLUMN_AUDIT_NAME = "audit_name";
        public static final String COLUMN_SALARY = "salary";
        public static final String COLUMN_CASH_ON_HAND = "cash_on_hand";
    }
}