package database;

public class CutsDbSchema {
    public static final class CutsTable {
        //table name
        public static final String NAME = "cuts";

        //contains the final class that defines the colums
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String DETAILS = "details";
            public static final String DATE = "date";
            public static final String FAVORITED = "favorite";
            public static final String TYPE = "type";
        }
    }
}
