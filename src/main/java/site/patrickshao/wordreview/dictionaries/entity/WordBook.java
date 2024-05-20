package site.patrickshao.wordreview.dictionaries.entity;

import com.google.gson.Gson;
import lombok.Builder;
import lombok.Data;
import site.patrickshao.wordreview.constants.Constants;
import site.patrickshao.wordreview.dictionaries.entity.basic.*;
import site.patrickshao.wordreview.dictionaries.util.Texts;
import site.patrickshao.wordreview.dictionaries.util.Words;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Builder
public class WordBook {
    private final String id;
    private final String title;
    private final String introduce;
    private final String[] tagName;
    private int wordNum;

    private String version = null;

    private int reciteUserNum = -1;
    private String originUrl = null;
    private String originName = null;

    private boolean custom = false;
    private Map<String, Word> words;
    private Map<String, Translation> translations;
    private HashSet<SimpleWord> index;

    public void loadwords() throws IOException {
        if (words != null) return;
        Words.loadFrequencyMap();
        index = new HashSet<>();
        words = new HashMap<>();
        try (var in = Files.newBufferedReader(Path.of(Constants.Folders.wordbook + id + ".json"))) {
            var line = in.readLine();
            while (line != null) {
                BookWord_ word_ = new Gson().fromJson(line, BookWord_.class);
                AtomicInteger translation_order = new AtomicInteger();
                Word word = Word.builder().record(new DictRecord(word_.wordRank, word_.bookId))
                        .name_en(word_.headWord)
                        .ukphone(word_.content.word.content.ukphone)
                        .usphone(word_.content.word.content.usphone)
                        .translations(Arrays.stream(word_.content.word.content.trans).map(
                                tran -> {
                                    var idx = new SimpleWord(Texts.toEnum(tran.pos), word_.headWord, tran.tranCn);
                                    index.add(idx);
                                    translation_order.getAndIncrement();
                                    return Translation.builder()
                                            .id(word_.content.word.wordId + translation_order.get())
                                            .en_word(word_.headWord)
                                            .dict(word_.bookId)
                                            .pos(Texts.toEnum(tran.pos))
                                            .trans_en(tran.tranOther)
                                            .frequency(Words.findFrenquency(word_.headWord, Texts.toEnum(tran.pos)))
                                            .meanings(Arrays.stream(Texts.seperateTrans(tran.tranCn))
                                                    .map(mean -> {
                                                        return Meaning.builder()
                                                                .ch_syno(Texts.seperateMeaning(mean))
                                                                .pos(Texts.toEnum(tran.pos))
                                                                .sentences(null).build();
                                                    }).toArray(Meaning[]::new))
                                            .build();
                                }
                        ).toArray(Translation[]::new))
                        .build();
                words.put(word_.headWord, word);
                line = in.readLine();
            }
        }
        translations = new HashMap<>();
        words.values().forEach(word -> Arrays.stream(word.translations).forEach(translation -> translations.put(translation.id, translation)));
    }

    public Translation getTranslation(String id) {
        return translations.get(id);
    }

    private List<String> sortTranslationByFrequency() {
        return translations.values().stream().sorted((tran1, tran2) -> {
            if (tran1.frequency.total - tran2.frequency.total > 0) return 1;
            else if (tran1.frequency.total - tran2.frequency.total < 0) return -1;
            else return 0;
        }).map(tran -> tran.id).toList();
    }

    @Data
    private class BookWord_ {
        private final int wordRank;
        private final String headWord;
        private final String bookId;
        private final Content_ content;

        @Data
        private class Content_ {
            private final Word_ word;

            @Data
            private class Word_ {
                private final String wordHead;
                private final String wordId;
                private final Content__ content;

                @Data
                private class Content__ {
                    private final Exam[] exam;
                    private final Sentence_ sentence;
                    private final String usphone;
                    private final String usspeech;
                    private final String ukphone;
                    private final String ukspeech;
                    private final Syno_ syno;
                    private final Phrase_ phrase;
                    private final RelWord_ relWord;
                    private final Tran[] trans;

                    @Data
                    private class Tran {
                        private String tranCn;
                        private String descOther;
                        private String pos;
                        private String descCn;
                        private String tranOther;
                    }

                    @Data
                    private class RelWord_ {
                        private final String desc;
                        private final Rel[] rels;

                        @Data
                        private class Rel {
                            private final String pos;
                            private final Word__[] words;

                            @Data
                            private class Word__ {
                                private String hwd;
                                private String tran;
                            }
                        }
                    }

                    @Data
                    private class Phrase_ {
                        private final String desc;
                        private final Phrase[] phrases;

                        @Data
                        private class Phrase {
                            private final String pContent;
                            private final String pCn;
                        }
                    }

                    @Data
                    private class Syno_ {
                        private final Syno[] synos;
                        private final String desc;

                        @Data
                        private class Syno {
                            private final String pos;
                            private final String tran;
                            private final Hwd[] hwds;

                            @Data
                            private class Hwd {
                                private final String w;
                            }
                        }
                    }

                    @Data
                    private class Sentence_ {
                        private final Sentence[] sentences;
                        private final String desc;

                        @Data
                        private class Sentence {
                            private final String sContent;
                            private final String sCn;
                        }
                    }

                    @Data
                    private class Exam {
                        private final String question;
                        private final Answer answer;
                        private final int examType;
                        private final Choice[] choices;

                        @Data
                        private class Choice {
                            private final int choiceIndex;
                            private final String choice;
                        }

                        @Data
                        private class Answer {
                            private final String explain;
                            private final int rightIndex;
                        }
                    }
                }
            }
        }
    }
}
