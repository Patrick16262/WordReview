package site.patrickshao.wordreview.gui.util;

import site.patrickshao.wordreview.dictionaries.entity.basic.Translation;
import site.patrickshao.wordreview.dictionaries.entity.basic.Word;
import site.patrickshao.wordreview.dictionaries.util.Texts;
import site.patrickshao.wordreview.user.ConfigManager;

public class FromConfig {
    public static String getChineseExplanation(Translation translation) {
        String str = null;
        switch (ConfigManager.getConfig().tranStyle) {
            case single_trans -> {
                str = Texts.TransToString(translation);
            }
            case single_meaning -> {
                str = Texts.MeaningToString(translation.meanings[0]);
            }
            case single_syno -> {
                str = Texts.chineseSynoToString(translation.pos, translation.meanings[0].ch_syno.ch_names[0]);
            }
        }
        return str;
    }

    public static String getSpeechText(Word word) {
        switch (ConfigManager.getConfig().speechType) {
            case UK -> {
                return word.ukphone;
            }
            case US -> {
                return word.usphone;
            }
        }
        return null;
    }
}
