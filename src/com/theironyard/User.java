package com.theironyard;

import java.util.ArrayList;

/**
 * Created by alexanderhughes on 2/22/16.
 */
public class User {
    String name;
    ArrayList<com.theironyard.Message> messages;

    public User() {
    }

    public User(String name) {
        this.name = name;
        this.messages = new ArrayList<>();
    }
}
