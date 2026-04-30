import javafx.application.Application;
import javafx.stage.Stage;

//javafx documentation here:
//https://openjfx.io/javadoc/26/
public class Main extends Application {
// to run, type the following line into your terminal!
    // ./gradlew run

    private final int STAGE_WIDTH = 640;
    private final int STAGE_HEIGHT = 480;
    private DatabaseManager db;

    @Override
    public void start(Stage stage) {
        db = DatabaseManager.getInstance();
        stage.setTitle("Hang Your Friends, Man!");
        stage.setMaxHeight(STAGE_HEIGHT); stage.setMinHeight(STAGE_HEIGHT);
        stage.setMaxWidth(STAGE_WIDTH); stage.setMinWidth(STAGE_WIDTH);
        SceneManager.init(stage);
        SceneManager.getInstance().navigateTo(SceneType.PLAY);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        if (db != null) {
            db.close();
        }
    }
}