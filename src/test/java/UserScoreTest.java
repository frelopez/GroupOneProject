import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UserScoreTest.java
 * Verifies that user scores can be updated correctly
 * in the database.
 *
 * @author Daniel Barker
 * @version 0.1.0
 * @since 5/5/26
 */
public class UserScoreTest {

    @Test
    public void userScoreCanBeUpdated() {
        DatabaseManager db = DatabaseManager.getInstance();

        String unique = String.valueOf(System.nanoTime());
        int userId = db.insertUser("daniel_score_user_" + unique, "testpass");

        assertNotEquals(-1, userId);

        int startingScore = db.getUserScore(userId);

        db.updateUserScore(userId, 25);

        int updatedScore = db.getUserScore(userId);

        assertEquals(startingScore + 25, updatedScore);

        db.deleteUser(userId);
    }
}