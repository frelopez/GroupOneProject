import javafx.scene.control.Label;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import static org.junit.jupiter.api.Assertions.*;

/**
 * LoginTest.java
 * tests user log in scene
 * @author Angel Corrales
 * @version 0.1.0
 * @since 4/26/26
 */

public class LoginTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testBothFieldsFilledNoError() {
        clickOn("#username").write("testuser");
        clickOn("#password").write("testpass");
        clickOn("#SignInButton");

        Label msg = lookup("#SignInMessage").query();
        assertEquals("You are signed Up", msg.getText());
    }
}

