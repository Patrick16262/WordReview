package net.patrickshao.wordreview.manager;

import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import net.patrickshao.wordreview.Main;
import net.patrickshao.wordreview.controller.SideBar;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BarSceneManager {
    private static List<VBox> pages = new ArrayList<VBox>();
    private static VBox sideBar;
    private static AnchorPane rootPane;
    private static SideBar sideBarController;
    private static HBox MenuIcon;
    private static HBox UserIcon;
    @Getter
    private static Scene primaryScene;
    private static boolean sidebarExsist = false;
    private static ParallelTransition hideTransition;
    private static ParallelTransition showTransition;

    public static void toMainPage(Stage stage) {
        if (primaryScene != null) {
            throw new RuntimeException();
        }
        MenuIcon.setLayoutX(0);
        MenuIcon.setLayoutY(0);
        rootPane.getChildren().add(MenuIcon);

        UserIcon.setLayoutX(500);
        UserIcon.setLayoutY(0);
        rootPane.getChildren().add(UserIcon);

        pages.get(0).setLayoutX(100);
        setPage(0);

        primaryScene = new Scene(rootPane);
        stage.setScene(primaryScene);
        stage.show();
    }

    public static void setPage(int index) {
        VBox page = pages.get(index);

        if (hideTransition.getChildren().size() > 1) hideTransition.getChildren().remove(1);
        if (showTransition.getChildren().size() > 1) showTransition.getChildren().remove(1);

        TranslateTransition showTransition_ = new TranslateTransition(Duration.seconds(0.5), page);
        showTransition_.setToX(100);
        showTransition_.setInterpolator(Interpolator.EASE_OUT);
        showTransition.getChildren().add(1, showTransition_);

        TranslateTransition hideTransition_= new TranslateTransition(Duration.seconds(0.5), page);
        hideTransition_.setToX(0);
        hideTransition_.setInterpolator(Interpolator.EASE_OUT);
        hideTransition.getChildren().add(1, hideTransition_);

        if (rootPane.getChildren().size() > 2)
        rootPane.getChildren().remove(1);
        rootPane.getChildren().add(1, page);
    }

    public static void showSideBar() {
        sideBar.setLayoutX(-200);
        sideBar.setLayoutY(0);
        rootPane.getChildren().remove(0);
        rootPane.getChildren().add(0, sideBar);
        showTransition.play();
    }

    public static void hideSideBar() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), rootPane.getChildren().get(1));
        translateTransition.setToX(0);
        translateTransition.setInterpolator(Interpolator.EASE_OUT);
        translateTransition.play();
        hideTransition.play();
        new Thread(() -> {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> {
                MenuIcon.setLayoutX(0);
                MenuIcon.setLayoutY(0);
                rootPane.getChildren().remove(0);
                rootPane.getChildren().add(0, MenuIcon);
            });
        }).start();

    }

    public static void loadNodes() throws IOException {
        rootPane = getLoader("main-root-pane.fxml").load();
        rootPane.getStylesheets().add(getStyleSheet("main-root-pane.css"));

        var sideBarloader = getLoader("side-bar.fxml");
        sideBar = sideBarloader.load();
        sideBar.getStylesheets().add(getStyleSheet("side-bar.css"));
        sideBarController = sideBarloader.getController();

        UserIcon = getLoader("user-icon.fxml").load();
        UserIcon.getStylesheets().add(getStyleSheet("user-icon.css"));

        MenuIcon = getLoader("menu-icon.fxml").load();
        boolean add = MenuIcon.getStylesheets().add(getStyleSheet("menu-icon.css"));


        pages.add(getLoader("main-view.fxml").load());
        pages.get(0).getStylesheets().add(Main.class.getResource("main-view.css").toExternalForm());

        TranslateTransition showTransition1 = new TranslateTransition(Duration.seconds(0.5), sideBar);
        showTransition1.setToX(200);
        showTransition1.setInterpolator(Interpolator.EASE_OUT);
        showTransition = new ParallelTransition(showTransition1);

        TranslateTransition hideTransition1 = new TranslateTransition(Duration.seconds(0.5), sideBar);
        hideTransition1.setToX(0);
        hideTransition1.setInterpolator(Interpolator.EASE_OUT);
        hideTransition = new ParallelTransition(hideTransition1);
    }
    private static FXMLLoader getLoader(String name) {
        return new FXMLLoader(Main.class.getResource(name));
    }
    private static String getStyleSheet(String name) {
        return Main.class.getResource(name).toExternalForm();
    }
}

