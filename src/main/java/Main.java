import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private DatabaseManager db;

    @Override
    public void start(Stage stage) {
        db = DatabaseManager.getInstance();
        stage.setTitle("Hang Your Friends, Man");

        SceneManager.init(stage);
        SceneManager.getInstance().navigateTo(SceneType.MAIN);

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