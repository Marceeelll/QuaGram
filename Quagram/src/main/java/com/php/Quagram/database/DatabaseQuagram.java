package com.php.Quagram.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseQuagram {

	// JDBC driver name and database url
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/Quagram";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "pmp";
	
	public void get() {
		
	}

	public void getUsernames() {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stmt = null;

		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute a query
			System.out.println("Creating statement");
			stmt = conn.createStatement();

			String sql;
			sql = "Select username from user";
			ResultSet rs = stmt.executeQuery(sql);

			// Extract data from result set
			while (rs.next()) {

				// Retrieve by column name
				String firstname = rs.getString("username");
				

				// Display
				System.out.println("Username = " + firstname);
			}

			// Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// Handle errors for JDBC
			e.printStackTrace();
		} catch (Exception f) {
			// Handle errors for Class.ForName
			f.printStackTrace();
		} finally {
			// Used to close resources
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException g) {
				// Nothing you can do
				g.printStackTrace();
			}

			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException h) {
				h.printStackTrace();
			}
		}
		System.out.println("Goodbye");
	}

}