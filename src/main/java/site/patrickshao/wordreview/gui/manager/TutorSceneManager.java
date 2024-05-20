package site.patrickshao.wordreview.gui.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import lombok.Getter;
import site.patrickshao.wordreview.Main;
import site.patrickshao.wordreview.user.ConfigManager;
import site.patrickshao.wordreview.user.entity.Config;
import site.patrickshao.wordreview.user.entity.TranStyle;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.ArrayList;

public class TutorSceneManager {
    private static AnchorPane root;
    private static AnchorPane tutor_root;
    private static ArrayList<Pane> pages = new ArrayList<>();
    @Getter
    private static Config config;
    private static int this_page;
    private static Button next_button;
    private static Button previous_button;
    private static Button close_button;
    private static Runnable OnTutorClose;

    public static void start(AnchorPane rootPane, Runnable OnTutorFinnish) throws IOException {
        OnTutorClose = OnTutorFinnish;
        root = rootPane;
        config = new Config();
        this_page = 0;
        config.tranStyle = TranStyle.single_meaning;
        config.differsImportance = true;


        loadNodes();
        root.getChildren().add(tutor_root);
        setPage(0);
    }


    public static void close() {
        setConfig();
        root.getChildren().remove(tutor_root);
        OnTutorClose.run();
    }

    public static void nextPage() {
        if (this_page == pages.size() - 1) {
            close();
            return;
        }
        tutor_root.getChildren().remove(pages.get(this_page));
        this_page++;
        tutor_root.getChildren().add(0, pages.get(this_page));
        refreshBottomBar();
    }

    public static void previousPage() {
        tutor_root.getChildren().remove(pages.get(this_page));
        this_page--;
        tutor_root.getChildren().add(0, pages.get(this_page));
        refreshBottomBar();
    }

    public static void setPage(int index) {
        tutor_root.getChildren().remove(pages.get(this_page));
        this_page = index;
        tutor_root.getChildren().add(index, pages.get(this_page));
        refreshBottomBar();
    }

    private static void refreshBottomBar() {
        tutor_root.getChildren().remove(close_button);
        tutor_root.getChildren().remove(next_button);
        tutor_root.getChildren().remove(previous_button);
        if (this_page == 0) {
            tutor_root.getChildren().add(next_button);
            return;
        }
        if (this_page == pages.size() - 1) {
            tutor_root.getChildren().add(previous_button);
            tutor_root.getChildren().add(close_button);
            return;
        }
        tutor_root.getChildren().add(previous_button);
        tutor_root.getChildren().add(next_button);
    }

    private static void setConfig() {
        ConfigManager.getConfig().differsImportance = config.differsImportance;
        ConfigManager.getConfig().tranStyle = config.tranStyle;
    }

    private static Button makeButton(Ikon iconCode) {
        var font = new FontIcon(iconCode);
        font.setIconColor(Paint.valueOf("#ffffff")); //?
        font.setIconSize(24);
        var button = new Button("", font);
        button.getStyleClass().add("common-button");
        button.setLayoutX(700);
        button.setLayoutY(500);
        return button;
    }

    private static void loadNodes() throws IOException {

        next_button = makeButton(FontAwesomeSolid.ARROW_RIGHT);
        previous_button = makeButton(FontAwesomeSolid.ARROW_LEFT);
        close_button = makeButton(FontAwesomeSolid.TIMES);

        previous_button.setLayoutX(60);
        previous_button.setLayoutY(500);

        next_button.setOnAction(actionEvent -> nextPage());
        previous_button.setOnAction(actionEvent -> previousPage());
        close_button.setOnAction(actionEvent -> close());

        pages.add((Pane) loadFXML("set-tran"));
        pages.add((Pane) loadFXML("set-diff"));
        pages.add((Pane) loadFXML("set-book"));
        pages.add((Pane) loadFXML("set-account"));

        tutor_root = (AnchorPane) loadFXML("tutor_root");
        tutor_root.setLayoutX(0);
        tutor_root.setLayoutY(40);
    }
    private static Node loadFXML(String name) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource( "tutor/"+ name + ".fxml"));
        return loader.load();
    }
}
