package site.patrickshao.wordreview.dictionaries.entity.basic;

import lombok.Builder;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode
@Builder
public class BookWord {
    public final Word word;
    public SimpleWord[] relWord;
    public SimpleWord[] synoWords;
    public SimpleWord[] phrases;
    public Exam[] exam;
}
