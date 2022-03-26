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
        String details = getString(getColumnIndex(ApptDbSchema.ApptTable.Cols.DETAILS));
        long date = getLong(getColumnIndex(ApptDbSchema.ApptTable.Cols.DATE));
        String apptType = getString(getColumnIndex(ApptDbSchema.ApptTable.Cols.TYPE));

        Appointments appointment = new Appointments(UUID.fromString(uuidString));
        appointment.setScheduledDate(new Date(date));
        appointment.setApptDetails(details);
        appointment.setCutType(apptType);

        return appointment;
    }
}