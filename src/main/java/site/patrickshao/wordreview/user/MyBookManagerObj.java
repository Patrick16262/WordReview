package site.patrickshao.wordreview.user;

import javafx.util.Pair;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import site.patrickshao.wordreview.dictionaries.entity.basic.Translation;
import site.patrickshao.wordreview.user.entity.LearnedTran;
import site.patrickshao.wordreview.user.entity.PlanGenerator;
import site.patrickshao.wordreview.user.entity.ReciteMethod;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@EqualsAndHashCode
public class MyBookManagerObj {
    private transient Map<String, MyBook> myBooks = new HashMap<>();
    private transient MyBook currentMyBook;
    private transient final HashSet<LearnedTran> allLearned = new HashSet<>();
    private transient final HashSet<LearnedTran> needReview = new HashSet<>();
    private transient final HashSet<String> needLearn = new HashSet<>();
    private transient int learnedNumToday;
    private transient int needLearnToday; //包括已学的
    private transient int reviewedNumToday;
    private transient int needReviewNumToday; //不包括已复习的

    PlanGenerator learnPlanGenerator = () -> {
        var plan = new ArrayList<Pair<Translation, ReciteMethod>>();
        var lists = groupList(needLearn.stream().toList(), 5);
        lists.stream().forEach(list -> {
            list.stream().forEach(
                    e -> plan.add(new Pair<>(currentMyBook.getWordBook().getTranslation(e),
                            ConfigManager.getConfig().allWordConfig.newWordMethod))
            );

            Arrays.stream(ConfigManager.getConfig().allWordConfig.learnMethods).forEach(
                    learnMethod ->
                            list.stream().forEach(e -> plan.add(new Pair<>(currentMyBook.getWordBook().getTranslation(e),
                                    learnMethod))
                            )
                    );
        });
        return plan;
    };

    PlanGenerator reviewPlanGenerator = () -> {
        var plan = new ArrayList<Pair<Translation, ReciteMethod>>();
        groupList(needReview.stream().toList(), 5).stream().forEach(
                list -> Arrays.stream(ConfigManager.getConfig().allWordConfig.reviewMethods).forEach(learnMethod -> list.stream().forEach(
                                e -> plan.add(new Pair<>(e.translation, learnMethod))
                        )
                )
        );
        return plan;
    };

    PlanGenerator recitePlanGenerator = () -> {
        var plan = new ArrayList<Pair<Translation, ReciteMethod>>();
        plan.addAll(learnPlanGenerator.generate());
        plan.addAll(reviewPlanGenerator.generate());
        return plan;
    };

    public void refreshLearned() {
        learnedNumToday = 0;
        needLearnToday = ConfigManager.getConfig().word_num_per_day;
        reviewedNumToday = 0;
        needReviewNumToday = 0;

        myBooks.values().stream().forEach(
                myBook -> myBook.getLearnedTrans().values().stream().forEach(
                        recited -> {
                            if (recited.markedTooEasy) return;
                            LocalDateTime dateTime = LocalDateTime.now();
                            dateTime.minusHours(12);
                            if (recited.learned.compareTo(dateTime) > 0) learnedNumToday++;
                            else if (recited.reviewed.isEmpty()) needReview.add(recited);
                            //简单粗暴判断是否隔一天需要复习;
                            System.err.println("正在使用简化版的复习方案");
                        }
                )
        );
        if (ConfigManager.getConfig().word_num_per_day - learnedNumToday > 0) {
            var list = currentMyBook.getWordBook().getTranslations().
                    keySet().stream().filter(key -> !currentMyBook.getLearnedTrans().containsKey(key))
                    .toList();
            list = list.subList(0, Math.min(list.size(), ConfigManager.getConfig().word_num_per_day - learnedNumToday));
            needLearnToday = list.size();
            needLearn.addAll(list);
        }
    }

    public void linkAllBooks() {
        myBooks.forEach((a, b) -> {
            b.linkBook();
        });
    }

    public void setBook(String bookId) {
        System.err.println("正在使用简化版的词书管理");
        myBooks.clear();
        MyBook myBook = MyBook.createMyBook(bookId);
        myBooks.put(bookId, myBook);
        //简单粗暴地添加词书
        currentMyBook = myBook;
        currentMyBook.linkBook();
    }

    public MyBook getMyBook(String id) {
        return myBooks.get(id);
    }

    private static <T> List<List<T>> groupList(List<T> list, int groupSize) {
        List<List<T>> groupedLists = new ArrayList<>();
        for (int i = 0; i < list.size(); i += groupSize) {
            int end = Math.min(i + groupSize, list.size());
            groupedLists.add(list.subList(i, end));
        }
        return groupedLists;
    }
}
