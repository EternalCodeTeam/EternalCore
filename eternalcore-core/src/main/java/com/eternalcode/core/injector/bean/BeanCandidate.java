package com.eternalcode.core.injector.bean;

import dev.rollczi.litecommands.priority.Prioritized;
import dev.rollczi.litecommands.priority.PriorityLevel;
import java.util.function.Supplier;

public interface BeanCandidate extends Prioritized {

    boolean isCandidate(Class<?> clazz);

    Class<?> getType();

    <T> BeanHolder<T> createBean(Class<T> clazz);

    @Override
    default PriorityLevel getPriority() {
        return PriorityLevel.NORMAL;
    }

    static <T> BeanCandidate of(String name, Class<T> type, Supplier<T> instance) {
        return new SimpleBeanCandidate(name, type, instance);
    }

    static <T> BeanCandidate of(Class<T> type, Supplier<T> instance) {
        return new SimpleBeanCandidate(BeanHolder.DEFAULT_NAME, type, instance);
    }

    static BeanCandidate prioritized(BeanCandidate candidate, PriorityLevel priorityLevel) {
        return new PrioritizedBeanCandidate(candidate, priorityLevel);
    }

}
