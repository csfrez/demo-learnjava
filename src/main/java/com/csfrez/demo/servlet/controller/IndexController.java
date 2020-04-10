package com.csfrez.demo.servlet.controller;

import com.csfrez.demo.servlet.framework.GetMapping;
import com.csfrez.demo.servlet.framework.ModelAndView;

import javax.servlet.http.HttpSession;

public class IndexController {

    @GetMapping("/")
    public ModelAndView index(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return new ModelAndView("/index.html", "user", user);
    }

    @GetMapping("/hello.html")
    public ModelAndView hello(String name) {
        if (name == null) {
            name = "World";
        }
        return new ModelAndView("/hello.html", "name", name);
    }
}
