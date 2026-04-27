import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DatabaseManagerTest.java
 * tests for the database manager and sqlite databases we've made so far
 * @author dennis strots
 * @version 0.1.0
 * @since 4/25/26
 */

class DatabaseManagerTest {
	private DatabaseManager db;

	@BeforeEach
	void setUp() {
		db = DatabaseManager.getInstance();
	}

	@Test
	public void usersTableTest() {
		// make sure the usernames we're testing on dont exist yet
		db.deleteUser(db.getUserId("test user 1"));
		db.deleteUser(db.getUserId("john test user"));
		// testing databasemanager's existence
		assertNotNull(db);
		// testing insertion
		int startSize = db.getAllUsers().size();
		db.insertUser("test user 1", "password");
		assertNotEquals(startSize, db.getAllUsers().size());
		int testUserId = db.getUserId("test user 1");
		assertNotEquals(-1, testUserId);
		// testing no duplicates
		int dupeTestSize = db.getAllUsers().size();
		db.insertUser("test user 1", "drowssap");
		assertEquals(dupeTestSize,db.getAllUsers().size());
		// test rename
		db.updateUserName(testUserId, "john test user");
		assertNotEquals(-1,db.getUserId("john test user"));
		assertEquals(-1, db.getUserId("test user 1"));
		assertEquals(testUserId, db.getUserId("john test user"));
		// test delete
		db.deleteUser(testUserId);
		assertEquals(startSize,db.getAllUsers().size());
	}
}
