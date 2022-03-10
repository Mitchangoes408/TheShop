package database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.main.theshop.Appointments;

import java.util.Date;
import java.util.UUID;

public class ApptCursorWrapper extends CursorWrapper{
    public ApptCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Appointments getAppt() {
        String uuidString = getString(getColumnIndex(ApptDbSchema.ApptTable.Cols.UUID));
        String title = getString(getColumnIndex(ApptDbSchema.ApptTable.Cols.TITLE));
        long date = getLong(getColumnIndex(ApptDbSchema.ApptTable.Cols.DATE));

        Appointments appointment = new Appointments(UUID.fromString(uuidString));
        appointment.setScheduledDate(new Date(date));
        appointment.setTitle(title);

        return appointment;
    }
}