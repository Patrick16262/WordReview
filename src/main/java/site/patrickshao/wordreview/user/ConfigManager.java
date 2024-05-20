package site.patrickshao.wordreview.user;

import com.google.gson.Gson;
import lombok.*;
import site.patrickshao.wordreview.constants.Constants;
import site.patrickshao.wordreview.exception.*;
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
        try {
            saveBasicConfig();
        } catch (IOException e) {
            throw new UnhandledException(e) ;
        }
    }

    public static void saveBasicConfig() throws IOException{
        var basicJson = new Gson().toJson(basicConfig);
        try (var writer = Files.newBufferedWriter(Path.of(Constants.Files.basicConfig))) {
            writer.write(basicJson);
            writer.flush();
        }
    }

    public static void setDefaultBasicConfig() {
        userDataBase = UserDataBase.openOfflineDatebase();
        basicConfig = new BasicConfig(false, -1, null);
        needTutor = true;
        try {
            saveBasicConfig();
        } catch (IOException e) {
            throw new UnhandledException(e);
        }
    }
    public static void setDefaultConfig() {
        config = new Config();
        config.allWordConfig = new Config.WordConfig(true,
                new int[]{1, 3, 7, 12},
                NewWordMethod.ReadLearn,
                new LearnMethod[]{LearnMethod.Selection_En, LearnMethod.Selection_Trans, LearnMethod.WriteEnglish}
        ,new LearnMethod[]{LearnMethod.Selection_En, LearnMethod.Selection_Trans});
        config.differsImportance = false;
        config.tranStyle = TranStyle.single_syno;
        config.word_num_per_day = 10;
        config.speechType = SpeechType.US;
        bookManager = MyBookManagerObj.createEmptyObj();
        bookManager.refreshLearned();

    }

    public static void loadConfigs() throws IOException {
        if (!Files.exists(Path.of(Constants.Files.basicConfig))) {
            setDefaultBasicConfig();
            setDefaultConfig();
        }
        else {
            basicConfig = new Gson().fromJson(Files.newBufferedReader(Path.of(Constants.Files.basicConfig)), BasicConfig.class);
            if (basicConfig.onlineAccont) {
                userDataBase = null;
                try {
                    userDataBase = Users.login(basicConfig.user_id, basicConfig.encrypted_password);
                } catch (InvalidAccontPassword | TooMuchOperationException | EmailVertificationFailureException |
                         InvalidEmailName e) {
                    setDefaultConfig();
                    return;
                }
            } else {
                userDataBase = UserDataBase.openOfflineDatebase();
            }
            loadDataBaseConfig();
        }
    }

    public static void loadDataBaseConfig() throws IOException {
        String[] jsons= userDataBase.loadConfig();
        if (jsons == null) {
            if (config == null) setDefaultConfig();
            else saveConfig();
            return;
        }
        config = new Gson().fromJson(jsons[0], Config.class);
        bookManager = MyBookManagerObj.fromJson(jsons[1]);
    }

    public static void saveConfig() throws IOException{
        saveBasicConfig();
        var json1 = new Gson().toJson(config);
        var json2 = bookManager.toJson();
        userDataBase.saveConfig(new String[] {json1, json2});
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
