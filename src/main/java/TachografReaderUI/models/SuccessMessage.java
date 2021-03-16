package TachografReaderUI.models;

import java.io.Serializable;

public class SuccessMessage implements Serializable {

    private String poruka;
    private String message;

    public SuccessMessage() {
    }

    public SuccessMessage(String poruka, String message) {
        this.poruka = poruka;
        this.message = message;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}