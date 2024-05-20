package site.patrickshao.wordreview.exception;

public class TooMuchOperationException extends Exception{
    public TooMuchOperationException() {
    }

    public TooMuchOperationException(String message) {
        super(message);
    }

    public TooMuchOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooMuchOperationException(Throwable cause) {
        super(cause);
    }

    public TooMuchOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
