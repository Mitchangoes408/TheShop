package database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.main.theshop.models.User;

import java.util.UUID;

public class LoginCursorWrapper extends CursorWrapper {
    public LoginCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser() {
        String uuidString = getString(getColumnIndex(LoginDbSchema.LoginTable.Cols.UUID));
        String userName = getString(getColumnIndex(LoginDbSchema.LoginTable.Cols.USERNAME));
        String password = getString(getColumnIndex(LoginDbSchema.LoginTable.Cols.PASSWORD));
        String acctType = getString(getColumnIndex(LoginDbSchema.LoginTable.Cols.TYPE));
        String fullName = getString(getColumnIndex(LoginDbSchema.LoginTable.Cols.NAME));
        String email = getString(getColumnIndex(LoginDbSchema.LoginTable.Cols.EMAIL));
        String phone = getString(getColumnIndex(LoginDbSchema.LoginTable.Cols.PHONE));


        User user = new User(UUID.fromString(uuidString));
        user.setmUserName(userName);
        user.setmPassword(password);
        user.setAcctType(acctType);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhoneNumber(phone);

        return user;
    }

}
