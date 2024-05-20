package site.patrickshao.wordreview.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import site.patrickshao.wordreview.Main;
import site.patrickshao.wordreview.dictionaries.entity.WordBook;
import site.patrickshao.wordreview.dictionaries.manager.WordBookManager;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookView implements Initializable {
    @FXML
    AnchorPane pane;
    @FXML
    FontIcon search_font;
    @FXML@Getter
    private FlowPane my_pane;
    @FXML@Getter
    private FlowPane rec_pane;
    @FXML
    private Button search_btn;
    @FXML
    private TextField search_field;
    @FXML
    private VBox vbox_mybook;
    @FXML
    private VBox vbox_content;
    private boolean tutorMod = false;
    private Runnable simpleMod = () -> {
        System.err.println("正在使用简化版的词书页面");
        vbox_content.getChildren().remove(vbox_mybook);
    };

    public void setTutorMod(boolean tutorMod) {
        this.tutorMod = tutorMod;
        refreshAllBooks();
    }

    public void refreshAllBooks() {
        my_pane.getChildren().clear();
        rec_pane.getChildren().clear();
        WordBookManager.getBooks().forEach((s, wordBook) -> {
            if (wordBook.isCustom()) pushBackMyBook(wordBook);
            else pushBackRecommandBook(wordBook);
        });
    }

    public void pushBackRecommandBook(WordBook book) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("book.fxml"));
        VBox bookScene = null;
        Book bookController = null;
        try {
            bookScene = loader.load();
            bookController = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bookController.setBook(book);
        if (tutorMod) bookController.setTutorMod(true);
        rec_pane.getChildren().add(bookScene);
    }

    public void pushBackMyBook(WordBook book) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("book.fxml"));
        VBox bookScene = null;
        Book bookController = null;
        try {
            bookScene = loader.load();
            bookController = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bookController.setBook(book);
        my_pane.getChildren().add(bookScene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        search_font.setIconCode(FontAwesomeSolid.SEARCH);
        search_btn.setOnAction(actionEvent -> {search();});
        search_field.setOnKeyPressed(keyEvent -> {if(keyEvent.getCode() == KeyCode.ENTER) search();});
        refreshAllBooks();
        pushBackCreator();
        simpleMod.run();
    }

    private void pushBackCreator() {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("book.fxml"));
        VBox bookScene = null;
        Book bookController = null;
        try {
            bookScene = loader.load();
            bookController = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bookController.setToAddNew();
        my_pane.getChildren().add(bookScene);
    }

    private void search() {
        rec_pane.getChildren().clear();
        WordBookManager.getBooks().forEach((s, wordBook) -> {
            if (wordBook.getTitle().contains(search_field.getCharacters()))
                pushBackRecommandBook(wordBook);
        });
    }
}
