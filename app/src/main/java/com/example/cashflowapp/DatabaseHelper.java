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
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
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
                    Golds.Gold.COLUMN_GOLD_TYPE + " TEXT," +
                    Golds.Gold.COLUMN_GOLD_AMOUNT + " INTEGER)";
    private static final String SQL_DELETE_GOLD =
            "DROP TABLE IF EXISTS " + Golds.Gold.TABLE_NAME;
    private String[] goldAllColumn = {
            Golds.Gold.COLUMN_ID,
            Golds.Gold.COLUMN_GOLD_TYPE,
            Golds.Gold.COLUMN_GOLD_AMOUNT,
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
        values.put(PlayerInfo.Player.COLUMN_SALARY, playerInfoRecord.getSalary());
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

    public boolean getBankLoanExistence() {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(Expenses.Expense.TABLE_NAME, expensesAllColumn, null,
                null, null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            String expensesType = cursor.getString(1);
            if(expensesType.equals("Bank Loan")) {
                return true;
            }
            cursor.moveToNext();
        }
        return false;
    }

    public ExpensesRecord getBankLoanExpenses() {
        SQLiteDatabase database = this.getReadableDatabase();
        ExpensesRecord record = new ExpensesRecord();

        Cursor cursor = database.query(Expenses.Expense.TABLE_NAME, expensesAllColumn, "expenses_type = ?",
                new String[] { "Bank Loan" }, null, null, null);

        cursor.moveToFirst();

        record.setId(cursor.getInt(0));
        record.setExpensesType(cursor.getString(1));
        record.setExpensesAmount(cursor.getInt(2));
        return record;
    }

    public ExpensesRecord getExpensesByExpensesType(String expensesTye) {
        SQLiteDatabase database = this.getReadableDatabase();
        ExpensesRecord record = new ExpensesRecord();

        Cursor cursor = database.query(Expenses.Expense.TABLE_NAME, expensesAllColumn, "expenses_type = ?",
                new String[] { expensesTye }, null, null, null);

        cursor.moveToFirst();

        record.setId(cursor.getInt(0));
        record.setExpensesType(cursor.getString(1));
        record.setExpensesAmount(cursor.getInt(2));
        return record;
    }

//    table stock_mutual_fund_cod
    public void  insertStockMutualFundCOD(StockMutualFundCODRecord stockMutualFundCODRecord) {
        ContentValues values = new ContentValues();
        values.put(StockMutualFundCOD.StockMFundCOD.COLUMN_STOCK_TYPE, stockMutualFundCODRecord.getStockType());
        values.put(StockMutualFundCOD.StockMFundCOD.COLUMN_BUYING_PRICE, stockMutualFundCODRecord.getBuyingPrice());
        values.put(StockMutualFundCOD.StockMFundCOD.COLUMN_NUM_SHARES, stockMutualFundCODRecord.getNumOfShares());
        values.put(StockMutualFundCOD.StockMFundCOD.COLUMN_DIVIDENDS, stockMutualFundCODRecord.getMonthlyDividends());
        values.put(StockMutualFundCOD.StockMFundCOD.COLUMN_INTEREST, stockMutualFundCODRecord.getMonthlyInterest());

        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(StockMutualFundCOD.StockMFundCOD.TABLE_NAME, null, values);

        database.close();
    }

    public boolean updateStockMutualFundCOD(StockMutualFundCODRecord stockMutualFundCODRecord) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StockMutualFundCOD.StockMFundCOD.COLUMN_STOCK_TYPE, stockMutualFundCODRecord.getStockType());
        values.put(StockMutualFundCOD.StockMFundCOD.COLUMN_BUYING_PRICE, stockMutualFundCODRecord.getBuyingPrice());
        values.put(StockMutualFundCOD.StockMFundCOD.COLUMN_NUM_SHARES, stockMutualFundCODRecord.getNumOfShares());
        values.put(StockMutualFundCOD.StockMFundCOD.COLUMN_DIVIDENDS, stockMutualFundCODRecord.getMonthlyDividends());
        values.put(StockMutualFundCOD.StockMFundCOD.COLUMN_INTEREST, stockMutualFundCODRecord.getMonthlyInterest());
        db.update(StockMutualFundCOD.StockMFundCOD.TABLE_NAME, values, "id = ?", new String[] { String.valueOf(stockMutualFundCODRecord.getId()) });
        return true;
    }

    public Integer deleteStockMutualFundCOD(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer rowDeleted = db.delete(StockMutualFundCOD.StockMFundCOD.TABLE_NAME, "id = ?", new String[] { String.valueOf(id) });
        return rowDeleted;
    }

    public Integer deleteAllStockMutualFundCOD() {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer rowDeleted = db.delete(StockMutualFundCOD.StockMFundCOD.TABLE_NAME, "", new String[] { });
        return rowDeleted;
    }

    public List<StockMutualFundCODRecord> getAllPurchasedStocks() {
        List<StockMutualFundCODRecord> records = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(StockMutualFundCOD.StockMFundCOD.TABLE_NAME, stockMFundCodAllColumn, null,
                null, null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            StockMutualFundCODRecord stockMutualFundCODRecord = new StockMutualFundCODRecord();
            stockMutualFundCODRecord.setId(cursor.getInt(0));
            stockMutualFundCODRecord.setStockType(cursor.getString(1));
            stockMutualFundCODRecord.setBuyingPrice(cursor.getInt(2));
            stockMutualFundCODRecord.setNumOfShares(cursor.getInt(3));
            stockMutualFundCODRecord.setMonthlyDividends(cursor.getInt(4));
            stockMutualFundCODRecord.setMonthlyInterest(cursor.getInt(5));
            records.add(stockMutualFundCODRecord);
            cursor.moveToNext();
        }

        return records;
    }

    public List<StockMutualFundCODRecord> getPurchasedStocksByType(String stockType) {
        List<StockMutualFundCODRecord> records = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(StockMutualFundCOD.StockMFundCOD.TABLE_NAME, stockMFundCodAllColumn, "stock_type = ?",
                new String[] { stockType }, null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            StockMutualFundCODRecord stockMutualFundCODRecord = new StockMutualFundCODRecord();
            stockMutualFundCODRecord.setId(cursor.getInt(0));
            stockMutualFundCODRecord.setStockType(cursor.getString(1));
            stockMutualFundCODRecord.setBuyingPrice(cursor.getInt(2));
            stockMutualFundCODRecord.setNumOfShares(cursor.getInt(3));
            stockMutualFundCODRecord.setMonthlyDividends(cursor.getInt(4));
            stockMutualFundCODRecord.setMonthlyInterest(cursor.getInt(5));
            records.add(stockMutualFundCODRecord);
            cursor.moveToNext();
        }

        return records;
    }

    //    table real_estate
    public void  insertRealEstate(RealEstatesRecord realEstatesRecord) {
        ContentValues values = new ContentValues();
        values.put(RealEstates.RealEstate.COLUMN_REAL_ESTATE_TYPE, realEstatesRecord.getRealEstateType());
        values.put(RealEstates.RealEstate.COLUMN_COST, realEstatesRecord.getCost());
        values.put(RealEstates.RealEstate.COLUMN_DOWN_PAYMENT, realEstatesRecord.getDownPayment());
        values.put(RealEstates.RealEstate.COLUMN_CASH_FLOW, realEstatesRecord.getCashFlow());
        values.put(RealEstates.RealEstate.COLUMN_NUM_OF_UNITS, realEstatesRecord.getNumberOfUnits());

        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(RealEstates.RealEstate.TABLE_NAME, null, values);

        database.close();
    }

    public boolean updateRealEstate(RealEstatesRecord realEstatesRecord) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RealEstates.RealEstate.COLUMN_REAL_ESTATE_TYPE, realEstatesRecord.getRealEstateType());
        values.put(RealEstates.RealEstate.COLUMN_COST, realEstatesRecord.getCost());
        values.put(RealEstates.RealEstate.COLUMN_DOWN_PAYMENT, realEstatesRecord.getDownPayment());
        values.put(RealEstates.RealEstate.COLUMN_CASH_FLOW, realEstatesRecord.getCashFlow());
        values.put(RealEstates.RealEstate.COLUMN_NUM_OF_UNITS, realEstatesRecord.getNumberOfUnits());
        db.update(RealEstates.RealEstate.TABLE_NAME, values, "id = ?", new String[] { String.valueOf(realEstatesRecord.getId()) });
        return true;
    }

    public Integer deleteRealEstate(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer rowDeleted = db.delete(RealEstates.RealEstate.TABLE_NAME, "id = ?", new String[] { String.valueOf(id) });
        return rowDeleted;
    }

    public Integer deleteAllRealEstates() {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer rowDeleted = db.delete(RealEstates.RealEstate.TABLE_NAME, "", new String[] { });
        return rowDeleted;
    }

    public List<RealEstatesRecord> getAllPurchasedRealEstates() {
        List<RealEstatesRecord> records = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(RealEstates.RealEstate.TABLE_NAME, realEstateAllColumn, null,
                null, null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            RealEstatesRecord realEstatesRecord = new RealEstatesRecord();
            realEstatesRecord.setId(cursor.getInt(0));
            realEstatesRecord.setRealEstateType(cursor.getString(1));
            realEstatesRecord.setCost(cursor.getInt(2));
            realEstatesRecord.setDownPayment(cursor.getInt(3));
            realEstatesRecord.setCashFlow(cursor.getInt(4));
            realEstatesRecord.setNumberOfUnits(cursor.getInt(5));
            records.add(realEstatesRecord);
            cursor.moveToNext();
        }

        return records;
    }

    //    table business
    public void  insertBusiness(BusinessRecord businessRecord) {
        ContentValues values = new ContentValues();
        values.put(Businesses.Business.COLUMN_BUSINESS_TYPE, businessRecord.getBusinessType());
        values.put(Businesses.Business.COLUMN_COST, businessRecord.getCost());
        values.put(Businesses.Business.COLUMN_DOWN_PAYMENT, businessRecord.getDownPayment());
        values.put(Businesses.Business.COLUMN_CASH_FLOW, businessRecord.getCashFlow());

        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(Businesses.Business.TABLE_NAME, null, values);

        database.close();
    }

    public boolean updateBusiness(BusinessRecord businessRecord) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Businesses.Business.COLUMN_BUSINESS_TYPE, businessRecord.getBusinessType());
        values.put(Businesses.Business.COLUMN_COST, businessRecord.getCost());
        values.put(Businesses.Business.COLUMN_DOWN_PAYMENT, businessRecord.getDownPayment());
        values.put(Businesses.Business.COLUMN_CASH_FLOW, businessRecord.getCashFlow());
        db.update(Businesses.Business.TABLE_NAME, values, "id = ?", new String[] { String.valueOf(businessRecord.getId()) });

        db.close();

        return true;
    }

    public Integer deleteBusiness(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer rowDeleted = db.delete(Businesses.Business.TABLE_NAME, "id = ?", new String[] { String.valueOf(id) });
        return rowDeleted;
    }

    public Integer deleteAllBusinesses() {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer rowDeleted = db.delete(Businesses.Business.TABLE_NAME, "", new String[] { });
        return rowDeleted;
    }

    public List<BusinessRecord> getAllPurchasedBusinesses() {
        List<BusinessRecord> records = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(Businesses.Business.TABLE_NAME, businessAllColumn, null,
                null, null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            BusinessRecord businessRecord = new BusinessRecord();
            businessRecord.setId(cursor.getInt(0));
            businessRecord.setBusinessType(cursor.getString(1));
            businessRecord.setCost(cursor.getInt(2));
            businessRecord.setDownPayment(cursor.getInt(3));
            businessRecord.setCashFlow(cursor.getInt(4));
            records.add(businessRecord);
            cursor.moveToNext();
        }

        return records;
    }

    //    table gold
    public void  insertGold(GoldRecord goldRecord) {
        ContentValues values = new ContentValues();
        values.put(Golds.Gold.COLUMN_GOLD_TYPE, goldRecord.getGoldType());
        values.put(Golds.Gold.COLUMN_GOLD_AMOUNT, goldRecord.getGoldAmount());

        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(Golds.Gold.TABLE_NAME, null, values);

        database.close();
    }

    public boolean updateGold(GoldRecord goldRecord) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Golds.Gold.COLUMN_GOLD_TYPE, goldRecord.getGoldType());
        values.put(Golds.Gold.COLUMN_GOLD_AMOUNT, goldRecord.getGoldAmount());
        db.update(Golds.Gold.TABLE_NAME, values, "id = ?", new String[] { String.valueOf(goldRecord.getId()) });

        db.close();

        return true;
    }

    public Integer deleteGold(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer rowDeleted = db.delete(Golds.Gold.TABLE_NAME, "id = ?", new String[] { String.valueOf(id) });
        return rowDeleted;
    }

    public Integer deleteAllGolds() {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer rowDeleted = db.delete(Golds.Gold.TABLE_NAME, "", new String[] { });
        return rowDeleted;
    }

    public List<GoldRecord> getAllPurchasedGolds() {
        List<GoldRecord> records = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(Golds.Gold.TABLE_NAME, goldAllColumn, null,
                null, null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            GoldRecord goldRecord = new GoldRecord();
            goldRecord.setId(cursor.getInt(0));
            goldRecord.setGoldType(cursor.getString(1));
            goldRecord.setGoldAmount(cursor.getInt(2));
            records.add(goldRecord);
            cursor.moveToNext();
        }

        return records;
    }

    //    table liability
    public void  insertLiability(LiabilityRecord liabilityRecord) {
        ContentValues values = new ContentValues();
        values.put(Liabilities.Liability.COLUMN_LOAN_TYPE, liabilityRecord.getLoanType());
        values.put(Liabilities.Liability.COLUMN_LOAN_AMOUNT, liabilityRecord.getLoanAmount());

        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(Liabilities.Liability.TABLE_NAME, null, values);

        database.close();
    }

    public boolean updateLiability(LiabilityRecord liabilityRecord) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Liabilities.Liability.COLUMN_LOAN_TYPE, liabilityRecord.getLoanType());
        values.put(Liabilities.Liability.COLUMN_LOAN_AMOUNT, liabilityRecord.getLoanAmount());
        db.update(Liabilities.Liability.TABLE_NAME, values, "id = ?", new String[] { String.valueOf(liabilityRecord.getId()) });

        db.close();

        return true;
    }

    public Integer deleteLiability(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer rowDeleted = db.delete(Liabilities.Liability.TABLE_NAME, "id = ?", new String[] { String.valueOf(id) });
        return rowDeleted;
    }

    public Integer deleteAllLiabilities() {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer rowDeleted = db.delete(Liabilities.Liability.TABLE_NAME, "", new String[] { });
        return rowDeleted;
    }

    public boolean getLiabilityBankLoanExistence() {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(Liabilities.Liability.TABLE_NAME, liabilitiesAllColumn, null,
                null, null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            String loanType = cursor.getString(1);
            if(loanType.equals("Bank Loan")) {
                return true;
            }
            cursor.moveToNext();
        }
        return false;
    }

    public LiabilityRecord getLiabilityBankLoan() {
        SQLiteDatabase database = this.getReadableDatabase();
        LiabilityRecord record = new LiabilityRecord();

        Cursor cursor = database.query(Liabilities.Liability.TABLE_NAME, liabilitiesAllColumn, "loan_type = ?",
                new String[] { "Bank Loan" }, null, null, null);

        cursor.moveToFirst();

        record.setId(cursor.getInt(0));
        record.setLoanType(cursor.getString(1));
        record.setLoanAmount(cursor.getInt(2));
        return record;
    }

    private static boolean containElement(String[] loanArr, String toCheckValue) {
        boolean hasElement;
        hasElement = Arrays.asList(loanArr).contains(toCheckValue);
        return hasElement;
    }

    public List<LiabilityRecord> getAllLoansToPay() {
        List<LiabilityRecord> records = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(Liabilities.Liability.TABLE_NAME, liabilitiesAllColumn, null,
                null, null, null, null);

        String[] loanCanBePaid = {"Car Loan", "Credit Card", "Mortgage", "Retail Debt", "School Loan", "Bank Loan"};

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            LiabilityRecord liabilityRecord = new LiabilityRecord();
            if (containElement(loanCanBePaid, cursor.getString(1)) && cursor.getInt(2) > 0) {
                liabilityRecord.setId(cursor.getInt(0));
                liabilityRecord.setLoanType(cursor.getString(1));
                liabilityRecord.setLoanAmount(cursor.getInt(2));
                records.add(liabilityRecord);
            }
            cursor.moveToNext();
        }

        return records;
    }

    public int getCashFlow() {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor stock2BIGCursor = database.query(StockMutualFundCOD.StockMFundCOD.TABLE_NAME, stockMFundCodAllColumn, "stock_type = ?",
                new String[] { "2BIG" }, null, null, null);
        Cursor stockCoDCursor = database.query(StockMutualFundCOD.StockMFundCOD.TABLE_NAME, stockMFundCodAllColumn, "stock_type = ?",
                new String[] { "Certificate of Deposit" }, null, null, null);
        Cursor realEstateCursor = database.query(RealEstates.RealEstate.TABLE_NAME, realEstateAllColumn, null,
                null, null, null, null);
        Cursor businessCursor = database.query(Businesses.Business.TABLE_NAME, businessAllColumn, null,
                null, null, null, null);

        int totalCashFlow = 0;

        stock2BIGCursor.moveToFirst();

        while(!stock2BIGCursor.isAfterLast()) {
            totalCashFlow += stock2BIGCursor.getInt(4);
            stock2BIGCursor.moveToNext();
        }

        stockCoDCursor.moveToFirst();

        while(!stockCoDCursor.isAfterLast()) {
            totalCashFlow += stockCoDCursor.getInt(5);
            stockCoDCursor.moveToNext();
        }

        realEstateCursor.moveToFirst();

        while(!realEstateCursor.isAfterLast()) {
            totalCashFlow += realEstateCursor.getInt(4);
            realEstateCursor.moveToNext();
        }

        businessCursor.moveToFirst();

        while(!businessCursor.isAfterLast()) {
            totalCashFlow += businessCursor.getInt(4);
            businessCursor.moveToNext();
        }

        return totalCashFlow;
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