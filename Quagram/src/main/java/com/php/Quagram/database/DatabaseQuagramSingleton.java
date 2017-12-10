package com.php.Quagram.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseQuagramSingleton {
	public static DatabaseQuagramSingleton sharedInstance = new DatabaseQuagramSingleton();
	private DatabaseQuagramSingleton() {
		startConnection();
	}
	
	// JDBC driver name and database url
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/Quagram";
		
	// Database credentials
	static final String USER = "root";
	static final String PASS = "pmp";	
	
	Connection conn = null;
	public Statement stmt = null;
	
	private void startConnection() {
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute a query
			System.out.println("Creating statement");
			stmt = conn.createStatement();
			
			System.out.println("Connected to Database");
		} catch (SQLException e) {
			// Handle errors for JDBC
			e.printStackTrace();
		} catch (Exception f) {
			// Handle errors for Class.ForName
			f.printStackTrace();
		}
	}
	
	public void closeConnection() {
		// Clean-up environment
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
