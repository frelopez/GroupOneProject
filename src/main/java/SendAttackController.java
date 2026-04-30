import javafx.fxml.FXML;

import java.awt.*;
import java.awt.event.ActionEvent;

public class SendAttackController {
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
