package site.patrickshao.wordreview.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import lombok.Setter;
import site.patrickshao.wordreview.dictionaries.entity.WordBook;
import site.patrickshao.wordreview.gui.controller.dialog.ConfigBook;
import site.patrickshao.wordreview.gui.controller.dialog.DialogType;
import site.patrickshao.wordreview.gui.manager.ImageManager;
import site.patrickshao.wordreview.gui.manager.SceneManager;

import java.net.URL;
import java.util.ResourceBundle;

public class Book implements Initializable {
    @FXML
    private ImageView book_image;
    @FXML
    private Label book_label;
    private WordBook book;
    private boolean addnew = false;
    @Setter
    private boolean tutorMod = false;

    public void setBook(WordBook book) {
        this.book = book;
        book_image.setImage(ImageManager.getImage(book.getId()));
        book_label.setText(book.getTitle());
    }

    public void setToAddNew() {
        book_image.setImage(ImageManager.getImage("add_new"));
        book_label.setText("点我新建词书");
        book_label.setFont(new Font(14));
        addnew = true;
    }

    public void clicked() {
        if (addnew) {
            return;
        }

        ConfigBook controller = (ConfigBook) SceneManager.showDialog(DialogType.CREATE_BOOK);
        controller.setBookInfo(book);
        if (tutorMod) {
            controller.setTutorMod(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        book_image.setOnMouseClicked(event -> {
            clicked();
        });
    }
}
