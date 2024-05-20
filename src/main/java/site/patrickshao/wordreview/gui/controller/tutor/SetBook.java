package site.patrickshao.wordreview.gui.controller.tutor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import site.patrickshao.wordreview.Main;
import site.patrickshao.wordreview.exception.UnhandledException;
import site.patrickshao.wordreview.gui.controller.BookView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SetBook implements Initializable {
    @FXML
    private VBox vbox_pane;
    @FXML
    private BookView bookViewController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("book-view.fxml"));
            Pane pane = loader.load();
            bookViewController = loader.getController();
            bookViewController.setTutorMod(true);
            pane.setMaxHeight(480);
            pane.setMaxWidth(600);
            vbox_pane.getChildren().add(pane);
        } catch (IOException e) {
            throw new UnhandledException(e);
        }
    }
}
