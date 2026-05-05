import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;

public class LogInController {
    public Scene buildScene() {
        URL fxmlURL = getClass().getResource("/LogIn.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlURL);
        Scene scene = null;
        try {
            Parent root = loader.load();
            scene = new Scene(root, 640, 480);
        } catch (IOException e) {
            System.err.println("LoginController buildScene failed: " + e.getMessage());
        }
        return scene;
    }



    @FXML
    private Button SignInButton;
    @FXML
    private Label LogInMessage;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public void LogInButtonOnAction(ActionEvent e) {
        if (username.getText().isBlank() || password.getText().isBlank()) {
            LogInMessage.setText("Username or password Not entered");
        } else {
            String Username = username.getText();
            String Password = password.getText();
            int userID = DatabaseManager.getInstance().loginUser(Username, Password);
            if (userID ==-1){
                LogInMessage.setText("Incorrect Username or Password");
            }else{
                GameManager.getInstance().setUser(Username);
                SceneManager.getInstance().navigateTo(SceneType.PROFILE);
            }

        }
    }
    public void SignInButtonOnAction(ActionEvent e){
        SceneManager.getInstance().navigateTo(SceneType.SIGN_UP);
    }
}
