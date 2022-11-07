package vn.vnpay.server.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import vn.vnpay.common.constant.Constant;

import java.time.Duration;

public class JedisConfig {
    private static JedisPool jedisPool;
    private static int maxTotal;
    private static int maxIdle;
    private static int minIdle;
    private static int minEvictableIdleSec;
    private static int timeBetweenEvictionRunsSec;
    private static boolean blockWhenExhausted;

    private JedisConfig(Builder builder) {
        maxTotal = builder.maxTotal;
        maxIdle = builder.maxIdle;
        minIdle = builder.minIdle;
        minEvictableIdleSec = builder.minEvictableIdleSec;
        timeBetweenEvictionRunsSec = builder.timeBetweenEvictionRunsSec;
        blockWhenExhausted = builder.blockWhenExhausted;
    }

    public static JedisPool getPool() {
        if (JedisConfig.jedisPool == null) {
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(maxTotal);
            poolConfig.setMaxIdle(maxIdle);
            poolConfig.setMinIdle(minIdle);
            poolConfig.setMinEvictableIdleTime(Duration.ofSeconds(minEvictableIdleSec));
            poolConfig.setTimeBetweenEvictionRuns(Duration.ofSeconds(timeBetweenEvictionRunsSec));
            poolConfig.setBlockWhenExhausted(blockWhenExhausted);

            JedisConfig.jedisPool = new JedisPool(poolConfig, Constant.Redis.HOST_NAME,
                    Constant.Redis.PORT);
        }
        return jedisPool;
    }

    public static class Builder {
        private int maxTotal;
        private int maxIdle;
        private int minIdle;
        private int minEvictableIdleSec;
        private int timeBetweenEvictionRunsSec;
        private boolean blockWhenExhausted;

        public Builder withMaxTotal(int maxTotal) {
            this.maxTotal = maxTotal;
            return this;
        }

        public Builder withMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
            return this;
        }

        public Builder withMinIdle(int minIdle) {
            this.minIdle = minIdle;
            return this;
        }

        public Builder withMinEvictableIdleSec(int minEvictableIdleSec) {
            this.minEvictableIdleSec = minEvictableIdleSec;
            return this;
        }

        public Builder withTimeBetweenEvictionRunsSec(int timeBetweenEvictionRunsSec) {
            this.timeBetweenEvictionRunsSec = timeBetweenEvictionRunsSec;
            return this;
        }

        public Builder withBlockWhenExhausted(boolean blockWhenExhausted) {
            this.blockWhenExhausted = blockWhenExhausted;
            return this;
        }

        public JedisConfig builder() {
            return new JedisConfig(this);
        }
    }
}
