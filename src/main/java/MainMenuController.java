import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;

/**
 * MainMenuController.java
 * Handles loading the main menu interface,
 * navigating to the collected words scene,
 * and exiting the application.
 *
 * this file & scene is unused, use PROFILE instead
 * @author Daniel Barker
 * @version 0.1.0
 * @since 4/29/26
 */

public class MainMenuController {

    public Scene buildScene() {
        URL fxmlURL = getClass().getResource("/MainMenu.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlURL);

        try {
            Parent root = loader.load();
            return new Scene(root, 640, 480);
        } catch (IOException e) {
            System.err.println("MainMenuController buildScene failed: " + e.getMessage());
            return null;
        }
    }

    public void openCollection() {
        SceneManager.getInstance().navigateTo(SceneType.COLLECTION);
    }

    public void closeApp() {
        System.exit(0);
    }

    public void playGame() { SceneManager.getInstance().navigateTo(SceneType.PLAY); }

    public void openProfile() { SceneManager.getInstance().navigateTo(SceneType.PROFILE); }

    public void openLeaderboard() { SceneManager.getInstance().navigateTo(SceneType.LEADERBOARD); }
}