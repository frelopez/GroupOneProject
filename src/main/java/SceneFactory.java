/**
 * SceneFactory.java
 * Call this class when constructing any scene
 * @author dennis strots
 * @version 0.1.0
 * @since 4/20/26
 */

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneFactory {

	//TODO: refactor this when we learn how
	//TODO: insert all scene constructors when they become available. it will be a simple null for now
	public static Scene create(SceneType type, Stage stage) {
		return switch (type) {
			case MAIN -> new MainMenuController().buildScene();
			case PLAY -> null;
			case LOGIN -> null;
			case FAILURE -> null;
			case PROFILE -> null;
			case SUCCESS -> null;
			case ATTACKING -> null;
			case COLLECTION -> new CollectionController().buildScene();
			case LEADERBOARD -> null;
			case SIGN_UP -> null;
			case PLAY_ATTACKED -> null;
			case FAILURE_ATTACKED -> null;
			case SUCCESS_ATTACKED -> null;
		};
	}
}
