package site.patrickshao.wordreview.gui.controller.word;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import site.patrickshao.wordreview.Main;
import site.patrickshao.wordreview.dictionaries.entity.basic.Translation;
import site.patrickshao.wordreview.dictionaries.entity.basic.Word;
import site.patrickshao.wordreview.gui.manager.WordSceneManager;
import site.patrickshao.wordreview.gui.util.FromConfig;
import site.patrickshao.wordreview.user.ConfigManager;
import site.patrickshao.wordreview.user.entity.ReciteMethod;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReadLearn implements WordSceneController, Initializable {
    @FXML
    private Button too_easy;
    @FXML
    private Button need_review;
    @FXML
    private Button not_recognize;
    @FXML
    private Button memorized;
    @FXML
    private Label header_label;
    @FXML
    private Label meaning_label;
    @FXML
    private VBox vbox_pane;
    private Pair<Translation, ReciteMethod> entry;
    @Override
    public void setEntry(Pair<Translation, ReciteMethod> entry) {
        this.entry = entry;
        Word word = ConfigManager.getBookManager().getCurrentMyBook().getWordBook().getWords().get(entry.getKey().en_word);
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("word/word-title.fxml"));
        try {
            vbox_pane.getChildren().add(1, loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ((WordTitle)loader.getController()).setWord(word);
        meaning_label.setText(FromConfig.getChineseExplanation(entry.getKey()));
        meaning_label.setOpacity(0);
    }

    public void memorize() {
        meaning_label.setOpacity(1);
        header_label.setText("请记住单词释义");
        too_easy.setDisable(true);
        need_review.setDisable(true);
        not_recognize.setDisable(true);
        too_easy.setOpacity(0);
        need_review.setOpacity(0);
        not_recognize.setOpacity(0);
        memorized.setOpacity(1);
        memorized.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        too_easy.setOnAction(event -> {
            ConfigManager.getBookManager().getCurrentMyBook().addLearned(entry.getKey().id);
            ConfigManager.getBookManager().getCurrentMyBook().markTooEasy(entry.getKey().id);
            WordSceneManager.nextPage();
        });
        need_review.setOnAction(event -> memorize());
        not_recognize.setOnAction(event -> memorize());
        memorized.setOnAction(event -> {
            ConfigManager.getBookManager().getCurrentMyBook().addLearned(entry.getKey().id);
            WordSceneManager.nextPage();
        });
    }
}
