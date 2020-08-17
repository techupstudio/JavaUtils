package com.techupstudio.otc_chingy.mychurch.utils.general.collections;


public class Stack<T> {

    protected MasterList<T> list = new MasterList<>();

    public void push(T value) {
        list.append(value);
    }

    public T pop() {
        if (!isEmpty()) {
            T top = list.poplast();
            list.remove(list.size() - 1);
            return top;
        }
        return null;
    }

    public T peekTop() {
        return list.poplast();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void clear() {
        list.clear();
    }

    public String toString() {
        return list.toString();
    }

}
