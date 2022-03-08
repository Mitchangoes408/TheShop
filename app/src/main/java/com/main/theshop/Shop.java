package com.main.theshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import database.CutsCursorWrapper;
import database.CutsDbSchema;

public class Shop {
    private static Shop sShop;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private Shop(Context context) {
        mContext = context.getApplicationContext();

        mDatabase = new DataBaseHelper(mContext).getWritableDatabase();
    }

    public static Shop get(Context context) {
        if(sShop == null)
            sShop = new Shop(context);
        return sShop;
    }

    public void addCut(Cuts cut) {
        ContentValues values = getContentValues(cut);
        mDatabase.insert(CutsDbSchema.CutsTable.NAME, null, values);
    }

    public void updateCut(Cuts cut) {
        String uuidString = cut.getmId().toString();
        ContentValues values = getContentValues(cut);

        /****
            BEFORE LINE WAS USED BUT MIGHT BE THROWING ERRORS
         */
        mDatabase.update(
                CutsDbSchema.CutsTable.NAME,
                values,
                CutsDbSchema.CutsTable.Cols.UUID + " = ?", new String[] { uuidString }
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
            {
                cursor.close();
            }
        }

        return cuts;
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

    private CutsCursorWrapper queryCuts(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
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

}
