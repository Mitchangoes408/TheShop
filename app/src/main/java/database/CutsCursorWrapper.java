package database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.main.theshop.Cuts;

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

        Cuts cut = new Cuts(UUID.fromString(uuidString));
        cut.setCutDetails(details);
        cut.setmDate(new Date(date));
        cut.setFavorite(isFavorite);
        cut.setCutType(type);

        return cut;
    }
}
