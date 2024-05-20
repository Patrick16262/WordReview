package site.patrickshao.wordreview.exception;

import java.io.IOException;

public final class NotCachedException extends IOException {
    public NotCachedException() {
    }

    public NotCachedException(String message) {
        super(message);
    }

    public NotCachedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotCachedException(Throwable cause) {
        super(cause);
    }
}
