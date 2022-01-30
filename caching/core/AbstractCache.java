package com.techupstudio.otc_chingy.mychurch.core.utils.caching.core;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractCache<K, V> implements Cache<K, V> {
    public abstract boolean put(K key, V item);

    public boolean putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
        return true;
    }

    public boolean putIfAbsent(K key, V item) {
        if (!containsKey(key))
            return put(key, item);
        return false;
    }

    public abstract V get(K key);

    public Map<K, V> getAll(Iterable<K> keys) {
        Map<K, V> map = new HashMap<>();
        for (K key : keys) {
            map.put(key, get(key));
        }
        return map;
    }

    public V getOrDefaultValue(K key, V defaultValue) {
        V cacheItem = get(key);
        if (cacheItem == null) return defaultValue;
        return cacheItem;
    }

    public abstract boolean remove(K key);

    public void removeAll(Iterable<K> keys) {
        for (K key : keys) {
            remove(key);
        }
    }

    public abstract boolean containsKey(K key);

    public abstract boolean containsValue(V item);

    public boolean containsKeyValue(K key, V item) {
        return containsKey(key) && get(key) == item;
    }

    public abstract Set<K> keys();

    public abstract Collection<V> values();

    public abstract void clear();

    public boolean isEmpty() {
        return size() == 0;
    }

    public abstract int size();
}
