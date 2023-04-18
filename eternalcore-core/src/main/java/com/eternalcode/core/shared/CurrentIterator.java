package com.eternalcode.core.shared;

import java.util.Iterator;
import java.util.List;

public class CurrentIterator<E> implements Iterator<E> {

    public static <E> CurrentIterator<E> wrap(List<E> list) {
        return new CurrentIterator<>(list);
    }

    private final List<E> list;
    private int position;

    private CurrentIterator(List<E> list) {
        this.list = list;
        this.position = -1;
    }

    @Override
    public boolean hasNext() {
        return this.position < this.list.size() - 1;
    }

    @Override
    public E next() {
        if (!hasNext()) {
            throw new IllegalStateException("No more elements");
        }
        return this.list.get(++position);
    }

    public E current() {
        return this.list.get(position);
    }

}
