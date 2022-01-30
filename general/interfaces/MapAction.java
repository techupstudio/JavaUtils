package com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces;

public interface MapAction<K, V> {
    void run(K key, V value);
}