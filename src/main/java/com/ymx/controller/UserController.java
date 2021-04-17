package com.ymx.controller;

import com.ymx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userServiceImpl;


    @RequestMapping("/hello1")
    @ResponseBody
    public String hello1() {
        int i=1/0;
        return "Hello World";
    }

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello World";
    }


    @RequestMapping("/index")
    public String index(Map<String, Object> map) {
        map.put("name","美丽的天使...");
        return "index";
    }

    @RequestMapping("/add")
    public String add(Map<String, Object> map) {
        map.put("name","美丽的天使...");
        userServiceImpl.add();
        return "index";
    }
    @RequestMapping("/add1")
    @ResponseBody
    public String add1() {
        userServiceImpl.add1();
        return "add1";
    }

    @RequestMapping("/add2")
    @ResponseBody
    public String add2() {
        userServiceImpl.add2();
        return "add2";
    }
    @RequestMapping("/saveUser")
    @ResponseBody
    public String saveUser() {
        userServiceImpl.saveUser();
        return "saveUser";
    }

    @RequestMapping("/saveUserAuto")
    @ResponseBody
    public String saveUserAuto() {
        userServiceImpl.saveUserAuto();
        return "saveUser";
    }
}
