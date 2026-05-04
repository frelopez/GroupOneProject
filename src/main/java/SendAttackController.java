import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;


import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

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

    private Stage stage;;

    private final String[] topUserNames = new String[3];

    private final DatabaseManager db = DatabaseManager.getInstance();


    public Scene buildScene(Stage stage) {
        this.stage = stage;
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
        lbl1.setVisible(false); lbl2.setVisible(false); lbl3.setVisible(false);
        btnSend1.setVisible(false); btnSend2.setVisible(false); btnSend3.setVisible(false);
        DatabaseManager db = DatabaseManager.getInstance();
        List<String[]> top = db.getTopUsers(3);


        if (top.size() > 0) { lbl1.setText(top.get(0)[0] + " " + top.get(0)[1]);lbl1.setVisible(true);btnSend1.setVisible(true); topUserNames[0] = top.get(0)[0]; }
        if (top.size() > 1) { lbl2.setText(top.get(1)[0] + " " + top.get(1)[1]);lbl2.setVisible(true);btnSend2.setVisible(true); topUserNames[1] = top.get(1)[0]; }
        if (top.size() > 2) { lbl3.setText(top.get(2)[0] + " " + top.get(2)[1]);lbl3.setVisible(true);btnSend3.setVisible(true); topUserNames[2] = top.get(2)[0]; }
    }



    public void sendButtonOnAction(ActionEvent actionEvent) {
        GameManager gm = GameManager.getInstance();
        if (actionEvent.getSource() == btnSend1) {
            Notifications.create()
                    .title("Attack Has Been Sent!")
                    .text("Your attack has been sent to "+topUserNames[0])
                    .darkStyle()
                    .owner(stage)
                    .position(Pos.TOP_RIGHT)
                    .showInformation();
            db.insertAttack(gm.getSendAttackWordId(), gm.getUserId(), db.getUserId(topUserNames[0]));
            db.deleteCollectedWord(gm.getUserId(), gm.getSendAttackWordId());
        }else if (actionEvent.getSource() == btnSend2) {
            Notifications.create()
                    .title("Attack Has Been Sent!")
                    .text("Your attack has been sent to "+topUserNames[1])
                    .darkStyle()
                    .owner(stage)
                    .position(Pos.TOP_RIGHT)
                    .showInformation();
            db.insertAttack(gm.getSendAttackWordId(), gm.getUserId(), db.getUserId(topUserNames[1]));
            db.deleteCollectedWord(gm.getUserId(), gm.getSendAttackWordId());
        }else if (actionEvent.getSource() == btnSend3) {
            Notifications.create()
                    .title("Attack Has Been Sent!")
                    .text("Your attack has been sent to "+topUserNames[2])
                    .darkStyle()
                    .owner(stage)
                    .position(Pos.TOP_RIGHT)
                    .showInformation();
            db.insertAttack(gm.getSendAttackWordId(), gm.getUserId(), db.getUserId(topUserNames[2]));
            db.deleteCollectedWord(gm.getUserId(), gm.getSendAttackWordId());
        }
        SceneManager.getInstance().navigateTo(SceneType.MAIN);
    }
}
