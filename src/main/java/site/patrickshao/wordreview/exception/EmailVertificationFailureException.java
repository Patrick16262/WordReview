package site.patrickshao.wordreview.exception;

public class EmailVertificationFailureException extends Exception {
    public EmailVertificationFailureException() {
    }

    public EmailVertificationFailureException(String message) {
        super(message);
    }

    public EmailVertificationFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailVertificationFailureException(Throwable cause) {
        super(cause);
    }

    public EmailVertificationFailureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
