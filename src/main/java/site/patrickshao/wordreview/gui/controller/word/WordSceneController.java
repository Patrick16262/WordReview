package site.patrickshao.wordreview.gui.controller.word;

import javafx.util.Pair;
import site.patrickshao.wordreview.dictionaries.entity.basic.Translation;
import site.patrickshao.wordreview.dictionaries.entity.basic.Word;
import site.patrickshao.wordreview.user.entity.ReciteMethod;

public interface WordSceneController {
    void setEntry(Pair<Translation, ReciteMethod> entry) ;
}
