package site.patrickshao.wordreview.dictionaries.manager;

import com.google.gson.Gson;
import lombok.Data;
import lombok.Getter;
import site.patrickshao.wordreview.constants.Constants;
import site.patrickshao.wordreview.dictionaries.entity.WordBook;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class WordBookManager {
    @Getter
    public static Map<String, WordBook> books;
    public static void loadBookMap() throws IOException {
        if (books != null) return;
        Info info = new Gson().fromJson(Files.newBufferedReader(Path.of(Constants.Files.wordbook_index)), Info.class);
        books = new HashMap<>();
        for (var c : info.data.normalBooksInfo ) {
            books.put(c.getId(), c.remake());
        }
    }

    public static WordBook getWordBook (String id) {
        return books.get(id);
    }
    private static volatile int downloaded = 0;
    public static void downloadBookCover() throws IOException {
        var pool= Executors.newFixedThreadPool(20);
        var client = HttpClient.newHttpClient();

        Info info = new Gson().fromJson(Files.newBufferedReader(Path.of(Constants.Files.wordbook_index)), Info.class);
        for (var c : info.data.normalBooksInfo ) {
            var request = HttpRequest.newBuilder(URI.create(c.getCover())).GET().build();

            pool.execute(()->{
                try {
                    client.send(request, HttpResponse.BodyHandlers.ofFile(Path.of(Constants.Folders.wordbook_image + c.getId() + ".jpg")));
                    downloaded ++;
                    System.out.println(downloaded);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @Data
    private class Info {
        private final Data_ data;
    }

    @Data
    private class Data_ {
        private final WordBook_[] normalBooksInfo;
    }

    @Data
    private class WordBook_ {
        private final String cover;
        private final BookOrigin bookOrigin;
        private final String introduce;
        private final int wordNum;
        private final int reciteUserNum;
        private final String id;
        private final String title;
        private final String version;
        private final Tag[] tags;

        @Data
        private class BookOrigin {
            private final String originUrl;
            private final String desc;
            private final String originName;
        }
        @Data
        private class Tag {
            private final String tagName;
        }

        public WordBook remake() {
            return WordBook.builder().originName(this.bookOrigin.originName)
                    .originUrl(this.bookOrigin.originUrl)
                    .wordNum(this.wordNum)
                    .title(this.title)
                    .id(this.id)
                    .reciteUserNum(this.reciteUserNum)
                    .tagName(Arrays.stream(this.getTags()).map(Tag::getTagName).toArray(String[]::new))
                    .version(this.version)
                    .introduce(this.introduce)
                    .build();
        }
    }
}
