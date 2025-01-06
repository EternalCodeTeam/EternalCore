package com.eternalcode.core.publish;

import java.lang.reflect.Method;

public final class SubscriberUtil {

    private SubscriberUtil() {
    }

    public static boolean isSubscriber(Class<?> subscriberCanditate) {
        for (Method method : subscriberCanditate.getDeclaredMethods()) {
            Subscribe subscribe = method.getAnnotation(Subscribe.class);

            if (subscribe == null) {
                continue;
            }

            return true;
        }

        return false;
    }

}
