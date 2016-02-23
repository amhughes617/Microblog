package com.theironyard;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class Main {

    static HashMap<String, User> users = new HashMap<>();
    static User user;// this static field gets reset each time a new user is created or logged on, reset

    public static void main(String[] args) {
	// write your code here

        Spark.init();
        Spark.get(
                "/",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    if (user == null) {
                        return new ModelAndView(m, "index.html");
                    }
                    else {
                        m.put("name", users.get(user.name).name);
                        if (!users.get(user.name).messages.isEmpty()) {
                            m.put("posts", users.get(user.name).messages);
                        }
                        return new ModelAndView(m, "messages.html");
                    }
                }),
                new MustacheTemplateEngine()
        );
        Spark.post(
                "/create-user",
                ((request, response) -> {
                    String name = request.queryParams("createUser");
                    String password = request.queryParams("createPass");
                    if (!users.containsKey(name)) {
                        user = new User(name, password);
                        users.put(user.name, user);
                        response.redirect("/");
                        return "";
                    }
                    user = users.get(name);
                    if (users.get(user.name).password.equals(password)){
                        response.redirect("/");
                        return "";
                    }
                    else {
                        return "";
                    }
                })
        );
        Spark.post(
                "/create-message",
                ((request, response) -> {
                    Message message = new Message(user.messages.size() + 1, request.queryParams(("createMessage")));
                    user.messages.add(message);
                    response.redirect("/");
                    return "";
                })
        );
        Spark.post(
                "/logout",
                ((request, response) -> {
                    user = null;
                    response.redirect("/");
                    return "";
                })
        );
    }
}
