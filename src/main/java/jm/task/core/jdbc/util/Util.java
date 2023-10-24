package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/new_schema";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root1";


    public static Connection getConnected() {

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database connected!");
        } catch (SQLException e) {
            System.out.println("Database not connected");
        } finally {
            return connection;
        }

    }

    // реализуйте настройку соеденения с БД
}
