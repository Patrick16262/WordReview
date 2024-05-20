package site.patrickshao.wordreview.gui.controller.dialog;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import site.patrickshao.wordreview.gui.manager.SceneManager;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class DialogCloseBtn implements Initializable {
    @FXML
    private Button close_button;
    @FXML
    private FontIcon close_font;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        close_button.setOnAction(actionEvent -> {SceneManager.closeDialog();});
        close_font.setIconCode(FontAwesomeSolid.TIMES);
    }
}
