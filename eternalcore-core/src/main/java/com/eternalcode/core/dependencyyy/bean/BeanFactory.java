package com.eternalcode.core.dependencyyy.bean;

import java.util.List;

public class BeanFactory {

    private final BeanContainer container = new BeanContainer();

    public <T> BeanHolder<T> getSingletonBean(Class<T> type) {
        List<BeanHolder<T>> beans = container.getBeans(type);

        if (beans.isEmpty()) {
            throw new BeanException("No bean found for type " + type.getName(), type);
        }

        if (beans.size() > 1) {
            String beansAsString = String.join(", ", beans.stream()
                .map(bean -> bean.getClass().getName())
                .toArray(String[]::new));

            throw new BeanException("Multiple beans found for type " + type.getName() + ": " + beansAsString, type);
        }

        return beans.get(0);
    }

}
