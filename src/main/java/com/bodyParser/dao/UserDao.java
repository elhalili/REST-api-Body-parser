package com.bodyParser.dao;

import com.bodyParser.models.User;

import java.util.ArrayList;

public interface UserDao {
    public void save(User user);
    public ArrayList<User> getAll();
    public User getByUsername(String username);
    public void delete(String username);
}
