package site.patrickshao.wordreview.exception;

import java.io.IOException;

public class DataBaseExistException extends IOException {
    public DataBaseExistException() {
    }

    public DataBaseExistException(String message) {
        super(message);
    }

    public DataBaseExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataBaseExistException(Throwable cause) {
        super(cause);
    }
}
