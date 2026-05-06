import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.util.List;


public class LeaderboardController{
    @FXML
    private Button returnBtn;
    @FXML
    private TableView<LeaderboardEntry> leaderboard;
    @FXML
    private TableColumn<LeaderboardEntry, Integer> scoreCol;
    @FXML
    private TableColumn<LeaderboardEntry, Integer> rankCol;
    @FXML
    private TableColumn<LeaderboardEntry, String> nameCol;

    private DatabaseManager db = DatabaseManager.getInstance();


    public Scene buildScene() {
        URL fxmlURL = getClass().getResource("Leaderboard.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlURL);
        Scene scene = null;
        try {
            Parent root = loader.load();
            scene = new Scene(root, 640, 480);
        } catch (IOException e) {

            System.err.println("Leaderboard buildScene failed: " + e.getMessage());
            e.printStackTrace();
        }
        return scene;
    }


    public void initialize() {
        rankCol.setCellValueFactory(new PropertyValueFactory<>("rank"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));

        List<String[]> top = db.getTopUsers(10);
        ObservableList<LeaderboardEntry> data = FXCollections.observableArrayList();

        for (int i = 0; i < top.size(); i++) {
            data.add(new LeaderboardEntry(i+1, top.get(i)[0], Integer.parseInt(top.get(i)[1])));
        }
        leaderboard.setItems(data);
    }


    public void returnButtonClicked(ActionEvent actionEvent) throws IOException {
        SceneManager.getInstance().navigateTo(SceneType.PROFILE);
    }
}




