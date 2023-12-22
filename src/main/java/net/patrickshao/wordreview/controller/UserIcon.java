package net.patrickshao.wordreview.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.patrickshao.wordreview.manager.ImageManager;

import java.net.URL;
import java.util.List;
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
