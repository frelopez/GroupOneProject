import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.util.List;


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
        String user = GameManager.getInstance().getUser();
        username.setText(user);
        int userid = DatabaseManager.getInstance().getUserId(user);
        int score = DatabaseManager.getInstance().getUserScore(userid);
        Score.setText("My Score: "+ score);
    }
    public void SetOnActionCollection(ActionEvent e){
        SceneManager.getInstance().navigateTo(SceneType.COLLECTION);
    }
    public void SetOnActionPlay(ActionEvent e){
        DatabaseManager db = DatabaseManager.getInstance();
        GameManager gm = GameManager.getInstance();
        int user = gm.getUserId();
        List<Integer> attacks = db.getAttacksToDestination(user);
        if(attacks.isEmpty()) {
            SceneManager.getInstance().navigateTo(SceneType.PLAY);
        } else {
            gm.setAttackWordID0(attacks.get(0));
            if(attacks.size()>=2) {
                gm.setAttackWordID1(attacks.get(1));
            } else {
                gm.setAttackWordID1(-1);
            }
            if(attacks.size()>=3) {
                gm.setAttackWordID2(attacks.get(2));
            } else {
                gm.setAttackWordID2(-1);
            }
            SceneManager.getInstance().navigateTo(SceneType.PLAY_ATTACKED);
        }
    }
    public void SetOnActionLeaderboard(ActionEvent e){
        SceneManager.getInstance().navigateTo(SceneType.LEADERBOARD);
    }
    public void SetOnActionLogOut(ActionEvent e){
        SceneManager.getInstance().navigateTo(SceneType.LOGIN);
    }




}
