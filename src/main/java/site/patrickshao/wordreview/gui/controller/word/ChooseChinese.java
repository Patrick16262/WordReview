package site.patrickshao.wordreview.gui.controller.word;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import site.patrickshao.wordreview.Main;
import site.patrickshao.wordreview.dictionaries.entity.basic.Translation;
import site.patrickshao.wordreview.dictionaries.entity.basic.Word;
import site.patrickshao.wordreview.exception.UnhandledException;
import site.patrickshao.wordreview.gui.manager.ApplicationManager;
import site.patrickshao.wordreview.gui.manager.TutorSceneManager;
import site.patrickshao.wordreview.gui.manager.WordSceneManager;
import site.patrickshao.wordreview.gui.util.FromConfig;
import site.patrickshao.wordreview.user.ConfigManager;
import site.patrickshao.wordreview.user.entity.ReciteMethod;

import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Random;
import java.util.ResourceBundle;

public class ChooseChinese implements WordSceneController, Initializable {
    @FXML
    private VBox inner_vbox;
    @FXML
    private Button choice0;
    @FXML
    private Button choice1;
    @FXML
    private Button choice2;
    @FXML
    private Button choice3;

    private int rightIndex;
    private int selectedIndex;
    private Pair<Translation, ReciteMethod> entry;

    public void choice(int choiceIndex) {
        this.selectedIndex = choiceIndex;
        setDisable();
        if (rightIndex == choiceIndex) chooseRight();
            else chooseWrong();
        WordSceneManager.markReviewed(entry.getKey().id);
    }

    public void setDisable() {
        choice0.setDisable(true);
        choice1.setDisable(true);
        choice2.setDisable(true);
        choice3.setDisable(true);
        getButtonByIndex(rightIndex).getStyleClass().remove("common-button");
        getButtonByIndex(rightIndex).getStyleClass().add(0, "common-button-right");
    }

    public void chooseWrong() {
        ApplicationManager.getBackgroundExecutor().execute(
                () -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new UnhandledException(e);
                    }
                    Platform.runLater(() -> WordSceneManager.showDict(entry));
                }
        );
        getButtonByIndex(selectedIndex).getStyleClass().remove("common-button");
        getButtonByIndex(selectedIndex).getStyleClass().add(0, "common-button-wrong");
        try {
            WordSceneManager.loadPlanEntry(entry);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void chooseRight() {
        ApplicationManager.getBackgroundExecutor().execute(
                () -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new UnhandledException(e);
                    }
                    Platform.runLater(WordSceneManager::nextPage);
                }
        );
    }

    @Override
    public void setEntry(Pair<Translation, ReciteMethod> entry) {
        this.entry = entry;
        Word word = ConfigManager.getBookManager().getCurrentMyBook().getWordBook().getWords().get(entry.getKey().en_word);
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("word/word-title.fxml"));
        try {
            inner_vbox.getChildren().add(0, loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ((WordTitle)loader.getController()).setWord(word);
        var wordRandom = new SecureRandom();
        rightIndex = new Random().nextInt(4);
        getButtonByIndex(rightIndex).setText(FromConfig.getChineseExplanation(entry.getKey()));
        var trans = ConfigManager.getBookManager().getCurrentMyBook().getWordBook().getTranslations().values().toArray(Translation[]::new);
        for (int i = 0; i < 4; i ++) {
            if (i == rightIndex) continue;
            int ran = wordRandom.nextInt(trans.length);
            if (trans[ran] == entry.getKey()) ran = wordRandom.nextInt(trans.length);
            getButtonByIndex(i).setText(FromConfig.getChineseExplanation(trans[ran]));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choice0.setOnAction(event -> choice(0));
        choice1.setOnAction(event -> choice(1));
        choice2.setOnAction(event -> choice(2));
        choice3.setOnAction(event -> choice(3));
    }

    private Button getButtonByIndex(int index) {
        switch (index) {
            case 0 -> {
                return choice0;
            }
            case 1 -> {
                return choice1;
            }
            case 2 -> {
                return choice2;
            }
            case 3 -> {
                return choice3;
            }
        }
        return null;
    }
}
