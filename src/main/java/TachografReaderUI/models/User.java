package TachografReaderUI.models;

public class User {
    private final String userName ;
    private final String token;

    public User(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }

    public String getUserName() {
        return userName ;
    }

    public String getUserToken() {
        return token ;
    }

    @Override
    public String toString() {
        return userName ;
    }
}
