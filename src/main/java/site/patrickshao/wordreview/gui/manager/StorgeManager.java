package site.patrickshao.wordreview.gui.manager;

import site.patrickshao.wordreview.dictionaries.manager.WordBookManager;
import site.patrickshao.wordreview.exception.UnhandledException;
import site.patrickshao.wordreview.user.ConfigManager;

import java.io.IOException;

public class StorgeManager {
    public static void load() throws IOException {
        ApplicationManager.getBackgroundExecutor().execute(() -> {
            try {
                ImageManager.loadImages();
                ImageManager.loadWorkBookCover();
            } catch (IOException e) {
                throw new UnhandledException(e);
            }
        });
        ConfigManager.loadConfigs();
        WordBookManager.loadBookMap();
        ConfigManager.getBookManager().linkAllBooks();
        WordBookManager.getBooks().values().forEach(wordBook ->
                ApplicationManager.getBackgroundExecutor().execute(() -> {
                    ApplicationManager.getBackgroundExecutor().execute(() -> {
                        try {
                            wordBook.loadwords();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        if (ConfigManager.getBookManager().getMyBook(wordBook.getId()) != null) {
                            ConfigManager.getBookManager().getMyBook(wordBook.getId()).linkTranslations();
                        }
                    });
                }));
    }
}
