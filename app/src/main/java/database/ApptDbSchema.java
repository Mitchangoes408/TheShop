package database;

public class ApptDbSchema {
    public static final class ApptTable {
        //table name
        public static final String NAME = "appointments";

        //columns
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String DATE = "date_scheduled";
            public static final String TYPE = "type";
            public static final String DETAILS = "details";
            public static final String USER_ID = "user_id";
        }
    }

}
