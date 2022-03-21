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
        String title = getString(getColumnIndex(CutsDbSchema.CutsTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CutsDbSchema.CutsTable.Cols.DATE));
        String isFavorite = getString(getColumnIndex(CutsDbSchema.CutsTable.Cols.FAVORITED));

        Cuts cut = new Cuts(UUID.fromString(uuidString));
        cut.setmTitle(title);
        cut.setmDate(new Date(date));
        cut.setFavorite(Integer.parseInt(isFavorite));

        return cut;
    }
}
