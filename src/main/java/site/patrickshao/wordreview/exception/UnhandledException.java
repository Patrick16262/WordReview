package site.patrickshao.wordreview.exception;

public final class UnhandledException extends Error{
    public UnhandledException() {
    }

    public UnhandledException(String message) {
        super(message);
    }

    public UnhandledException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnhandledException(Throwable cause) {
        super(cause);
    }

    public UnhandledException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
