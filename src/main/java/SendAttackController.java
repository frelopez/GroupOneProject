import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class SendAttackController {
    @FXML
    private Button btnSend1;
    @FXML
    private Button btnSend2;
    @FXML
    private Button btnSend3;
    @FXML
    private Button btnSend4;

    @FXML
    private Label lbl1;
    @FXML
    private Label lbl2;
    @FXML
    private Label lbl3;

    public Scene buildScene() {
        URL url = getClass().getResource("SendAttack.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Scene scene = null;
        try {
            Parent root = loader.load();
            scene = new Scene(root);
        }catch (IOException e){
            System.err.println("SendAttack buildScene failed: " + e.getMessage());
        }
        return scene;
    }

    public void initialize() {
        DatabaseManager db = DatabaseManager.getInstance();
        List<String[]> top = db.getTopUsers(3);

        lbl1.setText(top.get(0)[0] + " " + top.get(0)[1]);
        lbl2.setText(top.get(1)[0] + " " + top.get(1)[1]);
        lbl3.setText(top.get(2)[0] + " " + top.get(2)[1]);
    }

    public void sendButtonOnAction(ActionEvent actionEvent) {
        //TODO: Connect method to send word to user.
        if (actionEvent.getSource() == btnSend1) {
            //sendAttack(lbl1);
        }else if (actionEvent.getSource() == btnSend2) {
            //sendAttack(lbl2);
        }else if (actionEvent.getSource() == btnSend3) {
           // sendAttack(lbl3);
        }else if (actionEvent.getSource() == btnSend4) {
            SceneManager.getInstance().navigateTo(SceneType.COLLECTION);
        }
    }
}
