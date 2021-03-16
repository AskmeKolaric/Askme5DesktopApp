package TachografReaderUI.file.error;

public class ExceptionSignatureContent extends Exception {
    public ExceptionSignatureContent() {
        super();
    }

    public ExceptionSignatureContent(String message) {
        super(message);
    }

    public ExceptionSignatureContent(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionSignatureContent(Throwable cause) {
        super(cause);
    }

    protected ExceptionSignatureContent(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
