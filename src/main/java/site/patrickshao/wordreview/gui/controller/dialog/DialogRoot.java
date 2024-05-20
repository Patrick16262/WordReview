package site.patrickshao.wordreview.gui.controller.dialog;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class DialogRoot {

    @FXML
    private StackPane shadow_pane;
    @FXML
    private AnchorPane root_pane;
    @FXML
    private StackPane dialog_content;


    public void setContent(Node node) {
        dialog_content.getChildren().clear();
        dialog_content.getChildren().add(node);
    }
}
