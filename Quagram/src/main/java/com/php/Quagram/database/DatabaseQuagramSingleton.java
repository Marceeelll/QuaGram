package com.php.Quagram.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseQuagramSingleton {
	public static DatabaseQuagramSingleton sharedInstance = new DatabaseQuagramSingleton();
	private DatabaseQuagramSingleton() {
		startConnection();
	}
	
	// JDBC driver name and database url
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/Quagram";
		
	// Database credentials
	private static final String USER = "root";
	private static final String PASS = "pmp";	
	
	public Connection connection = null;
	public Statement statement = null;
	
	private void startConnection() {
		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// Open connection
			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute a query
			System.out.println("Creating statement");
			statement = connection.createStatement();
			
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
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Integer> createRandomNumberArrayWithMaxNumber(int maxNumber) {
		ArrayList<Integer> randomNumbers = new ArrayList<>();
		
		while(randomNumbers.size() < maxNumber) {
			int randomNumber = (int)(Math.random() * maxNumber);
			if (!randomNumbers.contains(randomNumber)) {
				randomNumbers.add(randomNumber);
			}
		}
		
		return randomNumbers;
	}
	
}

