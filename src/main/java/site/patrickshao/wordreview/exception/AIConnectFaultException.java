package site.patrickshao.wordreview.exception;

import java.io.IOException;

public final class AIConnectFaultException extends IOException {
    public AIConnectFaultException() {
    }

    public AIConnectFaultException(String message) {
        super(message);
    }

    public AIConnectFaultException(String message, Throwable cause) {
        super(message, cause);
    }

    public AIConnectFaultException(Throwable cause) {
        super(cause);
    }
}
