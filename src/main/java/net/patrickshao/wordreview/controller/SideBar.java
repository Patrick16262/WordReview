package net.patrickshao.wordreview.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import net.patrickshao.wordreview.manager.BarSceneManager;

import java.net.URL;
import java.nio.Buffer;
import java.util.ResourceBundle;

public class SideBar implements Initializable {
    @FXML
    private Button hide_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hide_button.setOnAction(event -> {BarSceneManager.hideSideBar();});
    }
}
