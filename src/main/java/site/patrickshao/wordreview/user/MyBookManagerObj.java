package site.patrickshao.wordreview.user;

import com.google.gson.Gson;
import javafx.util.Pair;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.patrickshao.wordreview.dictionaries.entity.basic.Translation;
import site.patrickshao.wordreview.user.entity.LearnedTran;
import site.patrickshao.wordreview.user.entity.PlanGenerator;
import site.patrickshao.wordreview.user.entity.ReciteMethod;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@EqualsAndHashCode@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MyBookManagerObj {
    private transient Map<String, MyBook> myBooks = new HashMap<>();
    private transient MyBook currentMyBook;
    private transient final HashSet<LearnedTran> allLearned = new HashSet<>();
    private transient final HashSet<LearnedTran> needReview = new HashSet<>();
    private transient final HashSet<String> needLearn = new HashSet<>();
    private transient int learnedNumToday;
    private transient int needLearnNumToday; //不包括已学的
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
        needLearnNumToday = 0;
        reviewedNumToday = 0;
        needReviewNumToday = 0;

        if (currentMyBook == null) return;
        myBooks.values().stream().forEach(
                myBook -> myBook.getLearnedTrans().values().stream().forEach(
                        recited -> {
                            LocalDateTime dateTime = LocalDateTime.now();
                            dateTime = dateTime.minusHours(12);
                            if (recited.learned.isAfter(dateTime)) learnedNumToday++;
                            else if (recited.reviewed.isEmpty()) {
                                if (recited.markedTooEasy) return;
                                needReview.add(recited);
                            }
                            //简单粗暴判断是否隔一天需要复习;
                            System.err.println("正在使用简化版的复习方案");
                        }
                )
        );
        needReviewNumToday = needReview.size();
        if (ConfigManager.getConfig().word_num_per_day - learnedNumToday > 0) {
            var list = currentMyBook.getWordBook().getTranslations().
                    keySet().stream().filter(key -> !currentMyBook.getLearnedTrans().containsKey(key))
                    .toList();
            list = list.subList(0, Math.min(list.size(), ConfigManager.getConfig().word_num_per_day - learnedNumToday));
            needLearnNumToday = list.size();
            needLearn.addAll(list);
        }

    }

    public void linkAllBooks() {
        myBooks.forEach((a, b) -> {
            b.linkBook();
        });
    }

    public void addLearned(String id) {
        currentMyBook.addLearned(id);
    }

    public void addReviewed(String id) {
        currentMyBook.addReviewed(id);
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

    public static MyBookManagerObj fromJson(String string) {
        var jsonObj = new Gson().fromJson(string, jsonObj.class);
        MyBookManagerObj obj = new MyBookManagerObj();
        Arrays.stream(jsonObj.myBooks).map(MyBook::fromJson)
                .forEach(myBook -> obj.myBooks.put(myBook.getBook_id(), myBook));
        obj.currentMyBook = obj.getMyBook(jsonObj.currentMyBook);
//        obj.linkAllBooks();
//        obj.refreshLearned();
        return obj;
    }

    public static MyBookManagerObj createEmptyObj() {
        return new MyBookManagerObj();
    }

    public String toJson() {
        var jsonObj = new jsonObj();
        jsonObj.myBooks = myBooks.values().stream()
                .map(MyBook::toJson)
                .toArray(String[]::new);
        if (currentMyBook != null)
        jsonObj.currentMyBook = currentMyBook.getBook_id();
        return new Gson().toJson(jsonObj);
    }

    private static <T> List<List<T>> groupList(List<T> list, int groupSize) {
        List<List<T>> groupedLists = new ArrayList<>();
        for (int i = 0; i < list.size(); i += groupSize) {
            int end = Math.min(i + groupSize, list.size());
            groupedLists.add(list.subList(i, end));
        }
        return groupedLists;
    }

    private class jsonObj {
        public String[] myBooks;
        public String currentMyBook;
    }
}
