package site.patrickshao.wordreview.user;

import com.google.gson.Gson;
import lombok.Data;
import lombok.Getter;
import site.patrickshao.wordreview.cache.database.DataBases;
import site.patrickshao.wordreview.cache.database.Database;
import site.patrickshao.wordreview.constants.Constants;
import site.patrickshao.wordreview.exception.*;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;


@Getter
public class UserDataBase implements Comparable<UserDataBase> {
    //todo
//    private final String uri = Web.userServer;
//    private final String uri = "http://127.0.0.1:2334";
    private Database database;
    private final int id;
    private final String encrypted_password;
    private static final HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofMillis(2000)).build();

    public UserDataBase(int id, String encryptedPassword) throws InvalidAccontPassword, InvalidAccontId, TooMuchOperationException, ConnectException {
        this.id = id;
        this.encrypted_password = encryptedPassword;
        int code = 0;
        if (Users.isExist(id))
            if (!Users.authenticate(id, encrypted_password))
                code = 505;
            else
                code = getFromServer();
        if (code == 404)
            throw new InvalidAccontId();
        if (code == 505)
            throw new InvalidAccontPassword();
        if (code == 606)
            throw new TooMuchOperationException();

        database = DataBases.openDataBase(Constants.DateBase.user_dir + Integer.toString(id) + Constants.DateBase.filename_suffix);
    }

    public UserDataBase(int id, String encryptedPassword, boolean isLocal) throws InvalidAccontPassword, InvalidAccontId, TooMuchOperationException, ConnectException {
        this.id = id;
        this.encrypted_password = encryptedPassword;
        int code = 200;
        if (isLocal) {
            if (!Users.authenticate(id, encrypted_password))
                code = 505;
        } else
            code = getFromServer();
        if (code == 404)
            throw new InvalidAccontId();
        if (code == 505)
            throw new InvalidAccontPassword();
        if (code == 606)
            throw new TooMuchOperationException();
        if (code != 200)
            throw new UnhandledException(String.valueOf(code));
        database = DataBases.openDataBase(Constants.DateBase.user_dir
                + Integer.toString(id) + Constants.DateBase.filename_suffix);
    }

    private UserDataBase(String path) throws IOException {
        if (!Files.exists(Path.of(path))) throw new IOException();
        database = DataBases.openDataBase(path);
        var rs = database.query("select * from basic");
        try {
            rs.next();
            encrypted_password = rs.getString("encrypted_password");
            id = rs.getInt("id");
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    private UserDataBase() {
        id = -1;
        encrypted_password = null;
        Runnable program = null;
        if (Files.exists(Path.of(Constants.Files.offline_datebase))) {
            program = () -> getDatabase().excute("create table basic(timestamp datetime)");
        }
        database = DataBases.openDataBase(Constants.Files.offline_datebase);
        if (program != null) program.run();
    }

    public static UserDataBase open(Path path) throws IOException {
        return new UserDataBase(path.toString());
    }

    public static UserDataBase openOfflineDatebase() {
        return new UserDataBase();
    }

    public static UserDataBase openLocalDateBase(int id, String encrypted_password) throws InvalidAccontPassword {
        UserDataBase db = null;
        try {
            db = new UserDataBase(id, encrypted_password, true);
        } catch (InvalidAccontId e) {
            throw new UnhandledException(e);
        } catch (TooMuchOperationException e) {
            throw new UnhandledException(e);
        } catch (ConnectException e) {
            throw new UnhandledException(e);
        }
        return db;
    }

    public void upload() throws ConnectException, TooMuchOperationException {
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .PUT(HttpRequest.BodyPublishers.ofFile(Path.of(Constants.DateBase.user_dir + String.valueOf(id) + Constants.DateBase.filename_suffix)))
                    .uri(Constants.Web.userServer)
                    .setHeader("UploadFile", "I like cat")
                    .setHeader("id", String.valueOf(id))
                    .setHeader("Password", encrypted_password).build();
        } catch (FileNotFoundException e) {
            throw new UnhandledException(e);
        }
        HttpResponse response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (Exception e) {
            throw new ConnectException(e.getMessage());
        }
        if (response.statusCode() == 606) {
            throw new TooMuchOperationException();
        }
        if (response.statusCode() != 200) {
            throw new UnhandledException();
        }
    }

    public void syncWithServer() throws ConnectException, TooMuchOperationException {
//        getFromServer();
//        database = DataBases.openDataBase(Constants.DateBase.user_dir
//                + Integer.toString(id) + Constants.DateBase.filename_suffix);
        upload();
        return;
    }

    private int getFromServer() throws ConnectException {
        Path temp_db = Path.of(Constants.DateBase.user_dir + "temp" + id + Constants.DateBase.filename_suffix);
        var request = HttpRequest.newBuilder().uri(Constants.Web.userServer)
                .setHeader("RequestFile", "UserDataBase")
                .method("GET", HttpRequest.BodyPublishers.ofString(
                        new Gson().toJson(new UserInfo(id, encrypted_password), UserInfo.class)
                )).build();
        HttpResponse response = null;
        try {
            response = client.send(request, HttpResponse.
                    BodyHandlers.ofFile(temp_db));
        } catch (Exception e) {
            throw new ConnectException(e.getMessage());
        }
        if (response.statusCode() != 200) {
            try {
                Files.deleteIfExists(Path.of(Constants.DateBase.user_dir + "temp" + id + Constants.DateBase.filename_suffix));
                return response.statusCode();
            } catch (IOException e) {
                throw new UnhandledException(e);
            }
        }
        try {
            Files.move(Path.of(Constants.DateBase.user_dir + "temp" + id + Constants.DateBase.filename_suffix)
                    , Path.of(Constants.DateBase.user_dir + id + Constants.DateBase.filename_suffix)
                    , StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new UnhandledException(e);
        }
        return response.statusCode();
    }


    public void saveConfig(String[] args) throws IOException {
        var rs = database.query("SELECT name FROM sqlite_master WHERE type='table' AND name='config';");
        String str = new Gson().toJson(args);
        try {
            if (!rs.next()) {
                database.excute("create table config(content varchar)");
                database.excute("insert into config(content) values ('" + str + "')");
            } else {
                database.excute("update config set content = '" + str + "'");
            }
            database.excute("update basic set timestamp = CURRENT_TIMESTAMP ");
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public String[] loadConfig() throws IOException {
        var rs = database.query("SELECT content FROM config");
        String str = null;
        if (rs == null) return null;
        try {
            rs.next();
            str = rs.getString("content");
        } catch (SQLException e) {
            throw new IOException(e);
        }
        return new Gson().fromJson(str, String[].class);
    }


    @Override
    public int compareTo(@NotNull UserDataBase o) {
        Date thisDate;
        Date otherDate;
        var rs = database.query("select timestamp from basic");
        try {
            thisDate = rs.getDate("timestamp");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        rs = o.getDatabase().query("select timestamp from basic");
        try {
            otherDate = rs.getDate("timestamp");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return thisDate.compareTo(otherDate);
    }

    public LocalDateTime getTimeStamp() {
        Timestamp thisDate;
        var rs = database.query("select timestamp from basic");
        try {
            thisDate = rs.getTimestamp("timestamp");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return thisDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    @Data
    private static class UserInfo {
        private final int id;
        private final String encrypted_password;
    }
}
