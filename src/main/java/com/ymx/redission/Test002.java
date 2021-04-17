package com.ymx.redission;

import com.ymx.redislock.LockService;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

public class Test002 {

    public static void main(String[] args) {
        LockServerImpl lockService = new LockServerImpl();
        for (int i = 0; i < 5; i++) {
             ThreadRedis threadRedis = new ThreadRedis(lockService);
            threadRedis.start();
        }
    }


}
