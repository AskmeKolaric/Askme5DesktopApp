package TachografReaderUI.file.error;

public class ExceptionBlockRequired extends Exception {
    public ExceptionBlockRequired() {
        super();
    }

    public ExceptionBlockRequired(String message) {
        super(message);
    }

    public ExceptionBlockRequired(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionBlockRequired(Throwable cause) {
        super(cause);
    }

    protected ExceptionBlockRequired(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
