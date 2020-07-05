package com.techupstudio.utils.general.collections;

import java.util.*;
import java.util.function.BiConsumer;

public class Dictionary<K, V> implements Iterable<KeyValuePair<K, V>> {

    private Map<K, V> DATA = new HashMap<>();

    public Dictionary() {
    }

    public Dictionary(Map<K, V> obj) {
        DATA = obj;
    }

    public void assign(Map<K, V> obj) {
        DATA = obj;
    }

    public void join(Map<K, V> obj) {
        obj.forEach((k, v) -> DATA.put(k, v));
    }

    public void set(K key, V value) {
        DATA.put(key, value);
    }

    public V get(K key) {
        return DATA.get(key);
    }

    public void setIfAbsent(K key, V value) {
        DATA.putIfAbsent(key, value);
    }

    public V getOrDefault(K key, V defaultValue) {
        return DATA.getOrDefault(key, defaultValue);
    }

    public void remove(K key) {
        DATA.remove(key);
    }

    public void remove(K key, V value) {
        DATA.remove(key, value);
    }

    public void replace(K key, V value) {
        DATA.replace(key, value);
    }

    public void replace(K key, V value, V new_value) {
        DATA.replace(key, new_value);
    }

    public boolean hasKey(K key) {
        return DATA.containsKey(key);
    }

    public boolean hasValue(V value) {
        return DATA.containsValue(value);
    }

    public boolean hasKeyValue(K key, V value) {
        return DATA.containsKey(key) && get(key) == value;
    }

    public Set<K> keys() {
        return DATA.keySet();
    }

    public Collection<V> values() {
        return DATA.values();
    }

    public int count(K key) {
        int count = 0;
        for (K i : DATA.keySet()) {
            if (key == i) {
                count++;
            }
        }
        return count;
    }

    public int count(K key, V value) {
        int count = 0;
        for (K i : DATA.keySet()) {
            if (key == i && value == DATA.get(key)) {
                count++;
            }
        }
        return count;
    }

    public String[][] iter() {
        String[][] n = new String[size()][];
        int index = 0;
        for (K i : DATA.keySet()) {
            n[index] = new String[]{i.toString(), get(i).toString()};
            index++;
        }
        return n;
    }


    public int size() {
        return DATA.size();
    }

    public void clear() {
        DATA.clear();
    }

    public void forEach(BiConsumer<? super K, ? super V> action) {
        DATA.forEach(action);
    }

    public Dictionary<K, V> clone() {
        return new Dictionary<>(DATA);
    }

    public String toString() {
        return DATA.toString();
    }

    public int hashCode() {
        return DATA.hashCode();
    }

    public boolean isEmpty() {
        return DATA.isEmpty();
    }

    protected List<KeyValuePair<K, V>> getList() {
        List<KeyValuePair<K, V>> list = new ArrayList<>();
        forEach((k, v) -> list.add(new KeyValuePair<>(k, v)));
        return list;
    }

    public Enumerator<KeyValuePair<K, V>> getEnumerator() {
        return new Enumerator<>(getList());
    }

    @Override
    public Iterator<KeyValuePair<K, V>> iterator() {
        return getList().iterator();
    }

    public Collection<K> getKeysWithValue(V value) {
        List<K> keys = new ArrayList<>();
        if (hasValue(value)) {
            for (K key : keys()) {
                if (get(key) == value) {
                    keys.add(key);
                }
            }
        }
        return keys;
    }

}