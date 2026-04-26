import org.junit.jupiter.api.BeforeEach;

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
}
