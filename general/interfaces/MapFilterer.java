package com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces;

public interface MapFilterer<K, V> {
    boolean filter(K key, V value);
}