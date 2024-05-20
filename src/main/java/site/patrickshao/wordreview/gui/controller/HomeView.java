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
import site.patrickshao.wordreview.user.ConfigManager;
import site.patrickshao.wordreview.user.MyBook;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeView implements Initializable {
    @FXML
    private ImageView word_book_image;
    @FXML
    private ProgressBar progress_bar;
    @FXML
    private Label recited_num;
    @FXML
    private Label total_num;
    @FXML
    private Label remaining_days;
    @FXML
    private Label book_title;
    @FXML
    private Button main_btn;
    @FXML
    private Button learn_btn;
    @FXML
    private Button review_btn;
    @FXML
    private Label config_plan;
    @FXML
    private Label need_review;
    @FXML
    private Label reviewed;
    @FXML
    private Label need_learn;
    @FXML
    private Label learned;
    @FXML
    private Label time_need;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
        config_plan.setOnMouseClicked(event -> SceneManager.setPage(1));
            learn_btn.setOnAction(event -> SceneManager.startReciting(ConfigManager.getBookManager().getLearnPlanGenerator()));
            review_btn.setOnAction(event -> SceneManager.startReciting(ConfigManager.getBookManager().getReviewPlanGenerator()));
            main_btn.setOnAction(event -> SceneManager.startReciting(ConfigManager.getBookManager().getRecitePlanGenerator()));
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
            main_btn.setDisable(true);
            learn_btn.setDisable(true);
            review_btn.setDisable(true);
            time_need.setText("0");
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

        need_learn.setText(String.valueOf(ConfigManager.getBookManager().getNeedLearnNumToday() + ConfigManager.getBookManager().getLearnedNumToday()));
        need_review.setText(String.valueOf(ConfigManager.getBookManager().getNeedReviewNumToday() + ConfigManager.getBookManager().getReviewedNumToday()));
        reviewed.setText(String.valueOf(ConfigManager.getBookManager().getReviewedNumToday()));
        learned.setText(String.valueOf(ConfigManager.getBookManager().getLearnedNumToday()));
        if (ConfigManager.getBookManager().getNeedLearnNumToday() == 0) learn_btn.setDisable(true);
        else learn_btn.setDisable(false);
        if (ConfigManager.getBookManager().getNeedReviewNumToday() == 0) review_btn.setDisable(true);
        else review_btn.setDisable(false);
        if (learn_btn.isDisabled() && review_btn.isDisabled()) main_btn.setDisable(true);
        else main_btn.setDisable(false);
        if (main_btn.isDisabled()) time_need.setText("0");
        else time_need.setText(String.valueOf((ConfigManager.getBookManager().getNeedReviewNumToday() + ConfigManager.getBookManager().getNeedLearnNumToday())/ 2 + 1));
    }


}
