/**
 * DatabaseManager.java
 * class responsible for all interfacing with databases
 * @author dennis strots
 * @version 0.1.0
 * @since 4/22/26
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
	private static final String DB_URL = "jdbc:sqlite:app.db";
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
		//check w3schools.com for docs!
		String sql = """
				CREATE TABLE IF NOT EXISTS Users(
				    id INTEGER PRIMARY KEY AUTOINCREMENT,
				    name TEXT NOT NULL,
				    password TEXT NOT NULL,
				    score INTEGER NOT NULL DEFAULT 0,
				    peak_score INTEGER NOT NULL DEFAULT 0,
				    word_0_id INTEGER,
				    word_1_id INTEGER,
				    word_2_id INTEGER,
				    CONSTRAINT UC_User UNIQUE (name),
				    CONSTRAINT FK_Word0 FOREIGN KEY (word_0_id) REFERENCES Words(id),
				    CONSTRAINT FK_Word1 FOREIGN KEY (word_1_id) REFERENCES Words(id),
				    CONSTRAINT FK_Word2 FOREIGN KEY (word_2_id) REFERENCES Words(id)
				)
				""";

		try(Statement stmt = connection.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.err.println("createTables failed: " + e.getMessage());
		}

		sql = """
				CREATE TABLE IF NOT EXISTS Words(
				    id INTEGER PRIMARY KEY AUTOINCREMENT,
				    word TEXT NOT NULL,
				    difficulty INTEGER NOT NULL
				)
			""";

		try(Statement stmt = connection.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.err.println("createTables failed: " + e.getMessage());
		}

		sql = """
				CREATE TABLE IF NOT EXISTS Attacks(
				    id INTEGER PRIMARY KEY AUTOINCREMENT,
				    origin_id INTEGER NOT NULL,
				    destination_id INTEGER NOT NULL,
				    word_id INTEGER NOT NULL,
				    CONSTRAINT FK_origin FOREIGN KEY (origin_id) REFERENCES Users(id),
				    CONSTRAINT FK_destination FOREIGN KEY (destination_id) REFERENCES Users(id),
				    CONSTRAINT FK_word FOREIGN KEY (word_id) REFERENCES Words(id)
				)
			""";

		try(Statement stmt = connection.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.err.println("createTables failed: " + e.getMessage());
		}
	}

	//TODO maybe return value based on if username already exists?
	public void insertUser(String name, String password) {
		String sql = "INSERT INTO Users (name, password) VALUES (?, ?)";
		try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("insertion failed: "+e.getMessage());
		}
	}

	//TODO redo this once we settle on a concrete implementation
	public List<String> getAllUsers() {
		List<String> usernames = new ArrayList<>();
		String sql = "SELECT name FROM Users ORDER BY id DESC";
		try(Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql)) {
			while(rs.next()) {
				usernames.add(rs.getString("name"));
			}
		} catch(SQLException e) {
			System.err.println("getAllUsers failed: "+e.getMessage());
		}
		return usernames;
	}

	//TODO methods to update a user's collected words
	public void updateUserName(int id, String name) {
		String sql = "UPDATE Users SET name = ? WHERE id = ?";
		try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, name);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			System.err.println("updateUserName failed: "+e.getMessage());
		}
	}

	public void deleteUser(int id) {
		String sql = "DELETE FROM Users WHERE id = ?";
		try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setInt(1,id);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			System.err.println("deleteUser failed: "+e.getMessage());
		}
	}

	//TODO figure out whether this should be temporary
	public void resetUserTable() {
		String sql = "DELETE FROM Users";
		try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.execute();
		} catch(SQLException e) {
			System.err.println("resetUserTable failed: "+e.getMessage());
		}
	}

	public void insertWord(String word, int difficulty) {
		String sql = "INSERT INTO Words (word, difficulty) VALUES (?)";
		try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1,word);
			pstmt.setInt(2,difficulty);
		} catch (SQLException e) {
			System.err.println("insertion failed: " + e.getMessage());
		}
	}

	//TODO figure out how to use foreign keys here. write insertAttack
	public void insertAttack() {
		System.out.println("NOT IMPLEMENTED, SILLY!");
	}

	//TODO ok this one is definitely temporary get rid of it when done using
	public void dropEverything() {
		String sql = "DROP TABLE Users";
		try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.execute();
		}catch(SQLException e) {
			System.err.println("FRICK "+e.getMessage());
		}
		sql = "DROP TABLE Words";
		try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.execute();
		}catch(SQLException e) {
			System.err.println("FRICK "+e.getMessage());
		}
		sql = "DROP TABLE Attacks";
		try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.execute();
		}catch(SQLException e) {
			System.err.println("FRICK "+e.getMessage());
		}
	}
}
