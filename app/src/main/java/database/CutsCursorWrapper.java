package database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.main.theshop.models.Cuts;

import java.util.Date;
import java.util.UUID;

public class CutsCursorWrapper extends CursorWrapper{
    public CutsCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Cuts getCut() {
        String uuidString = getString(getColumnIndex(CutsDbSchema.CutsTable.Cols.UUID));
        String details = getString(getColumnIndex(CutsDbSchema.CutsTable.Cols.DETAILS));
        long date = getLong(getColumnIndex(CutsDbSchema.CutsTable.Cols.DATE));
        String isFavorite = getString(getColumnIndex(CutsDbSchema.CutsTable.Cols.FAVORITED));
        String type = getString(getColumnIndex(CutsDbSchema.CutsTable.Cols.TYPE));
        String acctId = getString(getColumnIndex(CutsDbSchema.CutsTable.Cols.ACCT_ID));

        Cuts cut = new Cuts(UUID.fromString(uuidString));
        cut.setCutDetails(details);
        cut.setCompletedDate(new Date(date));
        cut.setFavorite(isFavorite);
        cut.setCutType(type);
        cut.setAcctId(UUID.fromString(acctId));

        return cut;
    }
}
