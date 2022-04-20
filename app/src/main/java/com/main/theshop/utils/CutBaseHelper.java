package com.main.theshop.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

import database.ApptDbSchema;
import database.CutsDbSchema.CutsTable;

public class CutBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    public static final String DATABASE_NAME = "theshop_cut.db";

    public CutBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + CutsTable.NAME +
                "(" + "_id integer PRIMARY KEY AUTOINCREMENT," +
                CutsTable.Cols.UUID + ", " +
                CutsTable.Cols.DETAILS + ", " +
                CutsTable.Cols.DATE + ", " +
                CutsTable.Cols.FAVORITED + ", " +
                CutsTable.Cols.TYPE + ", " +
                CutsTable.Cols.ACCT_ID + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }
}
