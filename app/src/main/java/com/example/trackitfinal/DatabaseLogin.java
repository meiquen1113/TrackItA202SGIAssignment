package com.example.trackitfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseLogin extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "testlogin.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "userdata";
    private static final String COL_id = "ID";
    private static final String COL_1 = "USERNAME";
    private static final String COL_2 = "PASSWORD";
    private static final String COL_3 = "FULLNAME";
    private static final String COL_4 = "EMAIL";

    public DatabaseLogin(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_1 + " TEXT,"
                + COL_2 + " TEXT,"
                + COL_3 + " TEXT,"
                + COL_4 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String username, String password, String fullname, String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, username);
        contentValues.put(COL_2, password);
        contentValues.put(COL_3, fullname);
        contentValues.put(COL_4, email);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public void updatePassword(String username, String password)
    {
        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // value to be updated
        values.put(COL_2, password);
        // update our database and passing our values comparing with username
        db.update(TABLE_NAME, values, "USERNAME=?", new String[]{username});
        db.close();
    }

    public void updateProfile(String username, String fullname, String email)
    {
        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // value to be updated
        values.put(COL_3, fullname);
        values.put(COL_4, email);
        // update our database and passing our values comparing with username
        db.update(TABLE_NAME, values, "USERNAME=?", new String[]{username});
        db.close();
    }

    public boolean checkUser(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_1 + " = " + "'" + username + "'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            db.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }

    public String checkProfile(String username, String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_3};
        String selection = "USERNAME=? AND EMAIL = ?";
        //String selection = "USERNAME=?";
        String[] selectionArgs = {username, email};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        String result = "null";
        if (cursor!=null && cursor.moveToFirst())
        {
            result = cursor.getString(cursor.getColumnIndex(COL_3));
        }
        cursor.close();
        db.close();
        return result;
    }

    public String checkLogin(String username, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_1};
        String selection = "USERNAME=? AND PASSWORD = ?";
        //String selection = "USERNAME=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        String result = "null";
        if (cursor!=null && cursor.moveToFirst())
        {
            result = cursor.getString(cursor.getColumnIndex(COL_1));
        }
        cursor.close();
        db.close();
        return result;
    }

    public String getFullname(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL_3 + " FROM " + TABLE_NAME + " WHERE " + COL_1 + " = " + "'" + username + "'";
        Cursor cursor = db.rawQuery(query, null);
        String result = "null";
        if (cursor!=null && cursor.moveToFirst())
        {
            result = cursor.getString(cursor.getColumnIndex(COL_3));
        }
        cursor.close();
        db.close();
        return result;
    }

    public String getEmail(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL_4 + " FROM " + TABLE_NAME + " WHERE " + COL_1 + " = " + "'" + username + "'";
        Cursor cursor = db.rawQuery(query, null);
        String result = "null";
        if (cursor!=null && cursor.moveToFirst())
        {
            result = cursor.getString(cursor.getColumnIndex(COL_4));
        }
        cursor.close();
        db.close();
        return result;
    }

}

