package vn.vnpay.server.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import vn.vnpay.server.constant.Constant;

import java.time.Duration;

public class JedisConfiguration {
    private static JedisPool jedisPool;

    public static JedisPool getPool() {
        if (JedisConfiguration.jedisPool == null) {
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(Constant.Redis.MAX_TOTAL);
            poolConfig.setMaxIdle(Constant.Redis.MAX_IDLE);
            poolConfig.setMinIdle(Constant.Redis.MIN_IDLE);
            poolConfig.setMinEvictableIdleTime(Duration.ofSeconds(Constant.Redis.MIN_EVICTABLE_IDLE_SEC));
            poolConfig.setTimeBetweenEvictionRuns(Duration.ofSeconds(Constant.Redis.TIME_BETWEEN_EVICTION_RUNS_SEC));
            poolConfig.setBlockWhenExhausted(Constant.Redis.BLOCK_WHEN_EXHAUSTED);

            JedisConfiguration.jedisPool = new JedisPool(poolConfig, Constant.Redis.HOST_NAME,
                    Constant.Redis.PORT);
        }
        return jedisPool;
    }
}
