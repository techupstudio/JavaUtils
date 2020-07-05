package com.techupstudio.utils.general.collections;


import static com.techupstudio.utils.general.Funcs.format;

public class KeyValuePair<K, V> {
    private K KEY;
    private V VALUE;

    public KeyValuePair() {
    }

    public KeyValuePair(K key, V value) {
        setKey(key);
        setValue(value);
    }

    public K getKey() {
        return KEY;
    }

    public void setKey(K key) {
        KEY = key;
    }

    public V getValue() {
        return VALUE;
    }

    public void setValue(V value) {
        VALUE = value;
    }

    public String toString() {
        return format("KeyValuePair[<> : <>]", getKey(), getValue());
    }

}