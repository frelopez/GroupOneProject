import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;

public class SignUpController {
    public Scene buildScene(){
        URL fxmlURL = getClass().getResource("/SignUp.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlURL);
        Scene scene = null;
        try{
            Parent root = loader.load();
            scene = new Scene(root, 640, 480);
        }catch (IOException e){
            System.err.println("SignUpcontroller failed: " + e.getMessage());
        }
        return scene;
    }
    @FXML
    private Button SignUpButton;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField repassword;

    public void SignUpButtonOnAction(ActionEvent e) {

    }
}
