package database;

public class LoginDbSchema {
    public static final class LoginTable {
        public static final String NAME = "login";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";
            public static final String TYPE = "type";
        }
    }
}
