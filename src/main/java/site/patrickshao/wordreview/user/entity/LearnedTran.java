package site.patrickshao.wordreview.user.entity;

import lombok.Builder;
import site.patrickshao.wordreview.dictionaries.entity.basic.Translation;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Builder
public class LearnedTran {
    public LocalDateTime learned;
    public ArrayList<LocalDateTime> reviewed;
    public boolean markedTooEasy;
    public String id;
    public transient Translation translation;
}
