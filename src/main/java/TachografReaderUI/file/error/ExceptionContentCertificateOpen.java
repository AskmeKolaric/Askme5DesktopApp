package TachografReaderUI.file.error;

public class ExceptionContentCertificateOpen extends Exception {
    public ExceptionContentCertificateOpen() {
        super();
    }

    public ExceptionContentCertificateOpen(String message) {
        super(message);
    }

    public ExceptionContentCertificateOpen(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionContentCertificateOpen(Throwable cause) {
        super(cause);
    }

    protected ExceptionContentCertificateOpen(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
