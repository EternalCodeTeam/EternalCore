package com.eternalcode.core.publish;

import com.eternalcode.core.injector.DependencyInjector;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
final class LocalPublisher implements Publisher {

    private final Map<Class<?>, Set<NativeSubscriber>> subscribersByType = new HashMap<>();
    private final DependencyInjector dependencyInjector;

    @Inject
    LocalPublisher(DependencyInjector dependencyInjector) {
        this.dependencyInjector = dependencyInjector;
    }

    @Override
    public void subscribe(Object subscriber) {
        Class<?> typeOfSubscriber = subscriber.getClass();

        for (Method method : typeOfSubscriber.getDeclaredMethods()) {
            Subscribe subscribe = method.getAnnotation(Subscribe.class);

            if (subscribe == null) {
                continue;
            }

            Class<?> type = this.getTypeOfEvent(method, subscribe);
            Set<NativeSubscriber> nativeSubscribers = this.subscribersByType.computeIfAbsent(type, key -> new HashSet<>());

            nativeSubscribers.add(new NativeSubscriber(subscriber, method));
        }
    }

    private Class<?> getTypeOfEvent(Method method, Subscribe subscribe) {
        Class<? extends PublishEvent> value = subscribe.value();

        if (value != PublishEvent.class) {
            return value;
        }

        Parameter[] parameters = method.getParameters();

        for (Parameter parameter : parameters) {
            Class<?> type = parameter.getType();

            if (PublishEvent.class.isAssignableFrom(type)) {
                return type;
            }
        }

        throw new IllegalStateException("Listener with @Subscribe must have at least one parameter, or specify the type of event in the annotation!");
    }

    @Override
    public void publish(PublishEvent publishEvent) {
        Set<NativeSubscriber> nativeSubscribers = this.subscribersByType.get(publishEvent.getClass());

        if (nativeSubscribers == null) {
            return;
        }

        for (NativeSubscriber nativeSubscriber : nativeSubscribers) {
            Object instance = nativeSubscriber.subscriber;
            Method method = nativeSubscriber.method;

            this.dependencyInjector.invokeMethod(instance, method, publishEvent);
        }
    }

    private record NativeSubscriber(Object subscriber, Method method) {
    }

}
