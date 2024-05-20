package site.patrickshao.wordreview.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Config {
    public TranStyle tranStyle;
    public WordConfig allWordConfig;
    public WordConfig essentialWord;
    public WordConfig importantWord;
    public WordConfig unimportantWord;
    public int word_num_per_day;
    public SpeechType speechType;

    public boolean differsImportance;

    @EqualsAndHashCode
    @AllArgsConstructor
    @Builder
    public static class WordConfig {
        public boolean procrastinate;
        public int[] reviewDays;
        public NewWordMethod newWordMethod;
        public LearnMethod[] learnMethods;
        public LearnMethod[] reviewMethods;
    }
}
