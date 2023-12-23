module net.patrickshao.wordreview.wordreviewgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;

    exports net.patrickshao.wordreview.constants;
    exports net.patrickshao.wordreview.controller;
    exports net.patrickshao.wordreview.manager;

    opens net.patrickshao.wordreview.controller to javafx.fxml;
}