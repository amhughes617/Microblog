package com.theironyard;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class Main {
    static User user;
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
                        m.put("name", user.name);
                        if (user.messages.isEmpty()) {
                            return new ModelAndView(m, "messages.html");
                        }
                        else {
                            m.put("posts", user.messages);
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
                    user = new User(name);
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
