package vn.vnpay.common.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CommonUtils {
    public static String kebabCaseToCamelCase(String str) {
        if (str == null) {
            throw new IllegalArgumentException("The String arg must not be null");
        }
        return Arrays.stream(str.split("-"))
                .skip(0)
                .map((s) -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining());
    }
}
