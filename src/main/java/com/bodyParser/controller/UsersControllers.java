package com.bodyParser.controller;

import com.bodyParser.body.utils.BodyUtils;
import com.bodyParser.dao.UserDao;
import com.bodyParser.dao.UserDaoImpl;
import com.bodyParser.models.User;
import com.google.gson.Gson;
import com.pfe.easyServlet.EasyServlet;
import jakarta.servlet.annotation.WebServlet;

import java.io.BufferedReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

@WebServlet("/*")
public class UsersControllers extends EasyServlet {
    private Gson gson = new Gson();
    private UserDao userDao = new UserDaoImpl();
    @Override
    protected void execute() {
        get("/", (req, res) -> {
            ArrayList<User> users = userDao.getAll();
            String json = gson.toJson(users);

            res.getWriter().print(json);
        });
        get("/:username", (req, res) -> {
            User user = userDao.getByUsername((String) req.getAttribute("username"));
            String json = gson.toJson(user);

            res.getWriter().print(json);
        });
        post("/", (req, res) -> {
            // body parser .......................................................................
            BufferedReader reader = req.getReader();
            BodyUtils bodyUtils = new BodyUtils(reader, req.getContentType(), req.getCharacterEncoding());
            Map<String, String> body = bodyUtils.getParsedBody();


            String username = body.get("username");
            String password = body.get("password");

            //-------------------------------------------------------------------------------------
            User user = new User(username, password);
            userDao.save(user);

            String json = gson.toJson(user);
            res.getWriter().print(json);
        });
        put("/:username", (req, res) -> {
            BufferedReader reader = req.getReader();
            BodyUtils bodyUtils = new BodyUtils(reader, req.getContentType(), req.getCharacterEncoding());
            Map<String, String> body = bodyUtils.getParsedBody();

            String password = body.get("password");

            String username = (String) req.getAttribute("username");

            User user = userDao.getByUsername(username);
            user.setPassword(password);

            String json = gson.toJson(user);
            res.getWriter().print(json);
        });

        delete("/:username", (req, res) -> {
            String username = (String) req.getAttribute("username");

            userDao.delete(username);

            String json = gson.toJson(userDao.getAll());
            res.getWriter().print(json);
        });
    }
}
