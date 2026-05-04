import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SendAttackTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/SendAttack.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void TestText(){
        Label nameLabel = lookup("#nameLabel").queryAs(Label.class);
        assertEquals("Expected Name", nameLabel.getText());
    }
}
