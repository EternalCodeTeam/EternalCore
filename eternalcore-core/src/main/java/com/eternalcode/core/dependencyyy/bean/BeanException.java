package com.eternalcode.core.dependencyyy.bean;

import com.eternalcode.core.dependencyyy.TypedException;

public class BeanException extends TypedException {

    public BeanException(Class<?> type) {
        super(type);
    }

    public BeanException(String message, Class<?> type) {
        super(message, type);
    }

    public BeanException(String message, Throwable cause, Class<?> type) {
        super(message, cause, type);
    }

    public BeanException(Throwable cause, Class<?> type) {
        super(cause, type);
    }

}
