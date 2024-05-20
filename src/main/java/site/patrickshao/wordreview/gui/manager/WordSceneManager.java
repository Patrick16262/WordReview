package site.patrickshao.wordreview.gui.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lombok.Setter;
import site.patrickshao.wordreview.Main;
import site.patrickshao.wordreview.dictionaries.entity.basic.Translation;
import site.patrickshao.wordreview.dictionaries.entity.basic.Word;
import site.patrickshao.wordreview.gui.controller.word.WordSceneController;
import site.patrickshao.wordreview.gui.controller.word.WordSceneRoot;
import site.patrickshao.wordreview.user.ConfigManager;
import site.patrickshao.wordreview.user.entity.LearnMethod;
import site.patrickshao.wordreview.user.entity.NewWordMethod;
import site.patrickshao.wordreview.user.entity.PlanGenerator;

import java.io.IOException;
import java.util.List;

public class WordSceneManager {
    private static List<Pane> pages;
    private static List<String> pageNames;
    private static int thisPage;
    private static AnchorPane mainRoot;
    private static AnchorPane wordRoot;
    @Setter
    private static Runnable onFinish;
    private static WordSceneRoot wordRootController;

    public static void start(AnchorPane root) {
        mainRoot = root;
    }

    private static void loadScenes(PlanGenerator generator) throws IOException {
        var plan = generator.generate();
        for (var entry : plan) {
            Word word = ConfigManager.getBookManager().getCurrentMyBook().getWordBook().getWords().get(entry.getKey().en_word);
            if (entry.getValue() instanceof LearnMethod)
                switch ((LearnMethod) entry.getValue()) {
                    default -> {
                        pageNames.add(word.name_en);
                        loadPage("choose-chinese", word, entry.getKey());
                    }
                }
            else switch ((NewWordMethod) entry.getValue()) {
                default -> {
                    pageNames.add(word.name_en);
                    loadPage("read-learn", word, entry.getKey());
                }
            }
        }
        FXMLLoader loader1 = getLoader("word-scene-root");
        wordRoot = loader1.load();
        wordRootController = loader1.getController();
    }

    public static void close() {
        mainRoot.getChildren().remove(wordRoot);
        onFinish.run();
    }

    public static void nextPage() {
        if (thisPage == pages.size() - 1) {
            close();
            return;
        }
        wordRoot.getChildren().remove(pages.get(thisPage));
        thisPage++;
        wordRoot.getChildren().add(0, pages.get(thisPage));
        refreshBottomBar();
    }

    public static void previousPage() {
        wordRoot.getChildren().remove(pages.get(thisPage));
        thisPage--;
        wordRoot.getChildren().add(0, pages.get(thisPage));
        refreshBottomBar();
    }

    public static void setPage(int index) {
        wordRoot.getChildren().remove(pages.get(thisPage));
        thisPage = index;
        wordRoot.getChildren().add(index, pages.get(thisPage));
        refreshBottomBar();
    }


    private static void refreshBottomBar() {
        wordRootController.setPreDisable(false);
        if (thisPage == 0) wordRootController.setPreDisable(true);
    }


    private static void loadPage(String name, Word word, Translation translation) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("word/" + name + ".fxml"));
        Pane pane = loader.load();
        WordSceneController controller = loader.getController();
        controller.setWord(word);
        controller.setTranslation(translation);
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        pages.add(pane);
    }

    private static FXMLLoader getLoader(String name) {
        return new FXMLLoader(Main.class.getResource("word/" + name + ".fxml"));
    }


//    private static LearnWord words;
//    private static void generateScenes() {}
}
