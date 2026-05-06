import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * SuccessController.java
 * Controls the success scene shown after the user wins a hangman puzzle.
 *
 * Displays the solved word, score change, and allows the user
 * to return to the main menu.
 *
 * @author Daniel Barker
 * @version 0.1.0
 * @since 5/4/26
 */
public class SuccessController {
    @FXML
    private Label wordLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label messageLabel;

    public Scene buildScene() {
        URL fxmlURL = getClass().getResource("/Success.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlURL);

        try {
            Parent root = loader.load();
            return new Scene(root, 640, 480);
        } catch (IOException e) {
            System.err.println("SuccessController buildScene failed: " + e.getMessage());
            return null;
        }
    }

    @FXML
    public void initialize() {
        GameManager gm = GameManager.getInstance();
        DatabaseManager db = DatabaseManager.getInstance();

        String word = gm.getRandomWord();
        int scoreChange = gm.getEarnedScore();

        wordLabel.setText("Word was: " + word);
        scoreLabel.setText("Score change: +" + scoreChange);

        int userId = getCurrentUserId();
        if (userId != -1) {
            db.updateUserScore(userId, scoreChange);
        }
    }

    public void collectWord() {
        GameManager gm = GameManager.getInstance();
        DatabaseManager db = DatabaseManager.getInstance();

        int userId = getCurrentUserId();
        String word = gm.getRandomWord();

        if (userId == -1 || word == null || word.isBlank()) {
            messageLabel.setText("Could not collect word.");
            return;
        }

        List<String> collected = db.getCollectedWordsForUser(userId);

        if (collected.contains(word)) {
            messageLabel.setText("Word already collected.");
            return;
        }

        if (collected.size() >= 3) {
            messageLabel.setText("Collection is full.");
            return;
        }

        int wordId = db.getWordId(word);
        if (wordId == -1) {
            wordId = db.insertWord(word, 2);
        }

        db.addCollectedWord(userId, wordId, collected.size());
        messageLabel.setText("Word collected!");
    }

    public void continueToLeaderboard() {
        SceneManager.getInstance().navigateTo(SceneType.LEADERBOARD);
    }

    private int getCurrentUserId() {
        GameManager gm = GameManager.getInstance();
        DatabaseManager db = DatabaseManager.getInstance();

        if (gm.getUserId() > 0) {
            return gm.getUserId();
        }

        if (gm.getUser() != null) {
            return db.getUserId(gm.getUser());
        }

        return -1;
    }
}