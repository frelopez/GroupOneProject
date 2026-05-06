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

	//TODO: insert all scene constructors when they become available. it will be a simple null for now
	public static Scene create(SceneType type, Stage stage) {
		return switch (type) {
	    case MAIN -> new MainMenuController().buildScene();
      case PLAY -> new PlayController().buildScene();
	    case LOGIN -> new LogInController().buildScene();
			case FAILURE -> new FailureController().buildScene();
			case PROFILE -> new ProfileController().buildScene();
			case SUCCESS -> new SuccessController().buildScene();
			case ATTACKING -> new SendAttackController().buildScene(stage);
			case COLLECTION -> new CollectionController().buildScene();
			case LEADERBOARD -> new LeaderboardController().buildScene();
			case SIGN_UP -> new SignUpController().buildScene();
			case PLAY_ATTACKED -> new PlayAttackedController().buildScene();
			case FAILURE_ATTACKED -> new FailureAttackedController().buildScene();
			case SUCCESS_ATTACKED -> new SuccessAttackedController().buildScene();
		};
	}


}
