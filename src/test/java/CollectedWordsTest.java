import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CollectedWordsTest.java
 * Verifies collected words can be added, retrieved,
 * updated, and deleted correctly.
 * @author Daniel Barker
 * @version 0.1.0
 * @since 4/29/26
 */

public class CollectedWordsTest {

    @Test
    public void collectedWordCanBeAddedAndRead() {
        DatabaseManager db = DatabaseManager.getInstance();

        String unique = String.valueOf(System.nanoTime());
        int userId = db.insertUser("daniel_test_user_" + unique, "testpass");
        int wordId = db.insertWord("banana_" + unique, 2);

        db.addCollectedWord(userId, wordId, 0);

        List<String> words = db.getCollectedWordsForUser(userId);

        assertTrue(words.contains("banana_" + unique));
    }

    @Test
    public void collectedWordCanBeUpdatedAndDeleted() {
        DatabaseManager db = DatabaseManager.getInstance();

        String unique = String.valueOf(System.nanoTime());
        int userId = db.insertUser("daniel_update_user_" + unique, "testpass");
        int firstWordId = db.insertWord("apple_" + unique, 1);
        int secondWordId = db.insertWord("orange_" + unique, 3);

        db.addCollectedWord(userId, firstWordId, 0);
        db.updateCollectedWord(userId, firstWordId, secondWordId);

        List<String> updatedWords = db.getCollectedWordsForUser(userId);

        assertFalse(updatedWords.contains("apple_" + unique));
        assertTrue(updatedWords.contains("orange_" + unique));

        db.deleteCollectedWord(userId, secondWordId);

        List<String> deletedWords = db.getCollectedWordsForUser(userId);

        assertFalse(deletedWords.contains("orange_" + unique));
    }
}