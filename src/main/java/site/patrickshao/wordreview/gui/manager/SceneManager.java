package site.patrickshao.wordreview.gui.manager;

import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import site.patrickshao.wordreview.Main;
import site.patrickshao.wordreview.exception.UnhandledException;
import site.patrickshao.wordreview.gui.controller.HomeView;
import site.patrickshao.wordreview.gui.controller.RootPane;
import site.patrickshao.wordreview.gui.controller.SideBar;
import site.patrickshao.wordreview.gui.controller.dialog.DialogRoot;
import site.patrickshao.wordreview.gui.controller.dialog.DialogType;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SceneManager {
    private static final List<Pane> fixedPages = new ArrayList<Pane>();
    private static AnchorPane sideBar;
    private static AnchorPane shadowPane;
    private static AnchorPane rootPane;
    private static AnchorPane mainPane;
    private static RootPane rootPaneController;
    private static SideBar sideBarController;
    @Getter
    private static Scene primaryScene;
    private static ParallelTransition hideTransition;
    private static ParallelTransition showTransition;
    @Getter
    private static boolean sidebarShown;
    @Getter
    private static boolean sidebarDisabled;
    private static DialogRoot dialogController;
    private static StackPane dialog;
    private static Button dialogCloseBtn;
    private static HomeView homeViewController;

    public static void startWithTutor(Stage primaryStage) throws IOException {
        sidebarDisabled = true;
        start(primaryStage);
        TutorSceneManager.start(rootPane, () -> sidebarDisabled = false);
    }

    public static Object showDialog(DialogType dialogType) {
        Pane pane = null;
        Object controller = null;
        try {
            switch (dialogType) {
                case LOGIN -> {
                    FXMLLoader loader = getLoader("dialog/login.fxml");
                    pane = loader.load();
                    controller = loader.getController();
                }
                case REGISTER -> {
                    FXMLLoader loader = getLoader("dialog/register.fxml");
                    pane = loader.load();
                    controller = loader.getController();
                }
                case CREATE_BOOK -> {
                    FXMLLoader loader = getLoader("dialog/config-book.fxml");
                    pane = loader.load();
                    controller = loader.getController();
                }
                case ACCOUNT -> {
                }
            }
        } catch (IOException e) {
            throw new UnhandledException(e);
        }
        pane.getChildren().add(dialogCloseBtn);
        rootPane.getChildren().remove(dialog);

        dialogController.setContent(pane);
        rootPane.getChildren().add(dialog);
        return controller;
    }
    public static void closeDialog() {
        rootPane.getChildren().remove(dialog);
    }
    public static void start(Stage stage) throws IOException {
        if (primaryScene != null) {
            throw new RuntimeException();
        }
        loadNodes();
        sideBar.setLayoutX(-200);
        sideBar.setLayoutY(40);
        rootPane.getChildren().add(0, sideBar);
        fixedPages.get(0).setLayoutX(100);
        setPage(0);
        sidebarShown = false;
        primaryScene = new Scene(shadowPane);
        stage.setScene(primaryScene);
        primaryScene.setFill(null);
        stage.show();
    }

    public static void setPage(int index) {
        Pane page = fixedPages.get(index);
        page.setLayoutY(0);
        page.setLayoutX(0);
        if (!mainPane.getChildren().isEmpty())
            mainPane.getChildren().clear();
        mainPane.getChildren().add(page);
    }

    public static void showSideBar() {
        if (sidebarDisabled) return;
        if (!sidebarShown) {
        sidebarShown = true;
        showTransition.play();}
    }

    public static void hideSideBar() {
        if (sidebarShown){
        sidebarShown = false;
        hideTransition.play();}
    }

    public static void refreshSidebar() {
        sideBarController.refreash();
    }

    public static void refreshHomeView() {
        homeViewController.refresh();
    }

    private static void loadNodes() throws IOException {
        var rootPaneLoader = getLoader("root-pane.fxml");
        shadowPane = rootPaneLoader.load();
        RootPane controller = rootPaneLoader.getController();
        rootPane = controller.getRoot_pane();
        mainPane = controller.getMain_pane();

        var sideBarloader = getLoader("side-bar.fxml");
        sideBar = sideBarloader.load();
        sideBarController = sideBarloader.getController();

        var dialogLoader = getLoader("dialog/dialog-root.fxml");
        dialog = dialogLoader.load();
        dialogController = dialogLoader.getController();
        dialogCloseBtn = getLoader("dialog/close-btn.fxml").load();
        dialog.setLayoutX(0);
        dialog.setLayoutY(40);

        FXMLLoader loaderHome = getLoader("home-view.fxml");

        fixedPages.add(loaderHome.load());
        homeViewController = loaderHome.getController();
        fixedPages.add(getLoader("plan-view.fxml").load());
        fixedPages.add(getLoader("book-view.fxml").load());
        fixedPages.add(getLoader("dict-view.fxml").load());
        fixedPages.add(getLoader("info-view.fxml").load());

        loadAnimation();
    }


    private static void loadAnimation() {
        TranslateTransition showTransition1 = new TranslateTransition(Duration.seconds(0.25), sideBar);
        showTransition1.setToX(200);
        showTransition1.setInterpolator(Interpolator.EASE_OUT);
        TranslateTransition showTransition2 = new TranslateTransition(Duration.seconds(0.25), mainPane);
        showTransition2.setToX(100);
        showTransition2.setInterpolator(Interpolator.EASE_OUT);
        showTransition = new ParallelTransition(showTransition1, showTransition2);

        TranslateTransition hideTransition1 = new TranslateTransition(Duration.seconds(0.25), sideBar);
        hideTransition1.setToX(0);
        hideTransition1.setInterpolator(Interpolator.EASE_OUT);
        TranslateTransition hideTransition2 = new TranslateTransition(Duration.seconds(0.25), mainPane);
        hideTransition2.setToX(0);
        hideTransition2.setInterpolator(Interpolator.EASE_OUT);
        hideTransition = new ParallelTransition(hideTransition1, hideTransition2);
    }
    private static void rootClip(Node node) {
        var clipRec = new Rectangle(800, 590);
        clipRec.setLayoutX(0);
        clipRec.setLayoutY(10);
        clipRec.setArcHeight(20);
        clipRec.setArcWidth(20);
        node.setClip(clipRec);
    }
    private static FXMLLoader getLoader(String name) {
        return new FXMLLoader(Main.class.getResource(name));
    }

}

