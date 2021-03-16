package TachografReaderUI.models;

import java.io.Serializable;

public class AuthModel implements Serializable {

    private boolean enabled;
    private String email;
    private String passwordHash;
    private boolean firstTime;

    public AuthModel(boolean enabled, String email, String passwordHash, boolean firstTime) {
        this.enabled = enabled;
        this.email = email;
        this.passwordHash = passwordHash;
        this.firstTime = firstTime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }
}
