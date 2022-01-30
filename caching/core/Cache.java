package com.techupstudio.otc_chingy.mychurch.core.utils.caching.core;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface Cache<K, V> {
    boolean put(K key, V item);

    boolean putAll(Map<? extends K, ? extends V> map);

    boolean putIfAbsent(K key, V item);

    V get(K key);

    Map<K, V> getAll(Iterable<K> keys);

    V getOrDefaultValue(K key, V defaultValue);

    boolean remove(K key);

    void removeAll(Iterable<K> keys);

    boolean containsKey(K key);

    boolean containsValue(V item);

    boolean containsKeyValue(K key, V item);

    Set<K> keys();

    Collection<V> values();

    void clear();

    boolean isEmpty();

    int size();
}
