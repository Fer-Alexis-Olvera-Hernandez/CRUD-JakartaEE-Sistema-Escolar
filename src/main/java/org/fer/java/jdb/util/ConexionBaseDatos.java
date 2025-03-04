package org.fer.java.jdb.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
public class ConexionBaseDatos {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/tesvb?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static BasicDataSource pool;

    public static BasicDataSource getInstance() throws SQLException {
        if (pool == null) {
            pool = new BasicDataSource();
            pool.setUrl(URL);
            pool.setUsername(USER);
            pool.setPassword(PASSWORD);
            pool.setInitialSize(3);
            pool.setMinIdle(3);
            pool.setMaxIdle(8);
            pool.setMaxTotal(8);
        }
        return pool;
    }

    public static Connection getConnection() throws SQLException {
        return getInstance().getConnection();
    }
}