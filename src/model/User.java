package model;

import java.util.UUID;

public class User {
    private String id;
    private String name;
    private String email;
    private String username;
    private String password;
    private String userType;

    public User(String name, String email, String username, String password, String userType) {
        this.id = generateUniqueID();
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public User() {
    }

    public String getId() {
        return id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }

    private String generateUniqueID() {
        return UUID.randomUUID().toString();
    }
}
