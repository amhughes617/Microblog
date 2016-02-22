package com.theironyard;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class Main {

    static HashMap<String, User> users = new HashMap();

    public static void main(String[] args) {
	// write your code here
        User user = new User();
        Spark.init();
        Spark.get(
                "/",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    if (users.get(user.name) == null) {
                        return new ModelAndView(m, "index.html");
                    }
                    else {
                        m.put("name", users.get(user.name).name);
                        if (users.get(user.name).messages.isEmpty()) {
                            return new ModelAndView(m, "messages.html");
                        }
                        else {
                            m.put("posts", users.get(user.name).messages);
                            return new ModelAndView(m, "messages.html");
                        }
                    }
                }),
                new MustacheTemplateEngine()
        );
        Spark.post(
                "/index",
                ((request, response) -> {
                    String name = request.queryParams("createUser");
                    String password = request.queryParams("createPass");
                    user.name = name;
                    user.password = password;
                    users.put(user.name, user);
                    response.redirect("/");
                    return "";
                })
        );
        Spark.post(
                "/messages",
                ((request, response) -> {
                    Message message = new Message(user.messages.size() + 1, request.queryParams(("createMessage")));
                    user.messages.add(message);
                    response.redirect("/");
                    return "";
                })
        );
    }
}
