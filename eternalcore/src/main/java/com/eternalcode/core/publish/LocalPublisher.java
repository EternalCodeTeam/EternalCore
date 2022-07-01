package com.eternalcode.core.publish;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class LocalPublisher implements Publisher {

    private final Map<Class<?>, Set<NativeSubscriber<?>>> subscribersByType = new HashMap<>();

    @Override
    public void subscribe(Subscriber subscriber) {
        Class<?> typeOfSubscriber = subscriber.getClass();

        for (Method method : typeOfSubscriber.getDeclaredMethods()) {
            Subscribe subscribe = method.getAnnotation(Subscribe.class);

            if (subscribe == null) {
                continue;
            }

            Parameter[] parameters = method.getParameters();

            if (parameters.length != 1) {
                throw new IllegalStateException("Listener with @Subscribe must have only one parameter!");
            }

            Parameter parameter = parameters[0];
            Class<?> type = parameter.getType();

            if (!Content.class.isAssignableFrom(type)) {
                throw new IllegalStateException("Parameter in the method must be an content!");
            }

            Set<NativeSubscriber<?>> nativeSubscribers = this.subscribersByType.computeIfAbsent(type, key -> new HashSet<>());

            nativeSubscribers.add(new NativeSubscriber<>(subscriber, method));
        }
    }

    @Override
    public void publish(Content content) {
        Set<NativeSubscriber<?>> nativeSubscribers = this.subscribersByType.get(content.getClass());

        if (nativeSubscribers == null) {
            return;
        }

        for (NativeSubscriber<?> nativeSubscriber : nativeSubscribers) {
            Subscriber instance = nativeSubscriber.subscriber;

            try {
                Method method = nativeSubscriber.method;

                method.setAccessible(true);
                method.invoke(instance, content);
            } catch (IllegalAccessException | InvocationTargetException exception) {
                exception.printStackTrace();
            }
        }
    }

    private static final class NativeSubscriber<T extends Subscriber> {
        private final T subscriber;
        private final Method method;

        private NativeSubscriber(T subscriber, Method method) {
            this.subscriber = subscriber;
            this.method = method;
        }
    }

}
