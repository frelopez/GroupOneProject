import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;

/**
 * FailureController.java
 * Controls the failure scene shown after the user loses a hangman puzzle.
 *
 * Displays the missed word, score change, and allows the user
 * to return to the main menu.
 *
 * @author Daniel Barker
 * @version 0.1.0
 * @since 5/4/26
 */
public class FailureController {
    @FXML
    private Label wordLabel;

    @FXML
    private Label scoreLabel;

    public Scene buildScene() {
        URL fxmlURL = getClass().getResource("/Failure.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlURL);
        Scene scene = null;

        try {
            Parent root = loader.load();
            scene = new Scene(root, 640, 480);
        } catch (IOException e) {
            System.err.println("FailureController buildScene failed: " + e.getMessage());
        }

        return scene;
    }

    @FXML
    public void initialize() {
        GameManager gm = GameManager.getInstance();

        String word = gm.getRandomWord();

        if (word == null || word.isBlank()) {
            wordLabel.setText("Word was: unknown");
        } else {
            wordLabel.setText("Word was: " + word);
        }

        scoreLabel.setText("Score change: -" + Math.abs(gm.getEarnedScore()));
    }

    public void continueToMenu() {
        SceneManager.getInstance().navigateTo(SceneType.MAIN);
    }
}