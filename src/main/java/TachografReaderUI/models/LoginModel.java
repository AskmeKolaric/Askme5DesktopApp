package TachografReaderUI.models;

import java.io.Serializable;

public class LoginModel implements Serializable {

    private String email;
    private String password;

    public LoginModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginModel{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
