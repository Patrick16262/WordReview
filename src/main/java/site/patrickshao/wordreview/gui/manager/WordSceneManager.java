package site.patrickshao.wordreview.gui.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import site.patrickshao.wordreview.Main;
import site.patrickshao.wordreview.dictionaries.entity.basic.Translation;
import site.patrickshao.wordreview.dictionaries.entity.basic.Word;
import site.patrickshao.wordreview.gui.controller.word.SideBarAlt;
import site.patrickshao.wordreview.gui.controller.word.WordLabel;
import site.patrickshao.wordreview.gui.controller.word.WordSceneController;
import site.patrickshao.wordreview.gui.controller.word.WordSceneRoot;
import site.patrickshao.wordreview.user.ConfigManager;
import site.patrickshao.wordreview.user.MyBookManagerObj;
import site.patrickshao.wordreview.user.entity.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordSceneManager {
    private static List<Pane> pages;
    @Getter
    private static int thisPage;
    private static AnchorPane mainRoot;
    private static AnchorPane wordRoot;
    private static VBox sidebar;
    @Setter
    private static Runnable onFinish = () -> {};
    private static WordSceneRoot wordRootController;
    private static SideBarAlt sideBarController;
    private static List<Pair<Translation, ReciteMethod>> plan;
    private static Map<String, Integer> reusedMap;

    public static void start(AnchorPane root) {
        mainRoot = root;
        mainRoot.getChildren().add(wordRoot);
        SceneManager.useAltSidebar(sidebar);
        setPage(0);
    }

    public static int getPageNum() {
        return pages.size();
    }

    public static void loadScenes(PlanGenerator generator) throws IOException {
        FXMLLoader loader = getLoader("word-scene-root");
        wordRoot = loader.load();
        wordRootController = loader.getController();
        loader = getLoader("side-bar-alt");
        sidebar = loader.load();
        sideBarController = loader.getController();
        pages = new ArrayList<>();
        plan = generator.generate();
        reusedMap = new HashMap<>();
        for (var entry : plan) loadPlanEntry(entry);
    }

    public static void close() {
        mainRoot.getChildren().remove(wordRoot);
        SceneManager.refreshHomeView();
        onFinish.run();
        SceneManager.removeAltSidebar();

    }

    public static void nextPage() {
        if (thisPage == pages.size() - 1) {
            close();
            return;
        }
        wordRoot.getChildren().remove(pages.get(thisPage));
        thisPage++;
        wordRoot.getChildren().add( pages.get(thisPage));
        refreshBottomBar();
    }

    public static void previousPage() {
        wordRoot.getChildren().remove(pages.get(thisPage));
        thisPage--;
        wordRoot.getChildren().add(pages.get(thisPage));
        refreshBottomBar();
    }

    public static void setPage(int index) {
        wordRoot.getChildren().remove(pages.get(thisPage));
        thisPage = index;
        wordRoot.getChildren().add(pages.get(index));
        refreshBottomBar();
    }

    public static void markReviewed(String id) {
        int val= reusedMap.get(id);
        reusedMap.replace(id, val - 1);
        if (val == 1) ConfigManager.getBookManager().getCurrentMyBook().addReviewed(id);
    }

    public static void loadPlanEntry(Pair<Translation, ReciteMethod> entry) throws IOException {
        Word word = ConfigManager.getBookManager().getCurrentMyBook().getWordBook().getWords().get(entry.getKey().en_word);
        String pageName = null;
        if (entry.getValue() instanceof LearnMethod) {
            if (reusedMap.containsKey(entry.getKey().id))
                reusedMap.replace(entry.getKey().id, reusedMap.get(entry.getKey().id) + 1);
            else reusedMap.put(entry.getKey().id, 1);

            switch ((LearnMethod) entry.getValue()) {
                default -> {
                    pageName = word.name_en;
                    loadPage("choose-chinese", entry);
                }
            }
        }
        else {
            switch ((NewWordMethod) entry.getValue()) {
                default -> {
                    pageName = word.name_en;
                    loadPage("read-learn", entry);
                }
            }
        }
        FXMLLoader loader =getLoader("word-label");
        Pane pane = loader.load();
        WordLabel controller = loader.getController();
        controller.setName(pageName);
        controller.setIndex(pages.size() -1);
        sideBarController.addWordNode(pane);
        refreshBottomBar();
    }


    private static void refreshBottomBar() {
        wordRootController.setPreDisable(false);
        if (thisPage == 0) wordRootController.setPreDisable(true);
        sideBarController.refreshButtom();
    }


    private static void loadPage(String name, Pair<Translation, ReciteMethod> entry) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("word/" + name + ".fxml"));
        Pane pane = loader.load();
        WordSceneController controller = loader.getController();
        controller.setEntry(entry);
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        pages.add(pane);
    }

    private static FXMLLoader getLoader(String name) {
        return new FXMLLoader(Main.class.getResource("word/" + name + ".fxml"));
    }

    public static void showDict(Pair<Translation, ReciteMethod> entry) {
        nextPage();
    }


//    private static LearnWord words;
//    private static void generateScenes() {}
}
