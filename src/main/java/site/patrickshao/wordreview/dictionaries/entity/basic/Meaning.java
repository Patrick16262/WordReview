package site.patrickshao.wordreview.dictionaries.entity.basic;

import lombok.*;

@EqualsAndHashCode@Builder
public class Meaning {
    public final PartOfSpeech pos; //词性
    public final Syno ch_syno;
    public  ExpSentence[] sentences; //例句
}
