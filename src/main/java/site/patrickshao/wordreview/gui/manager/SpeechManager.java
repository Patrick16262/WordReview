package site.patrickshao.wordreview.gui.manager;

import javafx.scene.media.Media;
import site.patrickshao.wordreview.constants.Constants;
import site.patrickshao.wordreview.user.entity.SpeechType;

public class SpeechManager {

    public Media getSpeech(String word, SpeechType speechType) {
        String mediaPath = null;
        switch (speechType) {
            case UK -> {
                mediaPath = Constants.Folders.ukSpeech;
            }
            case US -> {
                mediaPath = Constants.Folders.usSpeech;
            }
        }
        return new Media(mediaPath + word + ".mp3");
    }
}
