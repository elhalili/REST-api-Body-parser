package com.bodyParser.dao;

import com.bodyParser.models.User;

import java.util.ArrayList;

public class UserDaoImpl implements UserDao {
    @Override
    public void save(User user) {
        ArrayList<User> users = User.users;
        for (User item: users) {
            if (item.getUsername().equals(user.getUsername())) {
                item.setPassword(user.getPassword());
                return;
            }
        }

        users.add(user);
    }

    @Override
    public ArrayList<User> getAll() {
        return User.users;
    }

    @Override
    public User getByUsername(String username) {
        ArrayList<User> users = User.users;
        for (User item: users) {
            if (item.getUsername().equals(username)) return item;
        }

        return null;
    }

    @Override
    public void delete(String username) {
        ArrayList<User> users = User.users;
        for (User item: users) {
            if (item.getUsername().equals(username)) {
                users.remove(item);
                return;
            }
        }
    }
}
