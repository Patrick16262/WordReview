package site.patrickshao.wordreview.gui.controller.tutor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import site.patrickshao.wordreview.Main;
import site.patrickshao.wordreview.gui.controller.dialog.Login;
import site.patrickshao.wordreview.gui.controller.dialog.Register;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SetAccount implements Initializable {
    @FXML
    private VBox locator;
    private Pane registerPane;
    private Pane loginPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader1 = new FXMLLoader(Main.class.getResource("dialog/login.fxml"));
        FXMLLoader loader2 = new FXMLLoader(Main.class.getResource("dialog/register.fxml"));
        try {
            loginPane = loader1.load();
            registerPane = loader2.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Login login =  loader1.getController();
        Register register = loader2.getController();
        login.setTutorMod(this);
        register.setTutorMod(this);
        toLogin();
    }
    public void toLogin() {
        locator.getChildren().remove(registerPane);
        locator.getChildren().add(loginPane);
    }
    public void toRegister() {
        locator.getChildren().remove(loginPane);
        locator.getChildren().add(registerPane);
    }
}
