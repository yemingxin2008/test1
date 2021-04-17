package com.ymx.redission;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class LockServerImpl {

    public RedissonClient getSession(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }


}
