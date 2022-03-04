package com.main.theshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

import database.CutsDbSchema.CutsTable;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    public static final String DATABASE_NAME = "theshop.db";



    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + CutsTable.NAME +
                "(" + "_id integer PRIMARY KEY AUTOINCREMENT," +
                CutsTable.Cols.UUID + ", " +
                CutsTable.Cols.TITLE + ", " +
                CutsTable.Cols.DATE + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        database.execSQL("DROP TABLE IF EXISTS " + "cuts");
        onCreate(database);




    }
    /*
    public boolean insertUser (String username, String password, String fullName, String email, String phoneNumber, Boolean isClient) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("name", fullName);
        contentValues.put("email", email);
        contentValues.put("number", phoneNumber);
        contentValues.put("isClient", isClient);
        database.insert("users", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor res = database.rawQuery( "select * from users where is="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase database = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(database, USERS_TABLE_NAME);
        return numRows;
    }

    public boolean updateUser (Integer id, String username, String password) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        database.update("users", contentValues, "id = ? ", new String[] {Integer.toString(id) } );
        return true;
    }

    public Integer deleteUser(Integer id) {
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllUsers() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor res = database.rawQuery( "select * from users", null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(USERS_COLUMN_USERNAME)));
            res.moveToNext();
        }
        return array_list;
    }

     */


}
