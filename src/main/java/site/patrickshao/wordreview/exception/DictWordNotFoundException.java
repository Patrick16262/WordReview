package site.patrickshao.wordreview.exception;

import java.io.IOException;

public final class DictWordNotFoundException extends IOException {
    public DictWordNotFoundException() {
    }

    public DictWordNotFoundException(String message) {
        super(message);
    }

    public DictWordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DictWordNotFoundException(Throwable cause) {
        super(cause);
    }
}
