package site.patrickshao.wordreview.user.entity;

import javafx.util.Pair;
import site.patrickshao.wordreview.dictionaries.entity.basic.Translation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@FunctionalInterface
public interface PlanGenerator {
    @Nullable
    List<Pair<Translation, ReciteMethod>> generate();
}
