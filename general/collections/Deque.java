package com.techupstudio.otc_chingy.mychurch.core.utils.general.collections;

import com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces.Action;

import java.util.Iterator;

public class Deque<T> implements Iterable<T> {

    private final Items<T> items;

    public Deque() {
        items = new Items<>();
    }

    public void enqueueEnd(T object) {
        items.appendBack(object);
    }

    public void enqueueStart(T object) {
        items.appendFront(object);
    }

    public T dequeueEnd() {
        return peekBack();
    }

    public T dequeueStart() {
        return peekFront();
    }

    public T peekFront() {
        return items.peekFront();
    }

    public T peekBack() {
        return items.peekBack();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }

    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }

    public void forEach(Action<T> action) {
        items.forEach(action);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Deque{");
        sb.append("items=").append(items);
        sb.append('}');
        return sb.toString();
    }

}
