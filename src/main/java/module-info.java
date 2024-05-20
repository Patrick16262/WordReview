module net.patrickshao.wordreview.wordreviewgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires lombok;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
    requires rxcontrols;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.apache.poi.ooxml.schemas;
    requires org.apache.poi.scratchpad;
    requires com.google.gson;
    requires java.sql;
    requires java.net.http;
    requires okhttp3;
    requires android.json;
    requires org.apache.commons.compress;
    requires annotations;

    exports site.patrickshao.wordreview.constants;
    exports site.patrickshao.wordreview.gui.controller;
    exports site.patrickshao.wordreview.gui.manager;
    exports site.patrickshao.wordreview.cache.dict;
    exports site.patrickshao.wordreview.cache.database;
    exports site.patrickshao.wordreview.cache.user;
    exports site.patrickshao.wordreview.exception;
//    exports net.patrickshao.wordreview.dictionaries.wordbook;
    exports site.patrickshao.wordreview.dictionaries.util;
    exports site.patrickshao.wordreview.dictionaries.dicts;
    exports site.patrickshao.wordreview.dictionaries.entity.basic;
    exports site.patrickshao.wordreview.dictionaries.entity;
    exports site.patrickshao.wordreview.gui.controller.tutor;
    exports site.patrickshao.wordreview.gui.controller.dialog;
    exports site.patrickshao.wordreview.dictionaries.manager;
    exports site.patrickshao.wordreview.gui.controller.word;

    opens site.patrickshao.wordreview.dictionaries.entity.basic to com.google.gson;
    opens site.patrickshao.wordreview.dictionaries.util to com.google.gson;
    opens site.patrickshao.wordreview.dictionaries.entity to com.google.gson;
    opens site.patrickshao.wordreview.user to com.google.gson;
    opens site.patrickshao.wordreview.dictionaries.manager to com.google.gson;
    opens site.patrickshao.wordreview.gui.controller to javafx.fxml;
    opens site.patrickshao.wordreview.gui.controller.dialog to javafx.fxml;
    opens site.patrickshao.wordreview.gui.controller.tutor to  javafx.fxml;
    opens site.patrickshao.wordreview.gui.controller.word to  javafx.fxml;
    exports site.patrickshao.wordreview.user.entity;
    opens site.patrickshao.wordreview.user.entity to com.google.gson;
    exports site.patrickshao.wordreview.user;

}