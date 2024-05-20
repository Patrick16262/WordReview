package site.patrickshao.wordreview.dictionaries.entity.basic;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Builder
public class Translation {
    public final String id;
    public final String en_word;
    public final PartOfSpeech pos;
    public final Meaning[] meanings;
    public final String trans_en; //英文释义
    public final Frequency frequency;
    public final String dict;
}
