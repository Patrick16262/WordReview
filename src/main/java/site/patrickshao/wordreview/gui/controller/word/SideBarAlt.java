package site.patrickshao.wordreview.gui.controller.word;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import site.patrickshao.wordreview.gui.controller.Book;
import site.patrickshao.wordreview.gui.manager.SceneManager;
import site.patrickshao.wordreview.gui.manager.WordSceneManager;

import java.net.URL;
import java.util.ResourceBundle;

@Getter
public class SideBarAlt implements Initializable {
    @FXML
    private VBox words_pane;
    @FXML
    private Button exit_btn;
    @FXML
    private Label remeaning_num;
    @FXML
    private Label remaining_minus;
    public void addWordNode(Node node) {
        words_pane.getChildren().add(node);
    }
    public void refreshButtom() {
        remeaning_num.setText(String.valueOf(WordSceneManager.getPageNum() - WordSceneManager.getThisPage()));
        remaining_minus.setText(String.valueOf((WordSceneManager.getPageNum() - WordSceneManager.getThisPage())/10 + 1));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exit_btn.setOnAction(event -> WordSceneManager.close());
    }
}
