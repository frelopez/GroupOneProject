import javafx.scene.control.Label;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
        SceneManager.init(stage);
        Parent root = FXMLLoader.load(getClass().getResource("/SignUp.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
    @BeforeEach
    public void tearDown() {
        int id = DatabaseManager.getInstance().getUserId("testuser");
        DatabaseManager.getInstance().deleteUser(id);
    }
    @Test
    public void testBothFieldsFilledNoError() {
        clickOn("#Username").write("testuser");
        clickOn("#Password").write("testpass");
        clickOn("#Repassword").write("testpas");
        clickOn("#SignUpButton");

        Label msg = lookup("#label").query();
        assertEquals("passwords do not match", msg.getText());
    }

    @Test
    public void checkdbforuser(){
        clickOn("#Username").write("testuser");
        clickOn("#Password").write("testpass");
        clickOn("#Repassword").write("testpass");
        clickOn("#SignUpButton");

        String user = GameManager.getInstance().getUser();
        assertEquals("testuser", user);
    }
}

