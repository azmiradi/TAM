package azmithabet.com.tam.model;

public class User {
    // this class for operation that user make it like (login,registration)
    private String email;
    private String password;
    private String fcmToken;
    public User(){}
    public User(String email, String password,String fcmToken) {
        this.email = email;
        this.password = password;
        this.fcmToken=fcmToken;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFcmToken() {
        return fcmToken;
    }
}
