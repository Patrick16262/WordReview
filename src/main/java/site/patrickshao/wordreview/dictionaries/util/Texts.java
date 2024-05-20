package site.patrickshao.wordreview.dictionaries.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import site.patrickshao.wordreview.dictionaries.entity.basic.Meaning;
import site.patrickshao.wordreview.dictionaries.entity.basic.PartOfSpeech;
import site.patrickshao.wordreview.dictionaries.entity.basic.Syno;
import site.patrickshao.wordreview.dictionaries.entity.basic.Translation;

import java.util.Arrays;
import java.util.regex.Pattern;

//其实可以直接用循环进行替代
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Texts {
    public static PartOfSpeech toEnum(String str) {
        if (str == null) return null;
        if (str.contains("&")) str = str.split("&")[0];
        //将单词的词性转换为枚举类型
        if (str.matches("[ ]*n[. ]*")) return PartOfSpeech.Noun;
        if (str.matches("[ ]*v[. ]*")) return PartOfSpeech.Verb;
        if (str.matches("[ ]*vt[. ]*")) return PartOfSpeech.Verb;
        if (str.matches("[ ]*vi[. ]*")) return PartOfSpeech.Verb;
        if (str.matches("[ ]*aux[. ]*")) return PartOfSpeech.Verb;
        if (str.matches("[ ]*adj[. ]*")) return PartOfSpeech.Adjective;
        if (str.matches("[ ]*adv[. ]*")) return PartOfSpeech.Adverb;
        if (str.matches("[ ]*art[. ]*")) return PartOfSpeech.Article;
        if (str.matches("[ ]*prep[. ]*")) return PartOfSpeech.Preposition;
        if (str.matches("[ ]*conj[. ]*")) return PartOfSpeech.Conjunction;
        if (str.matches("[ ]*pron[. ]*")) return PartOfSpeech.Pronoun;
        if (str.matches("[ ]*int[. ]*")) return PartOfSpeech.Interjection;
//        if (str.matches(".*det\\..*")) return PartOfSpeech.Determiner;
        if (str.matches(".*det\\..*")) return PartOfSpeech.Article;

        if (str.matches("[ ]*N[ ]*")) return PartOfSpeech.Noun;
        if (str.matches("[ ]*V[ ]*")) return PartOfSpeech.Verb;
        if (str.matches("[ ]*J[ ]*")) return PartOfSpeech.Adjective;
        if (str.matches("[ ]*R[ ]*")) return PartOfSpeech.Adverb;
        if (str.matches("[ ]*X[ ]*")) return PartOfSpeech.Adverb;
        if (str.matches("[ ]*A[ ]*")) return PartOfSpeech.Article;
        if (str.matches("[ ]*P[ ]*")) return PartOfSpeech.Preposition;
        if (str.matches("[ ]*I[ ]*")) return PartOfSpeech.Conjunction;
        if (str.matches("[ ]*U[ ]*")) return PartOfSpeech.Pronoun;
        if (str.matches("[ ]*M[ ]*")) return PartOfSpeech.Adjective;
//        if (str.matches("[ ]*M[ ]*")) return PartOfSpeech.Number;
        if (str.matches("[ ]*C[ ]*")) return PartOfSpeech.Conjunction;
        if (str.matches("[ ]*D[ ]*")) return PartOfSpeech.Pronoun;
//        if (str != null)
//        System.out.println(str + " not found in pos");
        return null;
    }

    public static String fromEnum(PartOfSpeech pos) {
        if (pos == null) return "";
        switch (pos) {
            case Noun -> {
                return "n.";
            }
            case Verb -> {
                return "v.";
            }
            case Adjective -> {
                return "adj.";
            }
            case Adverb -> {
                return "adv.";
            }
            case Pronoun -> {
                return "pron.";
            }
            case Article -> {
                return "art.";
            }
            case Preposition -> {
                return "prep.";
            }
            case Conjunction -> {
                return "conj.";
            }
            case Interjection -> {
                return "int.";
            }
            case Determiner -> {
                return "det.";
            }
            case Phrase -> {
                return "";
            }
        }
        return "";
    }

    public static String[] seperateTrans(String str) {
        return str.split("[ ]*[;；][ ]*");
    }

    public static Syno seperateMeaning(String str) {
        var ss = str.split("[ ]*[,，][ ]*");
        String pre = null;
        if (ss.length != 0) {
            var marcher = Pattern.compile("(<.*>)|(\\(.*\\))").matcher(ss[0]);
            if (marcher.find()) {
                pre = marcher.group(1);
            }
        }
        return new Syno(pre, ss);
    }

    //    public static String htmlFromEscape(String text) {
//        //处理html文本中需要转义的字符
//        text = text.replaceAll("&lt;", "<");
//        text = text.replaceAll("&gt;", ">");
//        text = text.replaceAll("&amp;", "&");
//        text = text.replaceAll("&quot;", "\"");
//        text = text.replaceAll("&#39;", "'");
//        text = text.replaceAll("&apos;", "'");
//        text = text.replaceAll("&nbsp;", " ");
//        text = text.replaceAll("&copy;", "©");
//        text = text.replaceAll("&reg;", "®");
//        text = text.replaceAll("&euro;", "€");
//        text = text.replaceAll("&yen;", "¥");
//        text = text.replaceAll("&pound;", "£");
//        text = text.replaceAll("&cent;", "¢");
//        return text;
//    }
    public static String baiciToEscape(String text) {
        text = text.replaceAll("[ /\\\\:*?\"<>|]", "_");
        return text;
    }

    public static String deleteWrapping(String text) {
        text = text.replaceAll("[\n\r]", text);
        return text;
    }

    public static String TransToString(Translation translation) {
        StringBuilder str = new StringBuilder();
        str.append(fromEnum(translation.pos));
        Arrays.stream(translation.meanings).forEach(
                meaning -> {
                    str.append(meaning.ch_syno.prefix == null ? "" : meaning.ch_syno.prefix);
                    for (int i = 0; i < meaning.ch_syno.ch_names.length; i++) {
                        if (i != 0) str.append(", ");
                        str.append(meaning.ch_syno.ch_names[i]);
                    }
                    str.append("; ");
                }
        );
        return str.toString();
    }

    public static String MeaningToString(Meaning meaning) {
        StringBuilder str = new StringBuilder();
        str.append(fromEnum(meaning.pos));
        str.append(meaning.ch_syno.prefix == null ? "" : meaning.ch_syno.prefix);
        for (int i = 0; i < meaning.ch_syno.ch_names.length; i++) {
            if (i != 0) str.append(", ");
            str.append(meaning.ch_syno.ch_names[i]);
        }
        return str.toString();
    }

    public static String chineseSynoToString(PartOfSpeech pos, String prefix, String syno) {
        StringBuilder str = new StringBuilder();
        str.append(fromEnum(pos)).append(prefix == null ? "" : prefix).append(syno == null ? "" : syno);
        return str.toString();
    }
}
