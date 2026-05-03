import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class LeaderboardController{
    @FXML
    private Button returnBtn;
    @FXML
    private Label score1;
    @FXML
    private Label score2;
    @FXML
    private Label score3;


    public Scene buildScene() {
        URL fxmlURL = getClass().getResource("Leaderboard.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlURL);
        Scene scene = null;
        try {
            Parent root = loader.load();
            scene = new Scene(root, 640, 480);
        } catch (IOException e) {
            System.err.println("Leaderboard buildScene failed: " + e.getMessage());
        }
        return scene;
    }


    public void initialize() {
        DatabaseManager db = DatabaseManager.getInstance();
        List<String[]> top = db.getTopUsers(3);

        if (top.size() > 0) score1.setText(top.get(0)[0] + " " + top.get(0)[1]);
        if (top.size() > 1) score2.setText(top.get(1)[0] + " " + top.get(1)[1]);
        if (top.size() > 2) score3.setText(top.get(2)[0] + " " + top.get(2)[1]);
    }



    public void returnButtonClicked(ActionEvent actionEvent) throws IOException {
        SceneManager.getInstance().navigateTo(SceneType.MAIN);
    }
}
