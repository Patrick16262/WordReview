package site.patrickshao.wordreview.dictionaries.dicts;

import com.google.gson.Gson;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import site.patrickshao.wordreview.dictionaries.entity.basic.Word;
import site.patrickshao.wordreview.exception.DictConnFaultException;
import site.patrickshao.wordreview.exception.DictWordNotFoundException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Set;

//由于该api位于境外，采用代理的方式访问
public class BaicizhanOnlineDict implements OnlineDict {
    @Getter
    private final String id = "baicizhan";
    private final HttpClient client;
    String proxy_url = "http://206.237.6.142:2333";

    public BaicizhanOnlineDict() {
        client = HttpClient.newBuilder().connectTimeout(Duration.ofMillis(2000)).build();
    }

    @Override
    public  Word lookupWord(@NonNull String englishWord) throws DictWordNotFoundException, DictConnFaultException {
//        String url_base = "https://cdn.jsdelivr.net/gh/lyc8503/baicizhan-word-meaning-API/data/words/";
//        String url_end = ".json";
//        englishWord = Texts.baiciToEscape(englishWord);
//        Word word;
//        var request = HttpRequest.newBuilder().GET().uri(URI.create(proxy_url)).header("Transmit-To", url_base + englishWord + url_end)
//                .build();
//        try {
//            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
//            if (response.statusCode() == 404) throw new DictWordNotFoundException();
//            var obj = new Gson().fromJson(response.body(), WordReceive.class);
//            var meanings = obj.getMean_cn().split("；[ ]{0,4}");
//            word = new Word(obj.getWord(), obj.getAccent());
//            boolean flag = true;
//            for (var c : meanings) {
//                PartOfSpeech pos = null;
//                var matcher = Pattern.compile("[a-zA-Z]{0,10}?[.]").matcher(c);
//                if (matcher.find()) {
//                    pos = Texts.toEnum(matcher.group(0));
//                    c = c.replaceAll(matcher.group(0), "");
//                }
//                var ch_names = c.split("，");
//                word.getMeanings().add(new Meaning(pos, null, ch_names, getId()));
//                if (flag)
//                    word.getMeanings().get(0).getSentences().add(new ExpSentence(obj.getSentence(), obj.getSentence_trans()));
//                flag = false;
//            }
//        } catch (DictWordNotFoundException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new DictConnFaultException(e);
//        }
        return null;
    }

    @Override
    public @NonNull Set<String> allwords() throws IOException {
        String all_url = "https://cdn.jsdelivr.net/gh/lyc8503/baicizhan-word-meaning-API/data/list.json";
        var request = HttpRequest.newBuilder().GET().uri(URI.create(proxy_url))
                .header("Transmit-To", all_url)
                .build();
        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200)
                throw new IOException("连接错误");
            var json = response.body();
            var rec = new Gson().fromJson(json, ListReceive.class);
            return rec.getList();
        } catch (IOException e) {
            throw new IOException("无法连接到服务器: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new IOException("链接中断: " + e.getMessage());
        }
    }

    //    Response(以单词 average 为例)
//    {
//        // 英文单词
//        "word": "average",
//            // 音标
//            "accent": "/ˈævərɪdʒ/",
//            // 中文意思
//            "mean_cn": "n.平均数；  adj.通常的；  vt.平均为",
//            // 英文解释(可能为空字符串, 但绝大多数单词有英文解释.)
//            "mean_en": "the result of adding several amounts together, finding a total, and dividing the total by the number of amounts",
//            // 英文例句(可能为空字符串, 但大部分单词有例句.)
//            "sentence": "His height equals the average of his parents' heights.",
//            // 英文例句翻译
//            "sentence_trans": "他的身高等于他父母身高的平均数。",
//            // 相关的短语(可能为空字符串)
//            "sentence_phrase": "the average of",
//            // 单词词源(可能为空字符串)
//            "word_etyma": "",
//            // 一些其他数据(可能为空)
//            "cloze_data": {
//        "syllable": "av-er-age",
//                "cloze": "av-er-a[ge]",
//                "options": ["gue|dge|geo|dj"],
//        "tips": [
//			["a[ge]", "ima[ge]"]
//		]
//    }
//    }
    @Data
    class WordReceive {
        private final String word;
        private final String accent;
        private final String mean_cn;
        private final String mean_en;
        private final String sentence;
        private final String sentence_trans;
        private final String sentence_phrase;
    }

    @Data
    class ListReceive {
        private final Integer total;
        private final Set<String> list;
    }
}
