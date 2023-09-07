package com.eternalcode.core.dependencyyy.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanContainer {

    private final Map<Class<?>, BeanHolder<?>> beans = new HashMap<>();

    public <T> void addBean(Class<T> type, BeanHolder<T> bean) {
        if (this.beans.containsKey(type)) {
            throw new BeanException("Bean already exists for type " + type.getName(), type);
        }

        this.beans.put(type, bean);
    }

    public <T> List<BeanHolder<T>> getBeans(Class<T> type) {
        List<BeanHolder<T>> beans = new ArrayList<>();

        for (Map.Entry<Class<?>, BeanHolder<?>> entry : this.beans.entrySet()) {
            if (type.isAssignableFrom(entry.getKey())) {
                beans.add((BeanHolder<T>) entry.getValue());
            }
        }

        return beans;
    }

}
