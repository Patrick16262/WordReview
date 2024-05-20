package site.patrickshao.wordreview.user;

import com.google.gson.Gson;
import lombok.*;
import site.patrickshao.wordreview.constants.Constants;
import site.patrickshao.wordreview.exception.InvalidAccontPassword;
import site.patrickshao.wordreview.user.entity.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@EqualsAndHashCode
public class ConfigManager {
    @Getter
    private static Config config;
    @Getter
    private static BasicConfig basicConfig;
    @Getter
    private static boolean needTutor = false;
    @Getter
    private static UserDataBase userDataBase;
    @Getter
    private static MyBookManagerObj bookManager;


    public static void setUser(UserDataBase user) {
        userDataBase = user;

        basicConfig.onlineAccont = true;
        basicConfig.user_id = user.getId();
        basicConfig.encrypted_password = user.getEncrypted_password();
    }


    public static void setDefaultConfig() {
        basicConfig = new BasicConfig(false, -1, null);
        needTutor = true;
        config = new Config();
        config.allWordConfig = new Config.WordConfig(true,
                new int[]{1, 3, 7, 12},
                NewWordMethod.ReadLearn,
                new LearnMethod[]{LearnMethod.Selection_En, LearnMethod.Selection_Trans, LearnMethod.WriteEnglish}
        ,new LearnMethod[]{LearnMethod.Selection_En, LearnMethod.Selection_Trans});
        config.differsImportance = false;
        config.tranStyle = TranStyle.single_trans;
        bookManager = new MyBookManagerObj();
        bookManager.linkAllBooks();
        config.word_num_per_day = 10;
        config.speechType = SpeechType.US;
        userDataBase = UserDataBase.openOfflineDatebase();
    }

    public static void loadConfig() throws IOException {
        if (!Files.exists(Path.of(Constants.Files.basicConfig))) {
            setDefaultConfig();
        }
        else {
            basicConfig = new Gson().fromJson(Files.newBufferedReader(Path.of(Constants.Files.basicConfig)), BasicConfig.class);
            if (basicConfig.onlineAccont) {
                userDataBase = null;
                try {
                    userDataBase = UserDataBase.openLocalDateBase(basicConfig.user_id, basicConfig.encrypted_password);
                } catch (InvalidAccontPassword e) {
                    throw new IOException(e);
                }
            } else {
                userDataBase = UserDataBase.openOfflineDatebase();
            }
            bookManager.linkAllBooks();
        }
    }

    public static void saveConfig() throws IOException{
        new Gson().toJson(basicConfig, Files.newBufferedWriter(Path.of(Constants.Files.basicConfig)));
        var json = new Gson().toJson(config);
        userDataBase.saveConfig(json);
    }


    public static boolean needTutor() {
        return needTutor;
    }
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class BasicConfig {
        public boolean onlineAccont;
        public int user_id;
        public String encrypted_password;
    }


}
