package site.patrickshao.wordreview.exception;

public class InvalidAccontPassword extends Exception{
    public InvalidAccontPassword() {
    }

    public InvalidAccontPassword(String message) {
        super(message);
    }

    public InvalidAccontPassword(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAccontPassword(Throwable cause) {
        super(cause);
    }

    public InvalidAccontPassword(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
