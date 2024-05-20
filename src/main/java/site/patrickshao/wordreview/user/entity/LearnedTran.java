package site.patrickshao.wordreview.user.entity;

import lombok.Builder;
import site.patrickshao.wordreview.dictionaries.entity.basic.Translation;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
public class LearnedTran {
    public transient LocalDateTime learned;
    public transient List<LocalDateTime> reviewed = new ArrayList<>();
    public Date learned_;
    public List<Date> reviewed_;
    public boolean markedTooEasy;
    public String id;
    public transient Translation translation;
    public void adaptFromJson() {
        if (reviewed_ != null) {
            learned = learned_.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            reviewed = reviewed_.stream().map(date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()).toList();
        }
    }
    public void adaptToJson() {
        if (reviewed_ != null) {
            learned_ = Date.from(learned.atZone(ZoneId.systemDefault()).toInstant());
            reviewed_ = reviewed.stream().map(localDateTime -> Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())).toList();
        }
    }

}
