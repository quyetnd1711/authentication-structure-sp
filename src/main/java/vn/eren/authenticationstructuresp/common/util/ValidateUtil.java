package vn.eren.authenticationstructuresp.common.util;

import java.util.Map;

public class ValidateUtil {

    public static String mapAttribute(String message, Map<String, Object> attributes) {
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            message = message.replace("{" + key + "}", value);
        }
        return message;
    }
}