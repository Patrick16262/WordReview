package net.patrickshao.wordreview.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import net.patrickshao.wordreview.manager.ImageManager;

import java.net.URL;
import java.util.ResourceBundle;

public class MainView implements Initializable {
    @FXML
    ImageView word_book_image;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        word_book_image.setImage(ImageManager.getImage("wordbook-cet4"));
        word_book_image.setSmooth(true);
    }
}
