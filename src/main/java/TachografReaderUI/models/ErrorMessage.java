package TachografReaderUI.models;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class ErrorMessage implements Serializable {

    private String message;
    private HttpStatus status;

    public ErrorMessage() {
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
