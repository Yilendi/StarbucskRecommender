/**
 * This is the SQLite open helper page necessary
 * for accessing SQL database
 */
package com.yilendad.starbucksrecommender;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String tableName = "DRINKS_TABLE";
    public static final String dbName = "drinks.db";
    public static final String colName = "PRODUCT_NAME";
    public static final String colSize = "SIZE";
    public static final String colCalories = "CALORIES";
    public static final String colMilk = "MILK";
    public static final String colSugar = "SUGAR";
    public static final String colWhip = "WHIP";
    public static final String colTotalFat = "TOTALFAT";
    public static final String colCholesterol = "CHOLESTEROL";
    public static final String colTotalCarbohydrates = "TOTALCARBOHYDRATES";
    public static final String colProtein = "PROTEIN";
    public static final String colCaffiene = "CAFFIENE";

    public static final String sizeTable = "SIZE_TABLE";
    public static final String milkTable = colMilk + "_TABLE";
    public static final String caloriesTable = colCalories + "_TABLE";
    public static final String fatTable = colTotalFat + "_TABLE";
    public static final String carboTable = colTotalCarbohydrates + "_TABLE";
    public static final String proteinTable = colProtein + "_TABLE";

    public static String[] tableArray = {tableName, sizeTable, milkTable, caloriesTable,
            fatTable, carboTable, proteinTable} ;

    public static final String createCol = " (" + DBHelper.colName + " TEXT, " +
            colSize + " TEXT, " + colMilk + " TEXT, " + colWhip + " TEXT, "
            + colCalories + " INT, " + colTotalFat + " DOUBLE," +
            " " + colCholesterol + " INT, " + colTotalCarbohydrates + " INT, " + colSugar + " INT, "
            + colProtein + " DOUBLE, " + colCaffiene + " INT)";

    //constructer
    public DBHelper(Context context) {

        super(context, dbName, null, 1);
    }

    //this is called the first time a database is accessed.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //create first full table
        String createTable = "CREATE TABLE IF NOT EXISTS " + tableName + createCol;
                sqLiteDatabase.execSQL(createTable);
        Log.i("creation", "table created") ;
    }

    //this is called when database version number changes
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(sqLiteDatabase);
    }


    /**
     * delete previous records and create new ones
     */
    public void refreshAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, null, null);
        db.close() ;
    }

    /**
     * drop table to avoid data overwhelming
     *
     */
    public void dropTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 1; i < 7; i++) {
            db.execSQL("DROP TABLE IF EXISTS " + tableArray[i]);
        }
        db.close();
    }

    /**
     * get all drinks from given table
     * @param tableName
     * @return
     */
    public ArrayList<DrinkModel> getAllDrinks(String tableName) {
        ArrayList<DrinkModel> returnList = new ArrayList<>();
        //get data from database
        String query = "SELECT * FROM " + tableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            //loop through the cursor
            do {
                String drinkName = cursor.getString(0);
                String drinkSize = cursor.getString(1);
                String drinkMilk = cursor.getString(2);
                String drinkWhip = cursor.getString(3);
                int drinkCal = cursor.getInt(4);
                int drinkFat = cursor.getInt(5);
                int drinkCho = cursor.getInt(6);
                int drinkCarb = cursor.getInt(7);
                int drinkSugar = cursor.getInt(8);
                int drinkProt = cursor.getInt(9);
                int drinkCaf = cursor.getInt(10);

                DrinkModel drinkModel = new DrinkModel(drinkName, drinkSize, drinkMilk, drinkWhip, drinkCal, drinkFat,
                        drinkCho, drinkCarb, drinkSugar, drinkProt, drinkCaf) ;
                returnList.add(drinkModel) ;
            } while (cursor.moveToNext());
        } else {
            //failure
            Log.e("EmptyTable", "no drink in table");
        }
        cursor.close();
        db.close();
        return returnList;
    }

    /**
     * create a new table to use in next page
     * @param newTableName
     * @param oldTableName
     */
    public void createNewTable(String newTableName, String oldTableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        //drop table if exists
        db.execSQL("DROP TABLE IF EXISTS " + newTableName);
        //create new table
        String createNewTable = "CREATE TABLE " + newTableName + createCol;
        db.execSQL(createNewTable);
        db.close();
    }

    /**
     * populate a given table with values from old table
     * for range numeric values
     * @param tableName
     * @param oldTable
     * @param column
     * @param min
     * @param max
     */
    public void populateTable(String tableName, String oldTable, String column, double min, double max) {
        String test = "INSERT INTO " + tableName + " SELECT *" + " FROM " +
                oldTable + " WHERE " + column + " BETWEEN " + min + " AND " + max;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(test);
        } catch (SQLException e) {
            Log.e("SQLError", e.getMessage().toString());
        }
        db.close();
    }

    /**
     * populates a table from given table name by filtering from a previous table
     * based on numeric range in two selected columns
     * @param tableName
     * @param oldTable
     * @param column
     * @param min
     * @param max
     * @param column2
     * @param min2
     * @param max2
     */
    public void populateTable(String tableName, String oldTable,
                              String column, double min, double max,
                              String column2, double min2, double max2) {
        String test = "INSERT INTO " + tableName + " SELECT *" + " FROM " +
                oldTable + " WHERE (" + column + " BETWEEN " + min + " AND " + max + ") AND (" +
                column2 + " BETWEEN " + min2 + " AND " + max2 + ")";
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(test);
        } catch (SQLException e) {
            Log.e("SQLError", e.getMessage().toString());
        }
        db.close();
    }

    /**
     * populate a given table with selected values in a given column
     * from old table
     * @param tableName
     * @param oldTable
     * @param column
     * @param target1
     * @param target2
     */
    public void populateTable(String tableName, String oldTable, String column, String target1, String target2) {
        String test = "INSERT INTO " + tableName + " SELECT *" + " FROM " +
                oldTable + " WHERE " + column + " IN ('" + target1 + "', '" + target2 + "')";
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(test);
        } catch (SQLException e) {
            Log.e("SQLError", e.getMessage().toString());
        }
        db.close();
    }

    /**
     * select from given old table data that matches given column and given string
     * and insert into given new table
     * @param tableName
     * @param oldTable
     * @param column1
     * @param target1
     * @param column2
     * @param target2
     */
    public void populateTable(String tableName, String oldTable, String column1, String target1, String column2, String target2) {
        String test = "INSERT INTO " + tableName + " SELECT *" + " FROM " +
                oldTable + " WHERE " + column1 + " IN ('" + target1 + "'" + ", 'N/A') AND " + column2 + " IN ('" + target2 +
                "', 'N/A')";
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(test);
        } catch (SQLException e) {
            Log.e("SQLError", e.getMessage().toString());
        }
        db.close();
    }

    /**
     * copy from given old table to given new table
     * @param tableName
     * @param oldTable
     */
    public void copyTable(String tableName, String oldTable) {
        String sql = "INSERT INTO " + tableName + " SELECT *" + " FROM " +
                oldTable;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }
    /**
     * get row count for a table with given target string
     * @param tableName
     * @param column
     * @param target
     * @return
     */
    public int getRowsCount(String tableName, String column, String target) {
        String countQuery = "SELECT  * FROM " + tableName + " WHERE " + column + " IN ('" + target + "'" + ", 'N/A')";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    /**
     * get row count for a table with given ideal range
     * @param tableName
     * @param column
     * @param min
     * @param max
     * @return
     */
    public int getRowsCount(String tableName, String column, double min, double max) {
        String countQuery = "SELECT  * FROM " + tableName + " WHERE " + column + " BETWEEN " + min + " AND " + max;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    /**
     * get rows count for given table (no selection)
     * @param tableName
     * @return
     */
    public int getRowsCount(String tableName) {
        String countQuery = "SELECT  * FROM " + tableName ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    /**
     * get max value of a given column in given table
     * @param column
     * @param table
     * @return
     */
    public double getMaxColumnData(String column, String table) {

        SQLiteDatabase rdb = this.getReadableDatabase();

        final SQLiteStatement stmt = rdb
                .compileStatement("SELECT MAX(" + column + ") FROM " + table);
        String num = (String) stmt.simpleQueryForString();
        double number = Double.parseDouble(num);
        rdb.close();
        return number;
    }

    /**
     * get min value of a given column in given table
     * @param column
     * @param table
     * @return
     */
    public double getMinColumnData(String column, String table) {

        SQLiteDatabase rdb = this.getReadableDatabase();

        final SQLiteStatement stmt = rdb
                .compileStatement("SELECT MIN(" + column + ") FROM " + table);

        String num = (String) stmt.simpleQueryForString();
        double number = Double.parseDouble(num);
        rdb.close();
        return number;
    }
}
