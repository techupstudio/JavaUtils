package com.techupstudio.otc_chingy.mychurch.core.utils.general.collections.graph;

import java.util.HashMap;

public class Vertice<T> {

    private final T object;
    private final HashMap<Object, Object> properties;

    public Vertice(T value) {
        this.object = value;
        this.properties = new HashMap<>();
    }

    public T getValue() {
        return object;
    }

    public Vertice<T> setProperty(Object key, Object value) {
        this.properties.put(key, value);
        return this;
    }

    public Object getProperty(Object key) {
        return this.properties.get(key);
    }

    public boolean hasPropertyKey(Object key) {
        return this.properties.containsKey(key);
    }

    public boolean hasPropertyValue(Object value) {
        return this.properties.containsValue(value);
    }

    public Vertice<T> removeProperty(Object key) {
        this.properties.remove(key);
        return this;
    }

    @Override
    public String toString() {
        return "Vertice<" + getValue() + ">";
    }
}
