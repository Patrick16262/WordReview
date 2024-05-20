package site.patrickshao.wordreview.gui.controller.word;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class WordSceneRoot implements Initializable {
    @FXML
    private FontIcon pre_ico;
    @FXML
    private FontIcon next_ico;
    @FXML
    private Button pre_btn;
    @FXML
    private Button next_btn;


    public void setPreDisable(boolean b) {
        pre_btn.setDisable(b);
    }
    public void setNextDisable(boolean b) {
        next_btn.setDisable(b);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pre_ico.setIconCode(FontAwesomeSolid.ARROW_LEFT);
        next_ico.setIconCode(FontAwesomeSolid.ARROW_RIGHT);
    }
}
