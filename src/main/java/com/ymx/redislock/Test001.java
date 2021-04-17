package com.ymx.redislock;

public class Test001 {
    public static void main(String[] args) {
        LockService lockService = new LockService();
        for (int i = 0; i < 20; i++) {
            ThreadRedis threadRedis = new ThreadRedis(lockService);
            threadRedis.start();
        }
    }

}


