import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * CollectionController.java
 * Controls the collected words scene where users can view
 * saved words and choose one to send as an attack.
 *
 * Handles scene loading, populating the word list from
 * the database, and navigation back to main menu.
 * @author Daniel Barker
 * @version 0.1.0
 * @since 4/29/26
 */

public class CollectionController {
    @FXML
    private ListView<String> wordListView;

    @FXML
    private Label messageLabel;

    public Scene buildScene() {
        URL fxmlURL = getClass().getResource("/Collection.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlURL);
        Scene scene = null;

        try {
            Parent root = loader.load();
            scene = new Scene(root, 640, 480);
        } catch (IOException e) {
            System.err.println("CollectionController buildScene failed: " + e.getMessage());
        }

        return scene;
    }

    @FXML
    public void initialize() {
        DatabaseManager db = DatabaseManager.getInstance();

        int userId = db.getUserId("test user 1");

        if (userId == -1) {
            userId = db.insertUser("test user 1", "password");
            int wordId = db.insertWord("banana", 2);
            db.addCollectedWord(userId, wordId, 0);
        }

        List<String> words = db.getCollectedWordsForUser(userId);
        wordListView.getItems().setAll(words);

        if (words.isEmpty()) {
            messageLabel.setText("No collected words yet.");
        }
    }

    public void sendAttack() {
        String selectedWord = wordListView.getSelectionModel().getSelectedItem();

        if (selectedWord == null) {
            messageLabel.setText("Choose a word before sending an attack.");
        } else {
            messageLabel.setText("Attack selected with word: " + selectedWord);
        }
    }

    public void returnToMenu() {
        SceneManager.getInstance().navigateTo(SceneType.MAIN);
    }
}