package site.patrickshao.wordreview.exception;

import java.io.IOException;

public final class FileMissingException extends IOException {
    public FileMissingException() {
    }

    public FileMissingException(String message) {
        super(message);
    }

    public FileMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileMissingException(Throwable cause) {
        super(cause);
    }
}
