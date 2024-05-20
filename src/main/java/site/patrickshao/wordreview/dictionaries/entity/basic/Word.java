package site.patrickshao.wordreview.dictionaries.entity.basic;

import lombok.Builder;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode
@Builder
public class Word {
    public final String name_en;
    public final String usphone; //美音
    public final String ukphone; //英音
    public final DictRecord record;
    public final Translation[] translations; //每一个词性对应一个翻译
}
