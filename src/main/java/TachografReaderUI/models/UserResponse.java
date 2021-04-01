package TachografReaderUI.models;

import java.io.Serializable;
import TachografReaderUI.models.UserModel;

public class UserResponse implements Serializable {

    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
