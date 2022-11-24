package vn.vnpay.server.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class AppConfig {
    private static final Properties props = new Properties();

    public static Properties getConfig(String fileName) {
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(fileName)) {
            props.load(inputStream);
        } catch (IOException e) {
            log.error("Stack trace", e);
        }
        return props;
    }

    public static String getProperty(String key) {
        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("The String must not be null");
        }
        return props.getProperty(key);
    }
}
