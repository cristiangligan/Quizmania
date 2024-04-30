package model;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Users {
    private int id;
    private String username;
    private String password;
    private Date createdAt;

    public Users(int id, String username, String password, Date createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createdDateString = dateFormat.format(createdAt);
        return String.format("Username: %s%nPassword: %s%nCreated: %s%n", username, password, createdDateString);
    }


}
