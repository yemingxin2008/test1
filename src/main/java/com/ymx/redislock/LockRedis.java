package com.ymx.redislock;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

public class LockRedis {

    private JedisPool jedisPool;

    public LockRedis(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public String lockWithTimeout(String lockKey, Long acquireTimeout, Long timeOut) {
        Jedis conn = null;
        String retIdentifierValue = null;
        try {
            // 1.建立redis连接
            conn = jedisPool.getResource();
            // 2.随机生成一个value
            String identifierValue = UUID.randomUUID().toString();
            // 3.定义锁的名称
            String lockName = "redis_lock" + lockKey;
            // 4.定义上锁成功之后,锁的超时时间
            int expireLock = (int) (timeOut / 1000);
            // 5.定义在没有获取锁之前,锁的超时时间
            Long endTime = System.currentTimeMillis() + acquireTimeout;
            while (System.currentTimeMillis() < endTime) {
                // 6.使用setnx方法设置锁值
                if (conn.setnx(lockName, identifierValue) == 1) {
                    // 7.判断返回结果如果为1,则可以成功获取锁,并且设置锁的超时时间
                    conn.expire(lockName, expireLock);
                    retIdentifierValue = identifierValue;
                    return retIdentifierValue;
                }
                // 8.否则情况下继续循环等待
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return retIdentifierValue;
    }

    /**
     * 释放锁
     *
     * @return
     */
    public boolean releaseLock(String lockKey, String identifier) {

        Jedis conn = null;
        boolean flag = false;
        try {

            // 1.建立redis连接
            conn = jedisPool.getResource();
            // 2.定义锁的名称
            String lockName = "redis_lock" + lockKey;
            // 3.如果value与redis中一直直接删除，否则等待超时
            if (identifier.equals(conn.get(lockName))) {
                System.out.println(Thread.currentThread().getName() + "释放锁成功,锁id identifier:" + identifier);
                conn.del(lockName);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return flag;
    }

}
