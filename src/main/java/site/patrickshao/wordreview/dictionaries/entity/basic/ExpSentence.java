package site.patrickshao.wordreview.dictionaries.entity.basic;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Builder
public class ExpSentence {
    public final String en;
    public final String ch;
}
