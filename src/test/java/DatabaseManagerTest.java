import org.junit.jupiter.api.*;

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
		System.setProperty("app.db.url","jdbc:sqlite::memory:");
		DatabaseManager.resetForTesting();
	}

	@AfterEach
	void teardown() {
		DatabaseManager.resetForTesting();
	}

	@Test
	@DisplayName("getInstance returns the same thing each time")
	void singletonIdentity() {
		DatabaseManager a = DatabaseManager.getInstance();
		DatabaseManager b = DatabaseManager.getInstance();
		assertEquals(a,b);
	}

	@Test
	@DisplayName("CRUD testing on Users table")
	void usersTableTest() {
		DatabaseManager db = DatabaseManager.getInstance();
		db.insertUser("mr. tester","password123");
		assertTrue(db.getAllUsers().contains("mr. tester"));
		int testUserID = db.getUserId("mr. tester");
		assertEquals(testUserID, db.loginUser("mr. tester","password123"));
		//updateUserName is never used, but testing it anyway
		db.updateUserName(testUserID, "ms. tester"); //oh, she transitioned, congratulations!
		assertTrue(db.getAllUsers().contains("ms. tester"));
		assertFalse(db.getAllUsers().contains("mr. tester"));
		db.updateUserScore(testUserID, 999999999);
		assertEquals(999999999, db.getUserScore(testUserID));
		db.deleteUser(testUserID);
		assertFalse(db.getAllUsers().contains("ms. tester"));
	}

	@Test
	@DisplayName("CRUD testing on Words table (Words should not be edited at any point)")
	void wordsTableTest() {
		DatabaseManager db = DatabaseManager.getInstance();
		int testWordID = db.insertWord("testword", 1);
		int testUserID = db.insertUser("word liker", "password456");
		assertTrue(db.getAllWords().contains("testword"));
		assertEquals(testWordID, db.getWordId("testword"));
		assertEquals("testword", db.getWordText(testWordID));
		db.addCollectedWord(testUserID, testWordID, 0);
		assertTrue(db.getCollectedWordsForUser(testUserID).contains("testword"));
		int testWord2ID = db.insertWord("cooler testword", 2);
		db.updateCollectedWord(testUserID,testWordID,testWord2ID);
		assertFalse(db.getCollectedWordsForUser(testUserID).contains("testword"));
		assertTrue(db.getCollectedWordsForUser(testUserID).contains("cooler testword"));
		db.deleteWord(testWordID);
		assertFalse(db.getAllWords().contains("testword"));
		db.deleteCollectedWord(testUserID,testWord2ID);
		assertFalse(db.getCollectedWordsForUser(testUserID).contains("cooler testword"));
	}

	@Test
	@DisplayName("CRUD testing on Attacks table (Attacks should not be edited at any point)")
	void attacksTableTest() {
		DatabaseManager db = DatabaseManager.getInstance();
		int testUserRID = db.insertUser("john red", "password red");
		int testUserBIF = db.insertUser("john blue", "password blue");
		int testWordID = db.insertWord("john attack", 99);
		int testAttackID = db.insertAttack(testWordID, testUserRID, testUserBIF);
		assertEquals(testUserRID, db.getAttackOrigin(testAttackID));
		assertEquals(testWordID, db.getAttackWord(testAttackID));
		assertTrue(db.getAttacksToDestination(testUserBIF).contains(testWordID));
		db.deleteAttack(testAttackID);
		assertEquals(-1, db.getAttackOrigin(testAttackID));
	}
}
