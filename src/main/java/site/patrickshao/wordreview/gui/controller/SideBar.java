package site.patrickshao.wordreview.gui.controller;

import com.leewyatt.rxcontrols.controls.RXLineButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import site.patrickshao.wordreview.gui.controller.dialog.DialogType;
import site.patrickshao.wordreview.gui.manager.ImageManager;
import site.patrickshao.wordreview.gui.manager.SceneManager;
import site.patrickshao.wordreview.user.ConfigManager;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
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
    @FXML
    private RXLineButton _0;
    @FXML
    private RXLineButton _1;
    @FXML
    private RXLineButton _2;
    @FXML
    private RXLineButton _3;
    @FXML
    private RXLineButton _4;

    @FXML
    private Label user_name;
    @FXML
    private Label sync_text;
    @FXML
    private ImageView userImage;

    public void refreash() {
        if (ConfigManager.getBasicConfig().onlineAccont) {
            userImage.setImage(ImageManager.getImage("default"));
            user_name.setText("点我同步数据");
        } else {
            userImage.setImage(ImageManager.getImage("default"));
            user_name.setText("登录同步数据");
        }
    }

    public void coverWordPattern() {

    }

    public void removeWordPattern() {

    }

    public void setSyncTime(LocalDateTime time) {
        var dur =Duration.between(time, LocalDateTime.now());
        if (dur.getSeconds() < 90) {
            sync_text.setText("1分钟前同步");
        } else if (dur.toMinutes() < 60) {
            sync_text.setText(dur.toMinutes() + "分钟前同步");
        } else if (dur.toHours()< 24) {
            sync_text.setText(dur.toHours() + "小时前同步");
        } else if (dur.toDays() < 100) {
            sync_text.setText(dur.toDays() + "日前同步");
        } else {
            sync_text.setText("上次同步: 很久前");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreash();
        user_name.setOnMouseClicked(mouseEvent -> {
            if (ConfigManager.getBasicConfig().onlineAccont) {

            } else  {
                SceneManager.showDialog(DialogType.LOGIN);
            }
        });
        user_name.setOnMouseClicked(mouseEvent -> {
            if (ConfigManager.getBasicConfig().onlineAccont) {

            } else  {
                SceneManager.showDialog(DialogType.LOGIN);
            }
        });
        house_font.setIconCode(FontAwesomeSolid.WAREHOUSE);
        calen_font.setIconCode(FontAwesomeSolid.CALENDAR_ALT);
        book_font.setIconCode(FontAwesomeSolid.BOOK);
        dict_font.setIconCode(FontAwesomeSolid.BOOK_OPEN);
        info_font.setIconCode(FontAwesomeSolid.INFO);
        _0.setOnAction(actionEvent -> {SceneManager.setPage(0);});
        _1.setOnAction(actionEvent -> {SceneManager.setPage(1);});
        _2.setOnAction(actionEvent -> {SceneManager.setPage(2);});
//        _3.setOnAction(actionEvent -> {SceneManager.setPage(3);});
        _4.setOnAction(actionEvent -> {SceneManager.setPage(4);});
    }

}
