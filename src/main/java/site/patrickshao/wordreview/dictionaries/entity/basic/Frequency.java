package site.patrickshao.wordreview.dictionaries.entity.basic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

//单词中该单词出现的频率
@EqualsAndHashCode
@Builder@AllArgsConstructor
public class Frequency {
    public final double total;
    public final double spoken;
    public final double fiction;
    public final double magazine;
    public final double newspapaer;
    public final double academic;
}
