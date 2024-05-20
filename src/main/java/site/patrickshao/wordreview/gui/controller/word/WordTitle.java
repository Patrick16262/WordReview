package site.patrickshao.wordreview.gui.controller.word;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import site.patrickshao.wordreview.dictionaries.entity.basic.Word;
import site.patrickshao.wordreview.gui.util.FromConfig;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class WordTitle implements Initializable {
    @FXML
    private FontIcon speech;
    @FXML
    private Label word_title;
    private Word word;

    public void setWord(Word word) {
        speech.setText(FromConfig.getSpeechText(word));
        word_title.setText(word.name_en);
        this.word = word;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        speech.setIconCode(FontAwesomeSolid.AUDIO_DESCRIPTION);
    }
}
