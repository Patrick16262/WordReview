package site.patrickshao.wordreview.dictionaries.util;

import com.google.gson.Gson;
import lombok.Getter;
import site.patrickshao.wordreview.constants.Constants;
import site.patrickshao.wordreview.dictionaries.entity.basic.Frequency;
import site.patrickshao.wordreview.dictionaries.entity.basic.PartOfSpeech;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;

public class Words {
    @Getter
    private static HashMap<Excels.PosWordPair, Frequency> frequencyMap;

    public static int found;
    public static int not_found;

    public static void loadFrequencyMap()throws IOException {
        if (frequencyMap != null)
            return;
        frequencyMap = new HashMap<>();
        Excels.Info[] entries = new Gson().fromJson(Files.newBufferedReader(Path.of(Constants.Files.frenquency_json)), Excels.Info[].class);
        Arrays.stream(entries).forEach(entry -> {
            frequencyMap.put(entry.pair, entry.frequency);
        });
    }

    public static Frequency findFrenquency(String name, PartOfSpeech pos) {
        if (pos == null) {
            for (var c : PartOfSpeech.values()) {
                Frequency fre = frequencyMap.get(new Excels.PosWordPair(name, c));
                if (fre != null) {
                    found ++;
                    return fre;
                }
            }
        }
        Frequency fre = frequencyMap.get(new Excels.PosWordPair(name, pos));
        if (fre == null) {
            not_found ++;
//            System.out.println(name + " " + Texts.fromEnum(pos) + " not found frenquency!");
        } else {
            found ++;
        }
        return fre;
    }
}
