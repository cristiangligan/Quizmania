package model;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Users {
    private String username;
    private int password;
    private Date createdAt;

    public Users(String username, int password, Date createdAt) {
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public int getPassword() {
        return password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createdDateString = dateFormat.format(createdAt);
        return String.format("Username: %s%nPassword: %d%nCreated: %s%n", username, password, createdDateString);
    }


}
