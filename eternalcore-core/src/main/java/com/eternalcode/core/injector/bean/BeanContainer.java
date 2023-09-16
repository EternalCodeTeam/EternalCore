package com.eternalcode.core.injector.bean;

import com.eternalcode.core.util.ReflectUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class BeanContainer {

    private final Map<Class<?>, Set<BeanHolder<?>>> beans = new HashMap<>();

    <T> void addBean(BeanHolder<T> bean) {
        Class<T> type = bean.getType();

        for (Class<?> superClass : ReflectUtil.getAllSuperClasses(type)) {
            Set<BeanHolder<?>> beans = this.beans.computeIfAbsent(superClass, c -> new HashSet<>());
            beans.add(bean);
        }
    }

    <T> List<BeanHolder<T>> getBeans(Class<T> type) {
        Set<BeanHolder<T>> beans = ReflectUtil.unsafeCast(this.beans.getOrDefault(type, Collections.emptySet()));

        return new ArrayList<>(beans);
    }

}
