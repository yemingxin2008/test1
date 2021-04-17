package com.ymx.mapper;

import com.ymx.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("userMapper")
public interface UserMapper {
    @Insert("INSERT INTO USERS(NAME, AGE) VALUES(#{name}, #{age})")
    int insert(@Param("name") String name, @Param("age") Integer age);

    int saveUser(User user);
}
