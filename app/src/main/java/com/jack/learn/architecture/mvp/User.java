package com.jack.learn.architecture.mvp;

public class User {
    private String name;
    private String password;
    public User() {}
    //set or get ...
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
