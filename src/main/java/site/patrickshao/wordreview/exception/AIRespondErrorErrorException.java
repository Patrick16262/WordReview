package site.patrickshao.wordreview.exception;

public class AIRespondErrorErrorException extends Exception{
    public AIRespondErrorErrorException() {
        super();
    }

    public AIRespondErrorErrorException(String message) {
        super(message);
    }

    public AIRespondErrorErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public AIRespondErrorErrorException(Throwable cause) {
        super(cause);
    }

    protected AIRespondErrorErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
