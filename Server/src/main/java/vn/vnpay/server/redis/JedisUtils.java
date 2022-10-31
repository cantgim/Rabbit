package vn.vnpay.server.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import redis.clients.jedis.Jedis;
import vn.vnpay.server.domain.Transaction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Slf4j
public class JedisUtils {
    public void init(String key, List<Transaction> transactions) {
        Jedis jedis = null;
        try {
            jedis = JedisConfiguration.getPool().getResource();

//            Map<Long, Integer> map = new HashMap<>();

            for (Transaction transaction: transactions
                 ) {
//                map.put(transaction.getTransactionId(), 0);
                jedis.sadd(key, String.valueOf(transaction.getTransactionId()));
            }
            System.currentTimeMillis();
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY,23);
            cal.set(Calendar.MINUTE,59);
            cal.set(Calendar.SECOND,59);

            jedis.expireAt(key, cal.getTime().getTime());
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public boolean exist(String key, String id) {
        Jedis jedis = null;
        try {
            jedis = JedisConfiguration.getPool().getResource();
            return jedis.sismember(key, id);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return true;
    }
}
