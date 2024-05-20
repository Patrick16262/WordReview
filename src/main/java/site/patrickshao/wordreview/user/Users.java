package site.patrickshao.wordreview.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import site.patrickshao.wordreview.constants.Constants;
import site.patrickshao.wordreview.exception.*;

import java.net.ConnectException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.SQLException;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Users {

    public static boolean isExist(int id) {
        return Files.exists(Path.of(Constants.DateBase.user_dir + id + Constants.DateBase.filename_suffix));
    }

    public static boolean authenticate(int id, String encrypted_password) {
        try {
            var conn = DriverManager.getConnection(Constants.DateBase.prefix_driver  + Constants.DateBase.user_dir + id + Constants.DateBase.filename_suffix);
            var stmt = conn.createStatement();
            var rs = stmt.executeQuery("select * from basic");
            if (rs.next()) {
                var pw = rs.getString("encrypted_password");
                if (pw.equals(encrypted_password)) {
                    return true;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public static boolean validateEmail(String email) {
        return email.matches(".{1,50}[@].{1,10}[.].{1,6}");
    }

    public static UserDataBase register(String email, String encrypted_password) throws EmailVertificationFailureException, ConnectException, InvalidEmailName, TooMuchOperationException {
        int id;
        if (!validateEmail(email)) throw new InvalidEmailName();
        HttpResponse res = null;
        try {
            res = HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                    .GET()
                    .uri(Constants.Web.userServer)
                    .setHeader("Registry", "")
                    .setHeader("Email", email)
                    .setHeader("Password", encrypted_password)
                    .build(), HttpResponse.BodyHandlers.discarding());
        } catch (Exception e) {
            throw new ConnectException(e.getMessage());
        }
        int code = res.statusCode();
        if (code == 506)
            throw new EmailVertificationFailureException();
        if (code == 606)
            throw new TooMuchOperationException();
        if (code != 200)
            throw new UnhandledException();
        id = Integer.parseInt(res.headers().allValues("id").get(0));
        try {
            return new UserDataBase(id, encrypted_password, false);
        } catch (InvalidAccontPassword | InvalidAccontId | TooMuchOperationException e) {
            throw new UnhandledException(e);
        }
    }

    public static UserDataBase login(String email, String encrypted_password) throws EmailVertificationFailureException, ConnectException, InvalidEmailName, TooMuchOperationException, InvalidAccontId, InvalidAccontPassword {
        var id = getIdByEmail(email);
        return new UserDataBase(id, encrypted_password, false);
    }
    public static UserDataBase login(int id, String encrypted_password) throws EmailVertificationFailureException, ConnectException, InvalidEmailName, TooMuchOperationException, InvalidAccontId, InvalidAccontPassword {
        return new UserDataBase(id, encrypted_password, false);
    }
    public static String encrypte(String str) {
        try {
            // 创建MD5消息摘要对象
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 计算消息的摘要
            byte[] digest = md.digest(str.getBytes());

            // 将摘要转换为十六进制字符串
            String hexString = bytesToHex(digest);

            System.out.println("MD5 Digest: " + hexString);
            return hexString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getIdByEmail(String Email) throws InvalidEmailName, ConnectException, TooMuchOperationException, EmailVertificationFailureException {
        HttpRequest request = HttpRequest.newBuilder(Constants.Web.userServer)
                .GET()
                .setHeader("Email", Email)
                .setHeader("GetId", "I like cat")
                .build();
        HttpResponse response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.discarding());
        } catch (Exception e) {
            throw new ConnectException(e.getMessage());
        }
        if (response.statusCode() == 506) {
            throw new EmailVertificationFailureException();
        }
        if (response.statusCode() == 606) {
            throw new TooMuchOperationException();
        }
        if (response.statusCode() != 200) {
            throw new UnhandledException();
        }
        return Integer.parseInt(response.headers().firstValue("Id").get());
    }
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
