package net.patrickshao.wordreview.manager;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ApplicationManager extends Application {
    @Getter@Setter
    private static Stage primaryStage;
    private static ListSceneManager listSceneManager;
    private static BarSceneManager barSceneManager;

    public static void loadFonts() throws IOException {
        Font.loadFont(Files.newInputStream(Path.of("asset/font/Microsoft_Yahei.ttf")), 12);
        Font.loadFont(Files.newInputStream(Path.of("asset/font/Microsoft_Yahei_Bold.ttf")), 12);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        ApplicationManager.setPrimaryStage(primaryStage);
        ApplicationManager.loadFonts();
        ImageManager.loadIcons();
        ImageManager.loadWorkBookImage();
        BarSceneManager.loadNodes();
        BarSceneManager.toMainPage(primaryStage);
    }

    public static void launchWithoutArg() {
        launch();
    }
}
