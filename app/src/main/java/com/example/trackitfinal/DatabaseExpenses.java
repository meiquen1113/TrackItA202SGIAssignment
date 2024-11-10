package com.example.trackitfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseExpenses extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "expenseDB";
    // below int is our database version
    private static final int DB_VERSION = 1;
    // below variable is for our table name.
    private static final String TABLE_NAME = "userexpenses";
    // below variable is for our id column.
    private static final String ID_COL = "id";
    // below variable is for our date column
    private static final String DATE_COL = "date";
    // below variable id for our category column.
    private static final String CATEGORY_COL = "category";
    // below variable for our course description column.
    private static final String DESCRIPTION_COL = "description";
    // below variable is for our amount column.
    private static final String AMOUNT_COL = "amount";
    // below variable is for our logUser column.
    private static final String LOGUSER_COL = "loguser";

    // creating a constructor for our database handler.
    public DatabaseExpenses(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating an sqlite query and
        // setting our column names along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE_COL + " TEXT,"
                + CATEGORY_COL + " TEXT,"
                + DESCRIPTION_COL + " TEXT,"
                + AMOUNT_COL + " TEXT,"
                + LOGUSER_COL + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new expense record to our sqlite database.
    public void addNewExpense(String expenseDate, String expenseCategory, String expenseDescription, String expenseAmount, String expenseLoguser) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();
        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();
        // on below line we are passing all values
        // along with its key and value pair.
        values.put(DATE_COL, expenseDate);
        values.put(CATEGORY_COL, expenseCategory);
        values.put(DESCRIPTION_COL, expenseDescription);
        values.put(AMOUNT_COL, expenseAmount);
        values.put(LOGUSER_COL, expenseLoguser);
        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // we have created a new method for reading all the expenses.
    public ArrayList<ExpenseModal> readExpenses(String logUser) {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();
        // on below line we are creating a cursor with query to read data from database.
        // Cursor cursorExpenses = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY id DESC", null);
        Cursor cursorExpenses = db.rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE " + LOGUSER_COL + " = " + "'"+logUser+"'"+ " ORDER BY id DESC", null);
        // on below line we are creating a new array list.
        ArrayList<ExpenseModal> courseModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorExpenses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                courseModalArrayList.add(new ExpenseModal(cursorExpenses.getString(1),
                        cursorExpenses.getString(2),
                        cursorExpenses.getString(3),
                        cursorExpenses.getString(4),
                        cursorExpenses.getString(5),
                        cursorExpenses.getString(0)));
            } while (cursorExpenses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorExpenses.close();
        db.close();
        return courseModalArrayList;
    }

    // below is the method for updating our expenses record.
    public void updateExpense(String oID, String expenseDate, String expenseCategory,
                              String expenseDescription, String expenseAmount) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(DATE_COL, expenseDate);
        values.put(CATEGORY_COL, expenseCategory);
        values.put(DESCRIPTION_COL, expenseDescription);
        values.put(AMOUNT_COL, expenseAmount);

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with id.
        db.update(TABLE_NAME, values, "id=?", new String[]{oID});
        db.close();
    }

    // below is the method for deleting expense record in sql.
    public void deleteExpense(String oID) {

        // on below line we are creating a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();
        // on below line to delete our expenses by comparing it with id name.
        db.delete(TABLE_NAME, "id=?", new String[]{oID});
        db.close();
    }

    // get the total sum of income from the sql database by login username.
    public float sumOfIncome(String logUsername) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + AMOUNT_COL + ") as Total FROM " + TABLE_NAME + " WHERE " + CATEGORY_COL + " = " + "'Income'"
                + "  AND "+ LOGUSER_COL + " = " + "'" + logUsername + "'", null);
        float result = 0;
        if (cursor.moveToFirst()) {
            result = cursor.getFloat(cursor.getColumnIndex("Total"));
            cursor.close();
            db.close();
            return result;
        } else {
            return result;
        }
    }

    // get the total sum of expenses from the sql database by login username.
    public float sumOfExpenses(String logUsername) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + AMOUNT_COL + ") as Total FROM " + TABLE_NAME + " WHERE " + CATEGORY_COL + " != " + "'Income'"
                + "  AND "+ LOGUSER_COL + " = " + "'" + logUsername + "'", null);
        float result = 0;
        if (cursor.moveToFirst()) {
            result = cursor.getFloat(cursor.getColumnIndex("Total"));
            cursor.close();
            db.close();
            return result;
        } else {
            return result;
        }
    }

    // get the total sum of selected category input string from the sql database by login username.
    public float sumOfCategory(String category, String logUsername) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + AMOUNT_COL + ") as Total FROM " + TABLE_NAME + " WHERE " + CATEGORY_COL + " = " + "'"+category+"'"
                + "  AND "+ LOGUSER_COL + " = " + "'" + logUsername + "'", null);
        float result = 0;
        if (cursor.moveToFirst()) {
            result = cursor.getFloat(cursor.getColumnIndex("Total"));
            cursor.close();
            db.close();
            return result;
        } else {
            return result;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}