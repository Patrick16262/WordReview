package site.patrickshao.wordreview.gui.controller.word;

import site.patrickshao.wordreview.dictionaries.entity.basic.Translation;
import site.patrickshao.wordreview.dictionaries.entity.basic.Word;

public interface WordSceneController {
    abstract public void setWord(Word word);
    abstract public void setTranslation(Translation translation);
}
