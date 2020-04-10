package com.csfrez.demo.servlet.mvc;

public class User {

    public User(long id, String name, School school){
        this.id = id;
        this.name = name;
        this.school = school;
    }

    public long id;
    public String name;
    public School school;
}
