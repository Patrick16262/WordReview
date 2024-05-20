package site.patrickshao.wordreview.exception;

import java.io.IOException;

public final class DictConnFaultException extends IOException {
    public DictConnFaultException() {
    }

    public DictConnFaultException(String message) {
        super(message);
    }

    public DictConnFaultException(String message, Throwable cause) {
        super(message, cause);
    }

    public DictConnFaultException(Throwable cause) {
        super(cause);
    }
}
