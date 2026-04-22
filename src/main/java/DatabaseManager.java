/**
 * DatabaseManager.java
 * class responsible for all interfacing with databases
 * @author dennis strots
 * @version 0.1.0
 * @since 4/22/26
 */

import java.sql.*;

public class DatabaseManager {
	private static final String DB_URL = "jbdc:sqlite:app.db";
	private Connection connection;
	private static DatabaseManager instance;

	private DatabaseManager() {
		try {
			connection = DriverManager.getConnection(DB_URL);
			System.out.println("Database connected.");
			createTables();
		} catch (SQLException e) {
			System.err.println("Connection failed: " + e.getMessage());
		}
	}

	public static DatabaseManager getInstance() {
		if(instance == null) {
			instance = new DatabaseManager();
		}
		return instance;
	}

	public void close() {
		try {
			if(connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println("close failed: " + e.getMessage());
		}
	}

	private void createTables() {
		//check w3schools.com for docs
		//TODO MORE TABLES
		String sql = """
				CREATE TABLE IF NOT EXISTS users(
				    id		INTEGER	PRIMARY KEY	AUTOINCREMENT,
				    name	TEXT	NOT NULL,
				    password	TEXT	NOT NULL
				    )
				""";
		try(Statement stmt = connection.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.err.println("createTables failed: " + e.getMessage());
		}
	}
}
