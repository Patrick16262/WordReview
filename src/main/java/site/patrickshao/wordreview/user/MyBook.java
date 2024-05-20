package site.patrickshao.wordreview.user;

import com.google.gson.Gson;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.patrickshao.wordreview.dictionaries.entity.WordBook;
import site.patrickshao.wordreview.dictionaries.manager.WordBookManager;
import site.patrickshao.wordreview.user.entity.LearnedTran;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@EqualsAndHashCode@Getter@NoArgsConstructor
public class MyBook {
    private boolean deleted = false;
    private String book_id;
    private final transient HashMap<String, LearnedTran> learnedTrans = new HashMap<>();
    private transient WordBook wordBook;

    public static MyBook createMyBook(String book_id) {
        MyBook myBook = new MyBook(book_id);
        myBook.linkBook();
        return myBook;
    }


    private MyBook(String id) {
        book_id = id;
    }

    public void addLearned(String translation_id) {
        if (wordBook == null) linkBook();
        learnedTrans.put(translation_id,LearnedTran.builder().learned(LocalDateTime.now()).markedTooEasy(false).reviewed(new ArrayList<>()).id(translation_id).build());
        link(learnedTrans.get(translation_id));
        return;
    }

    public void addReviewed(String translation_id) {
        learnedTrans.get(translation_id).reviewed.add(LocalDateTime.now());
    }

    public void link(LearnedTran tran) {
        tran.translation = wordBook.getTranslation(tran.id);
    }

    public void markTooEasy(String id) {
        learnedTrans.get(id).markedTooEasy = true;
    }

    public void markRemoved() {
        deleted = true;
    }

    public void withdrawRemoved() {
        deleted = false;
    }

    public void linkBook() {
        wordBook = WordBookManager.getWordBook(book_id);
    }

    public String toJson() {
        var myJson = new JsonObj();
        myJson.learnedTrans = learnedTrans.values().toArray(LearnedTran[]::new);
        Arrays.stream(myJson.learnedTrans).peek(LearnedTran::adaptToJson).forEach(learnedTran -> learnedTrans.put(learnedTran.id, learnedTran));
        myJson.book_id = book_id;
        myJson.deleted = deleted;
        return new Gson().toJson(myJson);
    }

    public static MyBook fromJson(String json) {
        JsonObj jsonObj = new Gson().fromJson(json, JsonObj.class);
        Arrays.stream(jsonObj.learnedTrans).forEach(LearnedTran::adaptFromJson);
        MyBook myBook = new MyBook();
        myBook.book_id = jsonObj.book_id;
        return myBook;
    }
    public void linkTranslations() {
        learnedTrans.values().forEach(tran -> {
            tran.translation = WordBookManager.getWordBook(wordBook.getId()).getTranslation(tran.id);
        });
    }
    private class JsonObj {
        public boolean deleted = false;
        public String book_id;
        public LearnedTran[] learnedTrans;
    }
}
