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

	public int insertUser(String name, String password) {
		String sql = "INSERT INTO Users (name, password) VALUES (?, ?)";
		try(PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			pstmt.executeUpdate();

			try (ResultSet keys = pstmt.getGeneratedKeys()) {
				if (keys.next()) {
					return keys.getInt(1);
				}
			}
		} catch (SQLException e) {
			System.err.println("insertUser failed: " + e.getMessage());
		}
		return -1;
	}

	public int loginUser(String name, String password){
		String sql = "Select id From Users Where name = ? AND password = ?";
		try(PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()){
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			System.err.println("LoginUser failed: "+ e.getMessage());
		}
		return -1;
	}

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

	/**
	 * gets the id of the User which matches the name parameter
	 * @param name name of user to get id of
	 * @return the id of the User, -1 if no such user or error
	 */
	public int getUserId(String name) {
		String sql = "SELECT id FROM Users WHERE name = ?";
		int returnMe = -1;
		try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				returnMe = rs.getInt("id");
			}
		} catch(SQLException e) {
			System.err.println("getUserId failed: "+e.getMessage());
		}
		return returnMe;
	}
	public int getUserScore(int id) {
		String sql = "SELECT id FROM Users WHERE id = ?";
		try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			try(ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) return rs.getInt("score");
			}
		}catch (SQLException e){
			System.err.println("getUserScore failed: "+e.getMessage());
		}
		return 0;
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

	//TODO temporary. get rid of before submitting
	public void resetUserTable() {
		String sql = "DELETE FROM Users";
		try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.execute();
		} catch(SQLException e) {
			System.err.println("resetUserTable failed: "+e.getMessage());
		}
	}

	public int insertWord(String word, int difficulty) {
		String sql = "INSERT INTO Words (word, difficulty) VALUES (?, ?)";
		try(PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, word);
			pstmt.setInt(2, difficulty);
			pstmt.executeUpdate();

			try (ResultSet keys = pstmt.getGeneratedKeys()) {
				if (keys.next()) {
					return keys.getInt(1);
				}
			}
		} catch (SQLException e) {
			System.err.println("insertWord failed: " + e.getMessage());
		}
		return -1;
	}

	private String getCollectedWordColumn(int slot) {
		return switch (slot) {
			case 0 -> "word_0_id";
			case 1 -> "word_1_id";
			case 2 -> "word_2_id";
			default -> throw new IllegalArgumentException("Collected word slot must be 0, 1, or 2.");
		};
	}

	public void addCollectedWord(int userId, int wordId, int slot) {
		String column = getCollectedWordColumn(slot);
		String sql = "UPDATE Users SET " + column + " = ? WHERE id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setInt(1, wordId);
			pstmt.setInt(2, userId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("addCollectedWord failed: " + e.getMessage());
		}
	}

	public List<String> getCollectedWordsForUser(int userId) {
		List<String> collectedWords = new ArrayList<>();

		String sql = """
		SELECT w0.word AS word0, w1.word AS word1, w2.word AS word2
		FROM Users u
		LEFT JOIN Words w0 ON u.word_0_id = w0.id
		LEFT JOIN Words w1 ON u.word_1_id = w1.id
		LEFT JOIN Words w2 ON u.word_2_id = w2.id
		WHERE u.id = ?
	""";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setInt(1, userId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					if (rs.getString("word0") != null) {
						collectedWords.add(rs.getString("word0"));
					}
					if (rs.getString("word1") != null) {
						collectedWords.add(rs.getString("word1"));
					}
					if (rs.getString("word2") != null) {
						collectedWords.add(rs.getString("word2"));
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("getCollectedWordsForUser failed: " + e.getMessage());
		}

		return collectedWords;
	}

	public void updateCollectedWord(int userId, int oldWordId, int newWordId) {
		String sql = """
		UPDATE Users
		SET
			word_0_id = CASE WHEN word_0_id = ? THEN ? ELSE word_0_id END,
			word_1_id = CASE WHEN word_1_id = ? THEN ? ELSE word_1_id END,
			word_2_id = CASE WHEN word_2_id = ? THEN ? ELSE word_2_id END
		WHERE id = ?
	""";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setInt(1, oldWordId);
			pstmt.setInt(2, newWordId);
			pstmt.setInt(3, oldWordId);
			pstmt.setInt(4, newWordId);
			pstmt.setInt(5, oldWordId);
			pstmt.setInt(6, newWordId);
			pstmt.setInt(7, userId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("updateCollectedWord failed: " + e.getMessage());
		}
	}

	public void deleteCollectedWord(int userId, int wordId) {
		String sql = """
		UPDATE Users
		SET
			word_0_id = CASE WHEN word_0_id = ? THEN NULL ELSE word_0_id END,
			word_1_id = CASE WHEN word_1_id = ? THEN NULL ELSE word_1_id END,
			word_2_id = CASE WHEN word_2_id = ? THEN NULL ELSE word_2_id END
		WHERE id = ?
	""";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setInt(1, wordId);
			pstmt.setInt(2, wordId);
			pstmt.setInt(3, wordId);
			pstmt.setInt(4, userId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("deleteCollectedWord failed: " + e.getMessage());
		}
	}

	public int insertAttack(int wordID, int originID, int destinationID) {
		String sql = "INSERT INTO Attacks (origin_id, destination_id, word_id) VALUES (?, ?, ?)";
		try(PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setInt(1,originID);
			pstmt.setInt(2,destinationID);
			pstmt.setInt(3,wordID);
			pstmt.executeUpdate();
			try (ResultSet keys = pstmt.getGeneratedKeys()) {
				if (keys.next()) {
					return keys.getInt(1);
				}
			}
		} catch(SQLException e) {
			System.err.println("insertAttack failed: "+e.getMessage());
		}
		return -1;
	}

	public int getAttackOrigin(int attackID) {
		String sql = "SELECT origin_id FROM Attacks WHERE id = ?";
		int returnMe = -1;
		try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setInt(1,attackID);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				returnMe = rs.getInt("origin_id");
			}
		} catch (SQLException e) {
			System.err.println("getAttackOrigin failed: "+e.getMessage());
		}
		return returnMe;
	}

	public int getAttackWord(int attackID) {
		String sql = "SELECT word_id FROM Attacks WHERE id = ?";
		int returnMe = -1;
		try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setInt(1,attackID);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				returnMe = rs.getInt("word_id");
			}
		} catch (SQLException e) {
			System.err.println("getAttackWord failed: "+e.getMessage());
		}
		return returnMe;
	}

	public List<Integer> getAttacksToDestination(int userID) {
		String sql = "SELECT id FROM Attacks WHERE destination_id = ?";
		List<Integer> returnMe = new ArrayList<>();
		try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setInt(1,userID);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				returnMe.add(rs.getInt("id"));
			}
		} catch (SQLException e) {
			System.err.println("getAttacksToDestination failed: "+e.getMessage());
		}
		return returnMe;
	}

	public void deleteAttack(int id) {
		String sql = "DELETE FROM Attacks WHERE id = ?";
		try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setInt(1,id);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			System.err.println("deleteAttack failed: "+e.getMessage());
		}
	}

	//TODO temporary. get rid of before submitting
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
