package vn.vnpay.server.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

@Slf4j
public class AppConfig {
    public Properties loadProperties(String fileName) {
        Properties prop = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            prop.load(inputStream);
        } catch (IOException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
        return prop;
    }
}
