package com.bodyParser.models;

import java.util.ArrayList;

public class User {
    public static ArrayList<User> users = new ArrayList<>();
    private String username;
    private String password;
    public User() {
        this.username = "default";
        this.password = "default";
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
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

}
