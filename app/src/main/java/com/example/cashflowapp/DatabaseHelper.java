package com.example.cashflowapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "cash_flow_calculator.db";

    private static final String SQL_CREATE_PLAYER_INFO =
            "CREATE TABLE " + PlayerInfo.Player.TABLE_NAME + "(" +
                    PlayerInfo.Player.COLUMN_PROFESSION + " TEXT," +
                    PlayerInfo.Player.COLUMN_DREAM + " TEXT," +
                    PlayerInfo.Player.COLUMN_AUDIT_NAME + " TEXT," +
                    PlayerInfo.Player.COLUMN_SALARY + " INTEGER," +
                    PlayerInfo.Player.COLUMN_CASH_ON_HAND + " INTEGER)";
    private static final String SQL_DELETE_PLAYER_INFO =
            "DROP TABLE IF EXISTS " + PlayerInfo.Player.TABLE_NAME;
    private String[] allColumn = {
            PlayerInfo.Player.COLUMN_PROFESSION,
            PlayerInfo.Player.COLUMN_DREAM,
            PlayerInfo.Player.COLUMN_AUDIT_NAME,
            PlayerInfo.Player.COLUMN_SALARY,
            PlayerInfo.Player.COLUMN_CASH_ON_HAND,
    };

    private static final String SQL_CREATE_EXPENSES =
            "CREATE TABLE " + Expenses.Expense.TABLE_NAME + "(" +
                    Expenses.Expense.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Expenses.Expense.COLUMN_EXPENSES_TYPE + " TEXT," +
                    Expenses.Expense.COLUMN_EXPENSES_AMOUNT + " INTEGER)";
    private static final String SQL_DELETE_EXPENSES =
            "DROP TABLE IF EXISTS " + Expenses.Expense.TABLE_NAME;
    private String[] expensesAllColumn = {
            Expenses.Expense.COLUMN_ID,
            Expenses.Expense.COLUMN_EXPENSES_TYPE,
            Expenses.Expense.COLUMN_EXPENSES_AMOUNT,
    };

    private static final String SQL_CREATE_STOCK_MF_COD =
            "CREATE TABLE " + StockMutualFundCOD.StockMFundCOD.TABLE_NAME + "(" +
                    StockMutualFundCOD.StockMFundCOD.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    StockMutualFundCOD.StockMFundCOD.COLUMN_STOCK_TYPE + " TEXT," +
                    StockMutualFundCOD.StockMFundCOD.COLUMN_BUYING_PRICE + " INTEGER," +
                    StockMutualFundCOD.StockMFundCOD.COLUMN_NUM_SHARES + " INTEGER," +
                    StockMutualFundCOD.StockMFundCOD.COLUMN_DIVIDENDS + " INTEGER," +
                    StockMutualFundCOD.StockMFundCOD.COLUMN_INTEREST + " INTEGER)";
    private static final String SQL_DELETE_STOCK_MF_COD =
            "DROP TABLE IF EXISTS " + StockMutualFundCOD.StockMFundCOD.TABLE_NAME;
    private String[] stockMFundCodAllColumn = {
            StockMutualFundCOD.StockMFundCOD.COLUMN_ID,
            StockMutualFundCOD.StockMFundCOD.COLUMN_STOCK_TYPE,
            StockMutualFundCOD.StockMFundCOD.COLUMN_BUYING_PRICE,
            StockMutualFundCOD.StockMFundCOD.COLUMN_NUM_SHARES,
            StockMutualFundCOD.StockMFundCOD.COLUMN_DIVIDENDS,
            StockMutualFundCOD.StockMFundCOD.COLUMN_INTEREST,
    };

    private static final String SQL_CREATE_REAL_ESTATE =
            "CREATE TABLE " + RealEstates.RealEstate.TABLE_NAME + "(" +
                    RealEstates.RealEstate.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    RealEstates.RealEstate.COLUMN_REAL_ESTATE_TYPE + " TEXT," +
                    RealEstates.RealEstate.COLUMN_COST + " INTEGER," +
                    RealEstates.RealEstate.COLUMN_DOWN_PAYMENT + " INTEGER," +
                    RealEstates.RealEstate.COLUMN_CASH_FLOW + " INTEGER," +
                    RealEstates.RealEstate.COLUMN_NUM_OF_UNITS + " INTEGER)";
    private static final String SQL_DELETE_REAL_ESTATE =
            "DROP TABLE IF EXISTS " + RealEstates.RealEstate.TABLE_NAME;
    private String[] realEstateAllColumn = {
            RealEstates.RealEstate.COLUMN_ID,
            RealEstates.RealEstate.COLUMN_REAL_ESTATE_TYPE,
            RealEstates.RealEstate.COLUMN_COST,
            RealEstates.RealEstate.COLUMN_DOWN_PAYMENT,
            RealEstates.RealEstate.COLUMN_CASH_FLOW,
            RealEstates.RealEstate.COLUMN_NUM_OF_UNITS,
    };

    private static final String SQL_CREATE_BUSINESS =
            "CREATE TABLE " + Businesses.Business.TABLE_NAME + "(" +
                    Businesses.Business.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Businesses.Business.COLUMN_BUSINESS_TYPE + " TEXT," +
                    Businesses.Business.COLUMN_COST + " INTEGER," +
                    Businesses.Business.COLUMN_DOWN_PAYMENT + " INTEGER," +
                    Businesses.Business.COLUMN_CASH_FLOW + " INTEGER)";
    private static final String SQL_DELETE_BUSINESS =
            "DROP TABLE IF EXISTS " + Businesses.Business.TABLE_NAME;
    private String[] businessAllColumn = {
            Businesses.Business.COLUMN_ID,
            Businesses.Business.COLUMN_BUSINESS_TYPE,
            Businesses.Business.COLUMN_COST,
            Businesses.Business.COLUMN_DOWN_PAYMENT,
            Businesses.Business.COLUMN_CASH_FLOW,
    };

    private static final String SQL_CREATE_GOLD =
            "CREATE TABLE " + Golds.Gold.TABLE_NAME + "(" +
                    Golds.Gold.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Golds.Gold.COLUMN_GOLD_TYPE + " TEXT)";
    private static final String SQL_DELETE_GOLD =
            "DROP TABLE IF EXISTS " + Golds.Gold.TABLE_NAME;
    private String[] goldAllColumn = {
            Golds.Gold.COLUMN_ID,
            Golds.Gold.COLUMN_GOLD_TYPE,
    };

    private static final String SQL_CREATE_LIABILITIES =
            "CREATE TABLE " + Liabilities.Liability.TABLE_NAME + "(" +
                    Liabilities.Liability.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Liabilities.Liability.COLUMN_LOAN_TYPE + " TEXT," +
                    Liabilities.Liability.COLUMN_LOAN_AMOUNT + " INTEGER)";
    private static final String SQL_DELETE_LIABILITIES =
            "DROP TABLE IF EXISTS " + Liabilities.Liability.TABLE_NAME;
    private String[] liabilitiesAllColumn = {
            Liabilities.Liability.COLUMN_ID,
            Liabilities.Liability.COLUMN_LOAN_TYPE,
            Liabilities.Liability.COLUMN_LOAN_AMOUNT,
    };

    public DatabaseHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PLAYER_INFO);
        db.execSQL(SQL_CREATE_EXPENSES);
        db.execSQL(SQL_CREATE_STOCK_MF_COD);
        db.execSQL(SQL_CREATE_REAL_ESTATE);
        db.execSQL(SQL_CREATE_BUSINESS);
        db.execSQL(SQL_CREATE_GOLD);
        db.execSQL(SQL_CREATE_LIABILITIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PLAYER_INFO);
        db.execSQL(SQL_DELETE_EXPENSES);
        db.execSQL(SQL_DELETE_STOCK_MF_COD);
        db.execSQL(SQL_DELETE_REAL_ESTATE);
        db.execSQL(SQL_DELETE_BUSINESS);
        db.execSQL(SQL_DELETE_GOLD);
        db.execSQL(SQL_DELETE_LIABILITIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    //    table player_info
    public void  insertPlayerInfo(PlayerInfoRecord playerInfoRecord) {
        ContentValues values = new ContentValues();
        values.put(PlayerInfo.Player.COLUMN_PROFESSION, playerInfoRecord.getProfession());
        values.put(PlayerInfo.Player.COLUMN_DREAM, playerInfoRecord.getDream());
        values.put(PlayerInfo.Player.COLUMN_AUDIT_NAME, playerInfoRecord.getAuditName());
        values.put(PlayerInfo.Player.COLUMN_SALARY, playerInfoRecord.getSalary());
        values.put(PlayerInfo.Player.COLUMN_CASH_ON_HAND, playerInfoRecord.getCashOnHand());

        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(PlayerInfo.Player.TABLE_NAME, null, values);

        database.close();
    }

    public boolean updatePlayerInfo(PlayerInfoRecord playerInfoRecord) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PlayerInfo.Player.COLUMN_PROFESSION, playerInfoRecord.getProfession());
        values.put(PlayerInfo.Player.COLUMN_DREAM, playerInfoRecord.getDream());
        values.put(PlayerInfo.Player.COLUMN_AUDIT_NAME, playerInfoRecord.getAuditName());
        values.put(PlayerInfo.Player.COLUMN_SALARY, playerInfoRecord.getProfession());
        values.put(PlayerInfo.Player.COLUMN_CASH_ON_HAND, playerInfoRecord.getCashOnHand());
        db.update(PlayerInfo.Player.TABLE_NAME, values, "profession = ?", new String[] { playerInfoRecord.getProfession() });
        return true;
    }

    public Integer deletePlayerInfo() {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer rowDeleted = db.delete(PlayerInfo.Player.TABLE_NAME, "", new String[] { });
        return rowDeleted;
    }

    public PlayerInfoRecord getPlayerInfo() {
        PlayerInfoRecord record = new PlayerInfoRecord();
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(PlayerInfo.Player.TABLE_NAME, allColumn, null,
                null, null, null, null);

        cursor.moveToFirst();

        System.out.println("get profession -> "+cursor.getString(0));
        record.setProfession(cursor.getString(0));
        record.setDream(cursor.getString(1));
        record.setAuditName(cursor.getString(2));
        record.setSalary(cursor.getInt(3));
        record.setCashOnHand(cursor.getInt(4));

        return record;
    }

//    table expenses
    public void  insertExpenses(ExpensesRecord expensesRecord) {
        ContentValues values = new ContentValues();
        values.put(Expenses.Expense.COLUMN_EXPENSES_TYPE, expensesRecord.getExpensesType());
        values.put(Expenses.Expense.COLUMN_EXPENSES_AMOUNT, expensesRecord.getExpensesAmount());

        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(Expenses.Expense.TABLE_NAME, null, values);

        database.close();
    }

    public boolean updateExpenses(ExpensesRecord expensesRecord) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Expenses.Expense.COLUMN_EXPENSES_TYPE, expensesRecord.getExpensesType());
        values.put(Expenses.Expense.COLUMN_EXPENSES_AMOUNT, expensesRecord.getExpensesAmount());
        db.update(Expenses.Expense.TABLE_NAME, values, "expenses_type = ?", new String[] { expensesRecord.getExpensesType() });
        return true;
    }

    public Integer deleteExpenses(String expensesType) {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer rowDeleted = db.delete(Expenses.Expense.TABLE_NAME, "expenses_type = ?", new String[] { expensesType });
        return rowDeleted;
    }

    public Integer deleteAllExpenses() {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer rowDeleted = db.delete(Expenses.Expense.TABLE_NAME, "", new String[] { });
        return rowDeleted;
    }

    public int getSumOfExpenses() {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(Expenses.Expense.TABLE_NAME, expensesAllColumn, null,
                null, null, null, null);

        cursor.moveToFirst();

        int totalExpenses = 0;

        while(!cursor.isAfterLast()) {
            totalExpenses += cursor.getInt(2);
            cursor.moveToNext();
        }

        return totalExpenses;
    }



//    public List<UserRecord> getAllUsers() {
//        List<UserRecord> records = new ArrayList<>();
//        SQLiteDatabase database = this.getReadableDatabase();
//
//        Cursor cursor = database.query(UserContract.User.TABLE_NAME, allColumn, null,
//                null, null, null, null);
//
//        cursor.moveToFirst();
//
//        while(!cursor.isAfterLast()) {
//            UserRecord userRecord = new UserRecord();
//            userRecord.setPhone(cursor.getString(0));
//            userRecord.setName(cursor.getString(1));
//            userRecord.setEmail(cursor.getString(2));
//            records.add(userRecord);
//            cursor.moveToNext();
//        }
//
//        return records;
//    }

}