import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogInController {
    @FXML
    private Button SignInButton;
    @FXML
    private Label SignInMessage;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public void signInButtonOnAction(ActionEvent e) {


        if (username.getText().isBlank() || password.getText().isBlank()) {
            SignInMessage.setText("Failed to Sign Up");
        } else {
            SignInMessage.setText("You are signed Up");
        }
    }
}
