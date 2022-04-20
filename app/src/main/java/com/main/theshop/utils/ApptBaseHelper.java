package com.main.theshop.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import database.ApptDbSchema.ApptTable;

public class ApptBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    public static final String DATABASE_NAME = "theshop_appt.db";

    public ApptBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + ApptTable.NAME +
                "(" + "_id integer PRIMARY KEY AUTOINCREMENT," +
                ApptTable.Cols.UUID + ", " +
                ApptTable.Cols.DETAILS + ", " +
                ApptTable.Cols.DATE + "," +
                ApptTable.Cols.TYPE + "," +
                ApptTable.Cols.USER_ID + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //database.execSQL("DROP TABLE IF EXISTS " + "appointments");
        //onCreate(database);
    }

}
