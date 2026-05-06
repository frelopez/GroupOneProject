import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LeaderboardTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Leaderboard.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testLeaderboardExists() {
        TableView<?> tableView = lookup("#leaderboard").queryAs(TableView.class);
        assertNotNull(tableView);
    }
}
