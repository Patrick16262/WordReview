package site.patrickshao.wordreview.dictionaries.entity.basic;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Builder
public class Exam {
    public final String question;
    public final String explain;
    public final int rightIndex;
    public final int examType;
    public final Choice[] choices;
    @Data
    private class Choice {
        public final int choiceIndex;
        public final String choice;
    }
}