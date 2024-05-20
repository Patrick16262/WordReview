package site.patrickshao.wordreview.dictionaries.entity.basic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Builder@AllArgsConstructor
public class SimpleWord {
    public final PartOfSpeech pos;
    public String en_name; //英语
    public String tran; //翻译
}
