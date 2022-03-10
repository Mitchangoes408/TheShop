package database;

public class ApptDbSchema {
    public static final class ApptTable {
        //table name
        public static final String NAME = "appointments";

        //columns
        public static final class Cols {
            public static final String TITLE = "title";
            public static final String UUID = "uuid";
            public static final String DATE = "date";
        }
    }

}
