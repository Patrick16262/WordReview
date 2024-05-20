package site.patrickshao.wordreview.gui.controller.dialog;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import site.patrickshao.wordreview.exception.EmailVertificationFailureException;
import site.patrickshao.wordreview.exception.InvalidEmailName;
import site.patrickshao.wordreview.exception.TooMuchOperationException;
import site.patrickshao.wordreview.exception.UnhandledException;
import site.patrickshao.wordreview.gui.controller.tutor.SetAccount;
import site.patrickshao.wordreview.gui.manager.ApplicationManager;
import site.patrickshao.wordreview.gui.manager.SceneManager;
import site.patrickshao.wordreview.gui.manager.TutorSceneManager;
import site.patrickshao.wordreview.user.ConfigManager;
import site.patrickshao.wordreview.user.Users;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.ConnectException;
import java.net.URL;
import java.util.ResourceBundle;

public class Register implements Initializable {
    @FXML
    private TextField email_field;
    @FXML
    private PasswordField pwd_field;
    @FXML
    private PasswordField pwd_renter;
    @FXML
    private Label response;
    @FXML
    private Button submit;
    @FXML
    private Button cancel;
    @FXML
    private Label to_login;
    @FXML
    private FontIcon user_font;
    @FXML
    private FontIcon pwd_font;

    public void trySubmit(){
        response.setText("正在注册...");
        if (!email_field.getText().matches(".{1,100}@.{1,10}[.].{1,10}")) {
            response.setText("请输入正确的邮箱");
            return;
        }
        if (!pwd_field.getText().equals(pwd_renter.getText())) {
            response.setText("两次密码输入不一致，请重新输入");
            return;
        }
        ApplicationManager.getBackgroundExecutor().execute(()-> {
            try {
                var db = Users.register(email_field.getText(), Users.encrypte(pwd_field.getText()));
                ConfigManager.setUser(db);
                Platform.runLater(SceneManager::refreshSidebar);
                Platform.runLater(()->response.setText("注册成功！"));
                Platform.runLater(() ->cancel.getOnAction().handle(new ActionEvent()));
            } catch (EmailVertificationFailureException e) {
                Platform.runLater(()->response.setText("该邮箱已注册"));
                return;
            } catch (ConnectException e) {
                Platform.runLater(()->response.setText("网络不佳"));
                return;
            } catch (InvalidEmailName e) {
                Platform.runLater(()->response.setText("请输入正确的邮箱"));
                return;
            } catch (TooMuchOperationException e) {
                Platform.runLater(()->response.setText("操作太频繁，请休息一下~"));
                return;
            } catch (UnhandledException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void setTutorMod(SetAccount setAccountController) {
        cancel.setOnAction(actionEvent -> TutorSceneManager.nextPage());
        cancel.setText("暂不登录");
        to_login.setOnMouseClicked(actionEvent -> setAccountController.toLogin());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancel.setOnAction(actionEvent -> {SceneManager.closeDialog();});
        submit.setOnAction(actionEvent -> { trySubmit();});
        to_login.setOnMouseClicked(actionEvent -> {SceneManager.showDialog(DialogType.LOGIN);});
        user_font.setIconCode(FontAwesomeSolid.USER);
        pwd_font.setIconCode(FontAwesomeSolid.LOCK);
    }
}


