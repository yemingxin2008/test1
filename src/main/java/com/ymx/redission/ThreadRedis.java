package com.ymx.redission;

import com.ymx.redislock.LockService;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

class ThreadRedis extends Thread {

    private LockServerImpl lockService;
    public ThreadRedis(LockServerImpl lockService) {
        this.lockService = lockService;
    }

    @Override
    public void run() {
        RedissonClient redissonClient = lockService.getSession();
        String lockKey="lockKey";
// 3.获取锁对象实例（无法保证是按线程的顺序获取到）
        RLock rLock = redissonClient.getLock(lockKey);
        try {
            boolean res = rLock.tryLock((long)2, (long)5, TimeUnit.SECONDS);
            if (res) {
                System.out.println("成功获得锁，在这里处理业务");
                System.out.println(Thread.currentThread().getName() + "获取锁成功,锁id res:" + res + "，执行业务逻辑");
            }
        } catch (Exception e) {
            throw new RuntimeException("aquire lock fail");
        }finally {
            //无论如何, 最后都要解锁
            rLock.unlock();
            System.out.println(Thread.currentThread().getName() + "释放锁成功,锁id identifier:"    );

        }

    }

}