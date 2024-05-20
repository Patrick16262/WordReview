package site.patrickshao.wordreview.cache.database;

import lombok.*;
import site.patrickshao.wordreview.cache.dict.DictCacheDB;
import site.patrickshao.wordreview.dictionaries.dicts.OnlineDict;
import site.patrickshao.wordreview.exception.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataBases {


    public static Database openDataBase(String url_database) {
        var ept = !Files.exists(Path.of(url_database));
        var db = new Database() {
            @Getter
            private final String url = url_database;
            @Getter
            private Connection connection;
            @Getter
            private boolean empty = ept;

            @Override
            public void open() throws SQLException {
                connection = DriverManager.getConnection("jdbc:sqlite:" + url);
            }

            @Override
            public void close() throws IOException {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new FileMissingException(e);
                }
            }

            @Override
            public boolean excute(String command) {
                try {
                    var state = connection.createStatement();
                    state.execute(command);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }

            @Override
            public ResultSet query(String command) {
                try {
                    var state = connection.createStatement();
                    return state.executeQuery(command);
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            public boolean delete() {
                if (!Files.exists(Path.of(url))) return true;
                try {
                    this.close();
                    Files.delete(Path.of(url));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        };
        try {
            db.open();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return db;
    }

    //如果原缓存存在会删掉原缓存
    public static void CachDict(OnlineDict dict) throws DictConnFaultException, FileMissingException {
//        try {
//            var allwords = dict.allwords();
//            @Cleanup
//            Database db = openDataBase(dict_dir + dict_cache_name);
//            db.excute("drop table " + dict.getId());
//            db.excute("create table " + dict.getId() + "(word varchar primary key, json text);");
//            Object lock = new Object();
//            var cache = openCachedDict(dict.getId());
//            var pool = Executors.newFixedThreadPool(10);
//            String str;
//            AtomicReference<Integer> i = new AtomicReference<>(0);
//            for (var c : allwords) {
//                while (true) {
//                    pool.submit(() -> {
//                        try {
//                            var word = dict.lookupWord(c);
//                            System.out.println(i.get() + ". " + c + ": " + cache.addword(word));
//                            i.getAndSet(i.get() + 1);
//                        } catch (DictWordNotFoundException e) {
//                            System.out.println(c + ": Not Found!");
//                        } catch (DictConnFaultException e) {
//                            System.out.println(c + ": No Connection");
//                        }
//                    });
//                    break;
//                }
//
//            }
//        } catch (IOException e) {
//            throw new FileMissingException(e);
//        }
    }

    @NonNull
    public static DictCacheDB openCachedDict(String dict_id) throws NotCachedException, FileMissingException {
//        var db = DataBases.openDataBase(dict_dir + dict_cache_name);
//        if (db.query("SELECT name FROM sqlite_master WHERE type='table' AND name='" + dict_id + "'") == null) {
//            throw new NotCachedException(dict_id);
//        }
//        ;
//        var cacheDB = new DictCacheDB() {
//            @Override
//            public boolean isEmpty() {
//                return db.isEmpty();
//            }
//
//            @Override
//            public boolean delete() {
//                return db.delete();
//            }
//
//            private final String id = dict_id;
//            @Getter
//            private final Database database = db;
//
//            @Override
//            public @NonNull Word lookupWord(@NonNull String englishWord) throws DictWordNotFoundException {
//                var set = database.query("Select * from " + dict_id + " where word ='" + englishWord + "'");
//                Word word = null;
//                try {
//                    var str = set.getString("json");
//                    if (str == null) throw new DictWordNotFoundException();
//                    word = new Gson().fromJson(str, Word.class);
//                } catch (SQLException e) {
//                    throw new DictWordNotFoundException(e);
//                }
//                if (word == null) throw new DictWordNotFoundException(englishWord);
//                return word;
//            }
//
//            @Override
//            public @NonNull String getId() {
//                return id;
//            }
//
//            @Override
//            public boolean addword(Word word) {
//                var json = new Gson().toJson(word);
//                try {
//                    var state = database.getConnection().prepareStatement("insert into " + dict_id + "(word, json) values (?, ?)");
//                    state.setString(1, word.getName_en());
//                    state.setString(2, json);
//                    if (state.executeUpdate() > 0) return true;
//                    else return false;
//                } catch (Exception e) {
//                    return false;
//                }
//            }
//        };
//        return cacheDB;
        return null;
    }

}
