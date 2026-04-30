import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class SendAttackController implements Initializable {
    @FXML
    private Button btnSend1;
    @FXML
    private Button btnSend2;
    @FXML
    private Button btnSend3;
    @FXML
    private Label lbl1;
    @FXML
    private Label lbl2;
    @FXML
    private Label lbl3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void SendAttackBuild(Stage stage){
        DatabaseManager db = DatabaseManager.getInstance();

        lbl1.setText("Freddie "+db.getUserScore(db.getUserId("Freddie")));
        lbl2.setText("Angel "+db.getUserScore(db.getUserId("Angel")));
        lbl3.setText("Dennis "+db.getUserScore(db.getUserId("Dennis")));
    }

    public void sendButtonOnAction(ActionEvent actionEvent) {
        //TODO: Connect method to send word to user.
        if (actionEvent.getSource() == btnSend1) {
            //sendAttack(lbl1);
        }else if (actionEvent.getSource() == btnSend2) {
            //sendAttack(lbl2);
        }else if (actionEvent.getSource() == btnSend3) {
           // sendAttack(lbl3);
        }else {
            SceneManager.getInstance().navigateTo(SceneType.PLAY_ATTACKED);
        }
    }
}
