package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/to_do_list";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "12345";

    private Connection connection = null;
    private static DBConnection instance;

    private DBConnection() {
    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    public static DBConnection getInstance() {
        if (null == instance) {
            instance = new DBConnection();
        }
        return instance;
    }
}
