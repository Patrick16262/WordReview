package site.patrickshao.wordreview.gui.controller.tutor;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import site.patrickshao.wordreview.gui.manager.ImageManager;
import site.patrickshao.wordreview.gui.manager.SceneManager;
import site.patrickshao.wordreview.gui.manager.TutorSceneManager;
import site.patrickshao.wordreview.user.ConfigManager;
import site.patrickshao.wordreview.user.entity.TranStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class SetTran implements Initializable {
    @FXML
    private Button simple_btn;
    @FXML
    private Button normal_btn;
    @FXML
    private Button total_btn;
    @FXML
    private ImageView simple;
    @FXML
    private ImageView normal;
    @FXML
    private ImageView total;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        simple_btn.setOnAction(event -> {
            ConfigManager.getConfig().tranStyle = TranStyle.single_syno;
            TutorSceneManager.nextPage();
        });
        normal_btn.setOnAction(event -> {
            ConfigManager.getConfig().tranStyle = TranStyle.single_meaning;
            TutorSceneManager.nextPage();
        });
        total_btn.setOnAction(event -> {
            ConfigManager.getConfig().tranStyle = TranStyle.single_trans;
            TutorSceneManager.nextPage();
        });
        SceneManager.refreshConfigView();
        simple.setImage(ImageManager.getImage("simple_example"));
        normal.setImage(ImageManager.getImage("normal_example"));
        total.setImage(ImageManager.getImage("total_example"));
    }
}
