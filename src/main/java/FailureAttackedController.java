import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;

public class FailureAttackedController {
    @FXML
    private Label wordLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label attackWordsLabel;

    public Scene buildScene() {
        URL fxmlURL = getClass().getResource("/FailureAttacked.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlURL);

        try {
            Parent root = loader.load();
            return new Scene(root, 640, 480);
        } catch (IOException e) {
            System.err.println("FailureAttackedController buildScene failed: " + e.getMessage());
            return null;
        }
    }

    @FXML
    public void initialize() {
        GameManager gm = GameManager.getInstance();
        DatabaseManager db = DatabaseManager.getInstance();

        wordLabel.setText("Main word was: " + gm.getRandomWord());
        scoreLabel.setText("Score change: " + gm.getEarnedScore());
        attackWordsLabel.setText("Attack words: " + getAttackWordsText());

        int userId = getCurrentUserId();
        if (userId != -1) {
            db.updateUserScore(userId, gm.getEarnedScore());
        }

        deleteCompletedAttacks();
    }

    public void continueToMenu() {
        SceneManager.getInstance().navigateTo(SceneType.MAIN);
    }

    private String getAttackWordsText() {
        DatabaseManager db = DatabaseManager.getInstance();
        GameManager gm = GameManager.getInstance();

        StringBuilder result = new StringBuilder();

        appendAttackWord(result, db, gm.getAttackWordID0());
        appendAttackWord(result, db, gm.getAttackWordID1());
        appendAttackWord(result, db, gm.getAttackWordID2());

        if (result.isEmpty()) {
            return "none";
        }

        return result.toString();
    }

    private void appendAttackWord(StringBuilder result, DatabaseManager db, int attackId) {
        if (attackId == -1) {
            return;
        }

        int wordId = db.getAttackWord(attackId);
        String word = db.getWordText(wordId);

        if (word != null && !word.isBlank()) {
            if (!result.isEmpty()) {
                result.append(", ");
            }
            result.append(word);
        }
    }

    private void deleteCompletedAttacks() {
        DatabaseManager db = DatabaseManager.getInstance();
        GameManager gm = GameManager.getInstance();

        if (gm.getAttackWordID0() != -1) db.deleteAttack(gm.getAttackWordID0());
        if (gm.getAttackWordID1() != -1) db.deleteAttack(gm.getAttackWordID1());
        if (gm.getAttackWordID2() != -1) db.deleteAttack(gm.getAttackWordID2());
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