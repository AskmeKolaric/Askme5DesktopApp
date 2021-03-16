package TachografReaderUI.models;

import java.io.Serializable;

public class UserModel implements Serializable {

    private int id;
    private String name;
    private String email;
    private int paid;

    public UserModel(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int isPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }
}
