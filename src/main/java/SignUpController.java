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
    private TextField Username;
    @FXML
    private PasswordField Password;
    @FXML
    private PasswordField Repassword;
    @FXML
    private Label label;

    public void SignUpButtonOnAction(ActionEvent e) {
        if (Username.getText().isBlank() || Password.getText().isBlank()) {
            label.setText("Check usernane or password");
        } else {
            String username = Username.getText();
            String password = Password.getText();
            String repassword = Repassword.getText();
            if (password.equals(repassword)) {
                int createUser = DatabaseManager.getInstance().insertUser(username, password);
                if (createUser == -1) {
                    label.setText("User already exist");
                } else {
                    GameManager.getInstance().setUser(username);
                    SceneManager.getInstance().navigateTo(SceneType.PROFILE);
                }
            }else{
                label.setText("passwords do not match");
            }
        }
    }
}
