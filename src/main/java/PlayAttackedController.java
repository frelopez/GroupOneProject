import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;

public class PlayAttackedController {
	public Scene buildScene() {
		URL fxmlURL = getClass().getResource("/PlayAttacked.fxml");
		FXMLLoader loader = new FXMLLoader(fxmlURL);
		Scene scene = null;
		try {
			Parent root = loader.load();
			scene = new Scene(root, 640, 480);
		} catch (IOException e) {
			System.err.println("PlayAttackedController buildScene failed: "+e.getMessage());
		}
		return scene;
	}
}