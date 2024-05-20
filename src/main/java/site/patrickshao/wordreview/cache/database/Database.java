package site.patrickshao.wordreview.cache.database;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Database extends Closeable {
    public boolean excute(String command);
    public String getUrl();
    public Connection getConnection();
    public ResultSet query(String command);
    public boolean isEmpty();
    public boolean delete();
    public void open() throws SQLException;
}
