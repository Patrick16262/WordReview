package net.patrickshao.wordreview.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import net.patrickshao.wordreview.manager.ApplicationManager;
import net.patrickshao.wordreview.manager.SceneManager;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class RootPane implements Initializable {
    @FXML
    private Button close;
    @FXML
    private Button mini;
    @FXML
    private Button menu;
    @FXML
    private FontIcon close_font;
    @FXML
    private FontIcon mini_font;
    @FXML
    private FontIcon menu_font;
    @FXML
    @Getter
    private AnchorPane root_pane;
    @FXML
    private AnchorPane shadow_pane;
    @FXML
    @Getter
    private AnchorPane main_pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        close.setOnAction(e -> {
            ApplicationManager.hide();
        });
        mini.setOnAction(e -> {
            ApplicationManager.minimize();
        });
        close_font.setIconCode(FontAwesomeSolid.TIMES);
        mini_font.setIconCode(FontAwesomeSolid.WINDOW_MINIMIZE);
        menu_font.setIconCode(FontAwesomeSolid.LIST);
        menu.setOnAction(e -> {
            if (SceneManager.isSidebarShown()) SceneManager.hideSideBar();
            else SceneManager.showSideBar();
        });
        clip();
    }

    private void clip() {
        var clipper = new Rectangle(800, 600);
        clipper.setStrokeWidth(0);
        clipper.setLayoutX(0);
        clipper.setLayoutY(0);
        clipper.setArcWidth(20);
        clipper.setArcHeight(20);
        root_pane.setClip(clipper);
    }
}
