package com.ymx.redislock;

class ThreadRedis extends Thread {
    private LockService lockService;

    public ThreadRedis(LockService lockService) {
        this.lockService = lockService;
    }

    @Override
    public void run() {
        lockService.seckill();

    }

}