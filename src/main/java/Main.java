import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
// to run, type the following line into your terminal!
    // ./gradlew run

    private DatabaseManager db;
    @Override
    public void start(Stage stage) {
        db = DatabaseManager.getInstance();
        stage.setTitle("Main");
        SceneManager.getInstance().navigateTo(SceneType.MAIN);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void stop() {
        if(db!=null) {
            db.close();
        }
    }
}
