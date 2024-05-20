package site.patrickshao.wordreview.gui.controller.word;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Setter;
import site.patrickshao.wordreview.gui.manager.SceneManager;
import site.patrickshao.wordreview.gui.manager.WordSceneManager;

import java.net.URL;
import java.util.ResourceBundle;

public class WordLabel implements Initializable {
    @FXML
    private Label content;
    @FXML
    private HBox label_pane;
    @Setter
    private int index;
    public void setName(String name) {
        content.setText(name);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label_pane.setOnMouseClicked(event -> WordSceneManager.setPage(index));
    }
}
