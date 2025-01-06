package com.eternalcode.core.injector.bean;

import dev.rollczi.litecommands.priority.PriorityLevel;

class PrioritizedBeanCandidate implements BeanCandidate {

    private final BeanCandidate candidate;
    private final PriorityLevel priorityLevel;

    PrioritizedBeanCandidate(BeanCandidate candidate, PriorityLevel priorityLevel) {
        this.candidate = candidate;
        this.priorityLevel = priorityLevel;
    }

    @Override
    public boolean isCandidate(Class<?> clazz) {
        return this.candidate.isCandidate(clazz);
    }

    @Override
    public Class<?> getType() {
        return this.candidate.getType();
    }

    @Override
    public <T> BeanHolder<T> createBean(Class<T> clazz) {
        return this.candidate.createBean(clazz);
    }

    @Override
    public PriorityLevel getPriority() {
        return this.priorityLevel;
    }
}
