package com.example.cashflowapp;

import android.provider.BaseColumns;

public class StockMutualFundCOD {
    public StockMutualFundCOD() {}

    public static abstract class StockMFundCOD implements BaseColumns {
        public static final String TABLE_NAME = "stock_mutual_fund_cod";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_STOCK_TYPE = "stock_type";
        public static final String COLUMN_BUYING_PRICE = "buying_price";
        public static final String COLUMN_NUM_SHARES = "num_of_shares";
        public static final String COLUMN_DIVIDENDS = "monthly_dividends";
        public static final String COLUMN_INTEREST = "monthly_interest";
    }
}
