package site.patrickshao.wordreview.gui.manager;

import site.patrickshao.wordreview.dictionaries.manager.WordBookManager;

import java.io.IOException;

public class StorgeManager {
    public static void loadAllAsset() {
        ApplicationManager.getBackgroundExecutor().execute(() -> {
            try {
                ImageManager.loadImages();
                ImageManager.loadWorkBookCover();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                WordBookManager.loadBookMap();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            WordBookManager.getBooks().forEach(
                    (s, wordBook) -> {
                        ApplicationManager.getBackgroundExecutor().execute(() -> {
                            try {
                                wordBook.loadwords();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
            );
        });
    }
}
