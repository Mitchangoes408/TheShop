package com.main.theshop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import database.LoginDbSchema.LoginTable;

public class UserBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    public static final String DATABASE_NAME = "theshop_credentials.db";

    public UserBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + LoginTable.NAME +
                "(" + "_id integer PRIMARY KEY AUTOINCREMENT," +
                LoginTable.Cols.UUID + ", " +
                LoginTable.Cols.TYPE + ", " +
                LoginTable.Cols.USERNAME + ", " +
                LoginTable.Cols.PASSWORD + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {

    }
}
