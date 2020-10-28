package com.techupstudio.otc_chingy.mychurch.core.utils.general.collections;


public class Queue<T> {

    protected Items<T> items;

    public Queue() {
        items = new Items<>();
    }

    public void enqueue(T object) {
        items.appendBack(object);
    }

    public T dequeue() {
        return items.popFront();
    }

    public T peek() {
        return items.peekFront();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Queue{");
        sb.append("items=").append(items);
        sb.append('}');
        return sb.toString();
    }
}