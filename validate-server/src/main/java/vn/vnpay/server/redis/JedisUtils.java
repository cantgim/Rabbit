package vn.vnpay.server.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import vn.vnpay.common.constant.Constant;
import vn.vnpay.common.domain.Transaction;

import java.time.Duration;
import java.util.Calendar;
import java.util.Optional;

@Slf4j
public class JedisUtils {
    private static JedisPool jedisPool;

    public static JedisPool getPool() {
        if (jedisPool == null) {
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(JedisConfig.maxTotal);
            poolConfig.setMaxIdle(JedisConfig.maxIdle);
            poolConfig.setMinIdle(JedisConfig.minIdle);
            poolConfig.setMinEvictableIdleTime(Duration.ofSeconds(JedisConfig.minEvictableIdleSec));
            poolConfig.setTimeBetweenEvictionRuns(
                    Duration.ofSeconds(JedisConfig.timeBetweenEvictionRunsSec));
            poolConfig.setBlockWhenExhausted(JedisConfig.blockWhenExhausted);

            jedisPool = new JedisPool(poolConfig, Constant.Redis.HOST_NAME, Constant.Redis.PORT);
        }
        return jedisPool;
    }

    public void save(String key, Transaction transaction) {
        Jedis jedis = null;
        try {
            log.info("Begin init.");
            jedis = jedisPool.getResource();
            log.info("Get pool success. Saving to hashes...");
            jedis.hset(key, "trace", String.valueOf(transaction.getTrace()));
            jedis.hset(key, "name", String.valueOf(transaction.getName()));
            jedis.hset(key, "amount", String.valueOf(transaction.getAmount()));
            jedis.hset(key, "payDate", String.valueOf(transaction.getPayDate()));
            log.info("Set redis expire time.");
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            jedis.expire(key, cal.getTime().getTime());
            log.info("Transaction saved.");
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Optional<String> exist(String key, String field) {
        log.info("Check transaction exist.");
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            log.info("Redis get pool success.");
            return Optional.ofNullable(jedis.hget(key, field));
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return Optional.empty();
    }

    public String get(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hget(key, field);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }
}
