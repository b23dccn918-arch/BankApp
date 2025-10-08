package com.mycompany.bankonline.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/bank";
    private static final String DB_USER = "root"; 
    private static final String DB_PASS = ""; 

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // trả về null nếu lỗi
        }
    }

}



