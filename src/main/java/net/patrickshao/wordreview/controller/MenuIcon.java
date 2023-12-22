package net.patrickshao.wordreview.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import net.patrickshao.wordreview.manager.BarSceneManager;
import net.patrickshao.wordreview.manager.ImageManager;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuIcon implements Initializable {
    @FXML
    private ImageView button_img;
    @FXML
    private Rectangle button_rec;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_img.setImage(ImageManager.getImage("menu"));
        button_img.setOnMousePressed(event -> {
            button_rec.setStyle("-fx-fill: #0E3692;");
        });
        button_img.setOnMouseReleased(event -> {
            button_rec.setStyle("-fx-fill: #0f4ad3;");
            BarSceneManager.showSideBar();
        });
    }
}
