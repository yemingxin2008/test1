package com.ymx.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserMapping {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(){
        System.out.println("ssss");
        String name = "yemx";
        String age = "44";
        jdbcTemplate.update("insert into users(name,age) values(?,?);", name, age);
    }




}
