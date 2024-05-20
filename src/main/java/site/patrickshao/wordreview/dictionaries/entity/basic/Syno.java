package site.patrickshao.wordreview.dictionaries.entity.basic;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode@AllArgsConstructor
public class Syno {
    public final String prefix; //释义前缀，如"<非正式>"，"（特指口语的）"
    public final String[] ch_names; //释义数组，数组内都是近义词
}
