package com.ymx.service.impl;

import com.ymx.entity.User;
import com.ymx.mapper.UserDao;
import com.ymx.mapper.UserMapper;
import com.ymx.mapper.UserMapping;
import com.ymx.mq.RegisterMailboxProducer;
import com.ymx.service.UserService;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.util.Random;


@Service
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private  UserMapping userMapping;
   @Autowired
   private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RegisterMailboxProducer registerMailboxProducer;

    @Override
    public void add() {
        logger.info("log4j.......................");
        System.out.println("=====ssss");
        String name = "yemx";
        String age = "33";
        jdbcTemplate.update("insert into users(name,age) values(?,?);", name, age);
    }

    @Override
    public void add1() {
        logger.info("log4j.......................");
        userMapping.insert();

    }
    @Override
    public void add2() {
        String name = "yemx";
        int age = 55;
        userMapper.insert(name,age);

    }

    @Override
    public void saveUser() {
        User user = new User("张三",22);
        userMapper.saveUser(user);

    }

    @Override
    public void saveUserAuto() {
        User user = new User("张三",42);
        Random rand = new Random();
        user.setId(rand.nextInt(100));
        userDao.save(user,"users");

        String  mail="yemingxin";
        registerMailboxProducer.sendMess(mail);

    }


}
