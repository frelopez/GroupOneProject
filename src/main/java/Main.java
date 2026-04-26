import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	// to run, type the following line into your terminal!
	// ./gradlew run

	private DatabaseManager db;
	@Override
	public void start(Stage stage) {
		//TODO make a REAL main method
		db = DatabaseManager.getInstance();
		stage.setTitle("THIS IS TEMPORARY WATCH OUT");
		stage.setScene(new Scene(new VBox(),600,400));
		stage.show();
	}
	@Override
	public void stop() {
		if(db!=null) {
			db.close();
		}
	}
}
