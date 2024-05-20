package site.patrickshao.wordreview.cache.dict;

import lombok.NonNull;
import site.patrickshao.wordreview.cache.database.Database;
import site.patrickshao.wordreview.dictionaries.entity.basic.Word;
import site.patrickshao.wordreview.exception.DictWordNotFoundException;
import site.patrickshao.wordreview.exception.NotCachedException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

public interface DictCacheDB extends Database {
    @NonNull
    public Word lookupWord(@NonNull String englishWord) throws DictWordNotFoundException, NotCachedException;
    @NonNull
    public String getId();
    @NonNull
    public Database getDatabase();
    @Override
    default boolean excute(String command) {
        return getDatabase().excute(command);
    };
    @Override
    default String getUrl() {
        return getDatabase().getUrl();
    };
    @Override
    default Connection getConnection() {
        return getDatabase().getConnection();
    };
    @Override
    default ResultSet query(String command) {
        return getDatabase().query(command);
    };
    @Override
    default void close() throws IOException {
        getDatabase().close();
    };
    public boolean addword(Word word);
}
