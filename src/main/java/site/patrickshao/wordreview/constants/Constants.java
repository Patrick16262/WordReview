package site.patrickshao.wordreview.constants;

import java.net.URI;

public class Constants {
    public static class DateBase {
        public static final String user_dir = "config/user/";
        public static final String prefix_driver = "jdbc:sqlite:";
        public static final String filename_suffix = "";

    }

    public static class Files {
        public static final String wordbook_index = "asset/wordbook-index/bookLists.json";
        public static final String frenquency_excel = "asset/frequency/frequency60000.xlsx";
        public static final String frenquency_json = "asset/frequency/frequency60000.json";
        public static final String basicConfig = "config/basic.json";
        public static final String offline_datebase = "config/offline";
    }

    public static class Folders {
        public static final String icon = "asset/icon/";
        public static final String font = "asset/font/";
        public static final String wordbook_image = "asset/wordbook-image/";
        public static final String wordbook = "asset/wordbook/";
        public static final String image ="asset/image/";
        public static final String userConfig = "config/user/";
        public static final String ukSpeech = "asset/uk/";
        public static final String usSpeech = "asset/us/";
    }

    public static class Web {
//        public static final URI userServer = "http://206.237.6.142:2334";
        public static final URI userServer = URI.create("http://127.0.0.1:2334");
    }
}
