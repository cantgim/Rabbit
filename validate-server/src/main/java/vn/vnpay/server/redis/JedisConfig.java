package vn.vnpay.server.redis;

public class JedisConfig {
    public static int port;
    public static int maxTotal;
    public static int maxIdle;
    public static int minIdle;
    public static int minEvictableIdleSec;
    public static int timeBetweenEvictionRunsSec;
    public static boolean blockWhenExhausted;

    public JedisConfig() {
    }
}
