package com.techupstudio.GeneralUtils.collections;

import static com.techupstudio.GeneralUtils.Funcs.format;

public class Variable<V> {
    private String KEY;
    private V VALUE;

    public Variable() {
        setName(String.valueOf(hashCode()));
    }

    public Variable(V value) {
        setName(String.valueOf(hashCode()));
        setValue(value);
    }

    public Variable(String name, V value) {
        setName(name);
        setValue(value);
    }

    public String getName() {
        return KEY;
    }

    public void setName(String key) {
        KEY = key;
    }

    public V getValue() {
        return VALUE;
    }

    public void setValue(V value) {
        VALUE = value;
    }

    public void reset() {
        VALUE = null;
    }

    public String toString() {
        return format("Variable<<> : <>>", getName(), getValue());
    }

}