package org.hellosix.south.door.util;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author Jay.H.Zou
 * @date 6/29/2019
 */
public class CommonUtil {

    private CommonUtil() {}

    public static final String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static final Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
