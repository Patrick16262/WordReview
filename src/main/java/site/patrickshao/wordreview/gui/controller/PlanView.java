package site.patrickshao.wordreview.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import site.patrickshao.wordreview.exception.UnhandledException;
import site.patrickshao.wordreview.gui.manager.SceneManager;
import site.patrickshao.wordreview.user.ConfigManager;
import site.patrickshao.wordreview.user.entity.TranStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class PlanView implements Initializable {
    @FXML
    private RadioButton explain_simple;
    @FXML
    private RadioButton explain_normal;
    @FXML
    private RadioButton explain_all;
    @FXML
    private TextField word_num;
    private ToggleGroup explainToggle;
    public void refresh() {
        switch (ConfigManager.getConfig().tranStyle) {
            case single_trans -> {
                explainToggle.selectToggle(explain_all);
            }
            case single_meaning -> {
                explainToggle.selectToggle(explain_normal);
            }
            case single_syno -> {
                explainToggle.selectToggle(explain_simple);
            }
        }
        word_num.setText(String.valueOf(ConfigManager.getConfig().word_num_per_day));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        explainToggle = new ToggleGroup();
        explainToggle.getToggles().addAll(explain_simple, explain_normal, explain_all);
        explainToggle.selectedToggleProperty().addListener(observable -> {
            Toggle toggle =explainToggle.selectedToggleProperty().get();
            if (toggle == explain_all) {
                ConfigManager.getConfig().tranStyle = TranStyle.single_trans;
            } else if (toggle == explain_normal) {
                ConfigManager.getConfig().tranStyle = TranStyle.single_meaning;
            } else if (toggle == explain_simple) {
                ConfigManager.getConfig().tranStyle = TranStyle.single_syno;
            }
        });

        word_num.textProperty().addListener(invalidate -> {
            try {
                int num = Integer.parseInt(word_num.getText());
                if (num >=100 || num <= 0) throw new Exception();
                ConfigManager.getConfig().word_num_per_day = num;
                SceneManager.refreshHomeView();
            } catch (Exception e) {
                word_num.setText(String.valueOf(ConfigManager.getConfig().word_num_per_day));
            }
        });
    }
}
