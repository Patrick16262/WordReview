package site.patrickshao.wordreview.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import site.patrickshao.wordreview.gui.manager.ImageManager;

import java.net.URL;
import java.util.ResourceBundle;

public class UserIcon implements Initializable {
    @FXML
    Label label;
    @FXML
    ImageView image;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        image.setImage(ImageManager.getImage("user-connected"));
    }
}
