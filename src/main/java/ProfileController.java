import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;

public class ProfileController {
    @FXML
    private Label username;
    @FXML
    private Button MainMenu;
    @FXML
    private Label Score;

        public Scene buildScene(){
            URL fxmlURL = getClass().getResource("/Profile.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Scene scene = null;
            try{
                Parent root = loader.load();
                scene = new Scene(root, 640, 480);
            }catch (IOException e){
                System.err.println("ProfileController failed: " + e.getMessage());
            }
            return scene;
        }
        public void initialize(){
            String user = GameManager.getInstance().getUserName();
            username.setText(user);
            int userid = DatabaseManager.getInstance().getUserId(user);
            int score = DatabaseManager.getInstance().getUserScore(userid);
            Score.setText("My Score: "+ score);
        }
        public void SetOnActionMainMenu(ActionEvent e){
            SceneManager.getInstance().navigateTo(SceneType.MAIN);
        }

}
