package vn.vnpay.common.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author giangdv1
 * Created: Fri, 18/11/2022, 11:35 AM
 */
public class CommonUtilsTest {
    @Test
    public void verify_kebab_case_to_camel_case() {
        String kebabCase = "the-quick-brown-fox-jumps-over-the-lazy-dog";
        String camelCase = CommonUtils.kebabCaseToCamelCase(kebabCase);
        assertEquals(camelCase, "theQuickBrownFoxJumpsOverTheLazyDog");
    }
}
