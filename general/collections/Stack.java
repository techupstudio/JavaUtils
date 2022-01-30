package com.techupstudio.otc_chingy.mychurch.core.utils.general.collections;


public class Stack<T> {

    protected Items<T> items;

    public Stack() {
        items = new Items<>();
    }

    public void push(T value) {
        items.appendBack(value);
    }

    public T pop() {
        return items.popBack();
    }

    public T peekTop() {
        return items.peekBack();
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Stack{");
        sb.append("items=").append(items);
        sb.append('}');
        return sb.toString();
    }
}
