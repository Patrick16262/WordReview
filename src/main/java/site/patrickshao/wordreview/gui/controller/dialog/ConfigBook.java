package site.patrickshao.wordreview.gui.controller.dialog;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import site.patrickshao.wordreview.dictionaries.entity.WordBook;
import site.patrickshao.wordreview.dictionaries.entity.basic.Word;
import site.patrickshao.wordreview.dictionaries.util.Texts;
import site.patrickshao.wordreview.gui.manager.ApplicationManager;
import site.patrickshao.wordreview.gui.manager.ImageManager;
import site.patrickshao.wordreview.gui.manager.SceneManager;
import site.patrickshao.wordreview.gui.manager.TutorSceneManager;
import site.patrickshao.wordreview.user.ConfigManager;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ConfigBook implements Initializable {
    @FXML
    private TextField title_field;
    @FXML
    private TextArea content_field;
    @FXML
    private TableView<Item> word_table;
    @FXML
    private TableColumn<Item, String> word_col;
    @FXML
    private TableColumn<Item, Word> meaning_col;
    @FXML
    private ImageView book_image;
    @FXML
    private Button confirm_btn;
    @FXML
    private Button cancel_btn;
    private boolean tutorMod = false;

    private WordBook book;
    private ObservableList<Item> items = FXCollections.observableArrayList();

    public void setTutorMod(boolean tutorMod) {
        confirm_btn.setText("添加");
        this.tutorMod = tutorMod;
    }

    public void setBookInfo(WordBook book) {
        this.book = book;
        ApplicationManager.getBackgroundExecutor().execute(() -> {
            book.getWords().forEach((str, word) -> {
                Arrays.stream(word.translations).forEach(translation ->
                    Platform.runLater(() -> items.add(new Item(str, Texts.TransToString(translation))))
                );
            });
        });
        word_table.setItems(items);
        book_image.setImage(ImageManager.getImage(book.getId()));
        title_field.setText(book.getTitle());
        content_field.setText(book.getIntroduce());
        word_col.setCellValueFactory(new PropertyValueFactory<>("word"));
        meaning_col.setCellValueFactory(new PropertyValueFactory<>("tran"));
    }

    public void conform() {
        ConfigManager.getBookManager().setBook(book.getId());
        SceneManager.refreshHomeView();
        if (tutorMod) TutorSceneManager.nextPage();
        cancel_btn.getOnAction().handle(new ActionEvent());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        confirm_btn.setOnAction(event -> conform());
        cancel_btn.setOnAction(event -> SceneManager.closeDialog());
    }

    @EqualsAndHashCode@Getter@AllArgsConstructor
    public static class Item {
        private String word;
        private String tran;
    }
}
