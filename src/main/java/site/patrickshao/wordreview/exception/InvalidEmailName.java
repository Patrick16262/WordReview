package site.patrickshao.wordreview.exception;

public class InvalidEmailName extends Exception{
    public InvalidEmailName() {
    }

    public InvalidEmailName(String message) {
        super(message);
    }

    public InvalidEmailName(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEmailName(Throwable cause) {
        super(cause);
    }

    public InvalidEmailName(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
