package net.patrickshao.wordreview.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import net.patrickshao.wordreview.manager.SceneManager;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class SideBar implements Initializable {
    @FXML
    private FontIcon house_font;
    @FXML
    private FontIcon calen_font;
    @FXML
    private FontIcon book_font;
    @FXML
    private FontIcon dict_font;
    @FXML
    private FontIcon info_font;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        house_font.setIconCode(FontAwesomeSolid.WAREHOUSE);
        calen_font.setIconCode(FontAwesomeSolid.CALENDAR_ALT);
        book_font.setIconCode(FontAwesomeSolid.BOOK);
        dict_font.setIconCode(FontAwesomeSolid.BOOK_OPEN);
        info_font.setIconCode(FontAwesomeSolid.INFO);
    }

}
