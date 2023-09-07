package com.eternalcode.core.dependencyyy.bean;

public interface BeanCandidate {

    boolean isCandidate(Class<?> clazz);

    <T> BeanHolder<T> createBean(Class<T> clazz);

}
