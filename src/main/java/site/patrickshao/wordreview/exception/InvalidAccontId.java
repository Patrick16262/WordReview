package site.patrickshao.wordreview.exception;

import java.io.IOException;

public class InvalidAccontId extends IOException {
    public InvalidAccontId() {
    }

    public InvalidAccontId(String message) {
        super(message);
    }

    public InvalidAccontId(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAccontId(Throwable cause) {
        super(cause);
    }
}
