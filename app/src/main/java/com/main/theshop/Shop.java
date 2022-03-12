package com.main.theshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import database.ApptCursorWrapper;
import database.ApptDbSchema;
import database.CutsCursorWrapper;
import database.CutsDbSchema;

public class Shop {
    private static Shop sShop;
    private Context mContext;
    private SQLiteDatabase mCutsDatabase;
    private SQLiteDatabase mApptDatabase;

    private Shop(Context context) {
        mContext = context.getApplicationContext();
        mApptDatabase = new ApptBaseHelper(mContext).getWritableDatabase();
        mCutsDatabase = new CutBaseHelper(mContext).getWritableDatabase();

    }

    public static Shop get(Context context) {
        if(sShop == null)
            sShop = new Shop(context);
        return sShop;
    }

    public void addCut(Cuts cut) {
        ContentValues values = getContentValues(cut);
        mCutsDatabase.insert(CutsDbSchema.CutsTable.NAME, null, values);
    }

    public void addAppt(Appointments appointment) {
        ContentValues values = getContentValues(appointment);
        mApptDatabase.insert(ApptDbSchema.ApptTable.NAME, null, values);
    }

    public void updateCut(Cuts cut) {
        String uuidString = cut.getmId().toString();
        ContentValues values = getContentValues(cut);

        /****
            BEFORE LINE WAS USED BUT MIGHT BE THROWING ERRORS
         */
        mCutsDatabase.update(
                CutsDbSchema.CutsTable.NAME,
                values,
                CutsDbSchema.CutsTable.Cols.UUID + " = ?", new String[] { uuidString }
        );
    }

    public void updateAppt(Appointments appointment) {
        String uuidString = appointment.getApptUUID().toString();
        ContentValues values = getContentValues(appointment);

        mApptDatabase.update(
                ApptDbSchema.ApptTable.NAME,
                values,
                ApptDbSchema.ApptTable.Cols.UUID + " = ?", new String[] { uuidString }
        );
    }

    public List<Cuts> getCuts() {
        List<Cuts> cuts = new ArrayList<>();

        CutsCursorWrapper cursor = queryCuts(null, null);

        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                cuts.add(cursor.getCut());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return cuts;
    }

    public List<Appointments> getAppts() {
        List<Appointments> appointments = new ArrayList<>();

        ApptCursorWrapper cursor = queryAppts(null, null);

        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                appointments.add(cursor.getAppt());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return appointments;
    }


    public Cuts getCut(UUID id) {
        CutsCursorWrapper cursor = queryCuts(CutsDbSchema.CutsTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
                );

        try {
            if(cursor.getCount() == 0)
                return null;

            cursor.moveToFirst();
            return cursor.getCut();
        }
        finally {
            cursor.close();
        }
    }

    public Appointments getAppt(UUID id) {
        ApptCursorWrapper cursor = queryAppts(ApptDbSchema.ApptTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
                );

        try {
            if(cursor.getCount() == 0)
                return null;

            cursor.moveToFirst();
            return cursor.getAppt();
        }
        finally {
            cursor.close();
        }
    }



    public File getPhotoFile(Cuts cut) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, cut.getPhotoFileName());
    }

    private static ContentValues getContentValues(Cuts cut) {
        ContentValues values = new ContentValues();

        //values.put(CutsDbSchema.CutsTable.Cols.DATE, cut.getmDate().getTime());
        //values.put(CutsDbSchema.CutsTable.Cols.TITLE, cut.getmTitle());
        values.put(CutsDbSchema.CutsTable.Cols.UUID, cut.getmId().toString());

        return values;
    }

    private static ContentValues getContentValues(Appointments appointment) {
        ContentValues values = new ContentValues();

        values.put(ApptDbSchema.ApptTable.Cols.UUID, appointment.getApptUUID().toString());
        return values;
    }

    private CutsCursorWrapper queryCuts(String whereClause, String[] whereArgs) {
        Cursor cursor = mCutsDatabase.query(
                CutsDbSchema.CutsTable.NAME,
                null,        //selects all columns
                whereClause,
                whereArgs,
                null,       //groupBy
                null,        //having
                null        //orderBy
        );

        return new CutsCursorWrapper(cursor);
    }

    private ApptCursorWrapper queryAppts(String whereClause, String[] whereArgs) {
        Cursor cursor = mApptDatabase.query(
                ApptDbSchema.ApptTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new ApptCursorWrapper(cursor);
    }

}
