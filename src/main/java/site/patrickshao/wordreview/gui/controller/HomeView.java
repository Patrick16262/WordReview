package site.patrickshao.wordreview.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import site.patrickshao.wordreview.gui.controller.dialog.ConfigBook;
import site.patrickshao.wordreview.gui.controller.dialog.DialogType;
import site.patrickshao.wordreview.gui.manager.ImageManager;
import site.patrickshao.wordreview.gui.manager.SceneManager;
import site.patrickshao.wordreview.gui.manager.WordSceneManager;
import site.patrickshao.wordreview.user.ConfigManager;
import site.patrickshao.wordreview.user.MyBook;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeView implements Initializable {
    @FXML
    ImageView word_book_image;
    @FXML
    ProgressBar progress_bar;
    @FXML
    Label recited_num;
    @FXML
    Label total_num;
    @FXML
    Label remaining_days;
    @FXML
    Label book_title;
    @FXML
    Button main_btn;
    @FXML
    Button learn_btn;
    @FXML
    Button review_btn;
    @FXML
    Label config_plan;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
        config_plan.setOnMouseClicked(event -> SceneManager.setPage(1));
            learn_btn.setOnAction(event -> WordSceneManager.startLearn());
            review_btn.setOnAction(event -> WordSceneManager.startReview());
            main_btn.setOnAction(event -> WordSceneManager.start());
    }

    public void refresh() {
        MyBook myBook = ConfigManager.getBookManager().getCurrentMyBook();
        if (myBook == null) {
            word_book_image.setImage(ImageManager.getImage("add_new"));
            book_title.setText("点我设置词书");
            total_num.setText("0");
            recited_num.setText("0");
            progress_bar.setProgress(0);
            remaining_days.setText("0");
            book_title.setOnMouseClicked(event -> SceneManager.setPage(2));
            word_book_image.setOnMouseClicked(event -> SceneManager.setPage(2));
            return;
        }
        book_title.setOnMouseClicked(event ->((ConfigBook) SceneManager.showDialog(DialogType.CREATE_BOOK)).setBookInfo(ConfigManager.getBookManager().getCurrentMyBook().getWordBook()));
        word_book_image.setOnMouseClicked(event -> book_title.getOnMouseClicked().handle(event));
        word_book_image.setImage(ImageManager.getImage(myBook.getBook_id()));
        book_title.setText(myBook.getWordBook().getTitle());
        total_num.setText(String.valueOf(myBook.getWordBook().getTranslations().size()));
        recited_num.setText(String.valueOf(myBook.getLearnedTrans().size()));
        progress_bar.setProgress((double) myBook.getLearnedTrans().size() / myBook.getWordBook().getTranslations().size());
        remaining_days.setText(String.valueOf(myBook.getWordBook().getTranslations().size() / ConfigManager.getConfig().word_num_per_day  + 1));
    }
}
