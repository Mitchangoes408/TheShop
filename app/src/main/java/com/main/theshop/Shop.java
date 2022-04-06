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
import database.LoginCursorWrapper;
import database.LoginDbSchema;

public class Shop {
    private static Shop sShop;
    private Context mContext;

    private static UUID currUserId;

    private SQLiteDatabase mCutsDatabase;
    private SQLiteDatabase mApptDatabase;
    private SQLiteDatabase mUserDatabase;

    private Shop(Context context) {
        mContext = context.getApplicationContext();
        mApptDatabase = new ApptBaseHelper(mContext).getWritableDatabase();
        mCutsDatabase = new CutBaseHelper(mContext).getWritableDatabase();
        mUserDatabase = new UserBaseHelper(mContext).getWritableDatabase();
    }

    public static Shop get(Context context) {
        if(sShop == null)
            sShop = new Shop(context);
        return sShop;
    }

    public void addCut(Cuts cut) {
        ContentValues values = getContentValues(cut);
        mCutsDatabase.insert(
                CutsDbSchema.CutsTable.NAME,
                null,
                values);
    }

    public void addAppt(Appointments appointment) {
        ContentValues values = getContentValues(appointment);
        mApptDatabase.insert(
                ApptDbSchema.ApptTable.NAME,
                null,
                values);
    }

    public void addUser(User user) {
        ContentValues values = getContentValues(user);
        mUserDatabase.insert(
                LoginDbSchema.LoginTable.NAME,
                null,
                values);
    }

    public void deleteCut(Cuts cut) {
        UUID cutId = cut.getmId();
        mCutsDatabase.delete(
                CutsDbSchema.CutsTable.NAME,
                CutsDbSchema.CutsTable.Cols.UUID + " = ?",
                new String[] { cutId.toString() }
        );

        /** SHOULD DELETE FROM LIST<CUTS> AS WELL; **/
    }

    public void deleteAppt(UUID apptId) {
        mApptDatabase.delete(
                ApptDbSchema.ApptTable.NAME,
                ApptDbSchema.ApptTable.Cols.UUID + " = ?",
                new String[] { apptId.toString() }
        );

        /** SHOULD DELETE FROM LIST<APPTS> AS WELL; **/
    }

    public void deleteUser(UUID userId) {
        mUserDatabase.delete(
                LoginDbSchema.LoginTable.NAME,
                LoginDbSchema.LoginTable.Cols.UUID + " = ? ",
                new String[] { userId.toString() }
        );
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
                CutsDbSchema.CutsTable.Cols.UUID + " = ?",
                new String[] { uuidString }
        );
    }

    public void updateAppt(Appointments appointment) {
        String uuidString = appointment.getApptUUID().toString();
        ContentValues values = getContentValues(appointment);

        mApptDatabase.update(
                ApptDbSchema.ApptTable.NAME,
                values,
                ApptDbSchema.ApptTable.Cols.UUID + " = ?",
                new String[] { uuidString }
        );
    }

    public void updateUser(User user) {
        String uuidString = user.getId().toString();
        ContentValues values = getContentValues(user);

        mUserDatabase.update(
                LoginDbSchema.LoginTable.NAME,
                values,
                LoginDbSchema.LoginTable.Cols.UUID + " = ?",
                new String[] { uuidString }
        );
    }

    public List<Cuts> getCuts() {
        List<Cuts> cuts = new ArrayList<>();

        /** ONLY NEED ACCOUNT SPECIFIC CUTS
         *      NULL ARGS GET THE WHOLE LIST
         * **/
        CutsCursorWrapper cursor = queryCuts(
                CutsDbSchema.CutsTable.Cols.ACCT_ID + " = ?",
                new String[] { currUserId.toString() }
        );

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

        ApptCursorWrapper cursor = queryAppts(
                ApptDbSchema.ApptTable.Cols.USER_ID + " = ?",
                new String[] { currUserId.toString() } );

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

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        LoginCursorWrapper cursor = queryUsers(null, null);

        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                users.add(cursor.getUser());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return users;
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
        ApptCursorWrapper cursor = queryAppts(
                ApptDbSchema.ApptTable.Cols.UUID + " = ?",
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

    public User getUser(UUID userId) {
        LoginCursorWrapper cursor = queryUsers(
                LoginDbSchema.LoginTable.Cols.UUID + " = ?",
                new String[] { userId.toString() }
        );

        try {
            if(cursor.getCount() == 0)
                return null;

            cursor.moveToFirst();
            return cursor.getUser();
        }
        finally {
            cursor.close();
        }
    }

    public User getUser(String username, String password) {
        LoginCursorWrapper cursor = queryUsers(
                LoginDbSchema.LoginTable.Cols.USERNAME + " = ? AND " + LoginDbSchema.LoginTable.Cols.PASSWORD + " = ?",
                new String[] { username, password }
        );

        try {
            if(cursor.getCount() == 0)
                return null;

            cursor.moveToFirst();
            return cursor.getUser();
        }
        finally {
            cursor.close();
        }
    }

    public void setCurrUser(UUID userId) {
        currUserId = userId;
    }

    public UUID getCurrUserId() {
        return currUserId;
    }





    public Cuts getFavorite() {
        CutsCursorWrapper cursor = queryCuts(
                CutsDbSchema.CutsTable.Cols.FAVORITED + " = ?",
                new String[] { "true" }
        );

        try {
            if(cursor.getCount() == 0)
                return null;

            cursor.moveToFirst();
            return cursor.getCut();
        }
        finally {
            cursor.close();;
        }
    }

    public File getPhotoFile(Cuts cut) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, cut.getPhotoFileName());
    }

    private static ContentValues getContentValues(Cuts cut) {
        ContentValues values = new ContentValues();

        values.put(CutsDbSchema.CutsTable.Cols.DATE, cut.getCompletedDate().getTime());
        values.put(CutsDbSchema.CutsTable.Cols.DETAILS, cut.getCutDetails());
        values.put(CutsDbSchema.CutsTable.Cols.UUID, cut.getmId().toString());
        values.put(CutsDbSchema.CutsTable.Cols.FAVORITED, cut.isFavorite());
        values.put(CutsDbSchema.CutsTable.Cols.TYPE, cut.getCutType());
        values.put(CutsDbSchema.CutsTable.Cols.ACCT_ID, currUserId.toString());

        return values;
    }

    private static ContentValues getContentValues(Appointments appointment) {
        ContentValues values = new ContentValues();

        //ADDS to VALUES
        values.put(ApptDbSchema.ApptTable.Cols.UUID, appointment.getApptUUID().toString());
        values.put(ApptDbSchema.ApptTable.Cols.DATE, appointment.getScheduledDate().getTime());
        values.put(ApptDbSchema.ApptTable.Cols.DETAILS, appointment.getApptDetails());
        values.put(ApptDbSchema.ApptTable.Cols.TYPE, appointment.getCutType());
        values.put(ApptDbSchema.ApptTable.Cols.USER_ID, currUserId.toString());

        return values;
    }

    private static ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();

        values.put(LoginDbSchema.LoginTable.Cols.UUID, user.getId().toString());
        values.put(LoginDbSchema.LoginTable.Cols.USERNAME, user.getmUserName());
        values.put(LoginDbSchema.LoginTable.Cols.PASSWORD, user.getmPassword());
        values.put(LoginDbSchema.LoginTable.Cols.TYPE, user.getAcctType());
        values.put(LoginDbSchema.LoginTable.Cols.NAME, user.getFullName());
        values.put(LoginDbSchema.LoginTable.Cols.EMAIL, user.getEmail());
        values.put(LoginDbSchema.LoginTable.Cols.PHONE, user.getPhoneNumber());

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

    private LoginCursorWrapper queryUsers(String whereClause, String[] whereArgs) {
        Cursor cursor = mUserDatabase.query(
                LoginDbSchema.LoginTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new LoginCursorWrapper(cursor);
    }

}
