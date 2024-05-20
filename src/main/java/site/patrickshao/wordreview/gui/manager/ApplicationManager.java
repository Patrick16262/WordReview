package site.patrickshao.wordreview.gui.manager;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.Setter;
import site.patrickshao.wordreview.user.ConfigManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApplicationManager extends Application {
    @Getter@Setter
    private static Stage primaryStage;
    private static WordSceneManager wordSceneManager;
    private static SceneManager sceneManager;
    @Getter
    private static ExecutorService backgroundExecutor = Executors.newFixedThreadPool(20);

    public static void loadFonts() throws IOException {
        Font.loadFont(Files.newInputStream(Path.of("asset/font/Microsoft_Yahei.ttf")), 12);
        Font.loadFont(Files.newInputStream(Path.of("asset/font/Microsoft_Yahei_Bold.ttf")), 12);
    }

    public static void hide() {
        primaryStage.hide();
    }

    public static void minimize() {
        primaryStage.setIconified(true);
    }

    public void start(Stage primaryStage) throws Exception {
        ConfigManager.loadConfig();
        StorgeManager.loadAllAsset();
        primaryStage.setOnHidden(e -> {
            backgroundExecutor.shutdown();});
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);
        ApplicationManager.setPrimaryStage(primaryStage);
        ApplicationManager.loadFonts();
        ImageManager.loadWorkBookCover();
        if (ConfigManager.needTutor()) SceneManager.startWithTutor(primaryStage);
        else
            SceneManager.start(primaryStage);
    }

    public static void launchWithoutArg() {
        launch();
    }
}
