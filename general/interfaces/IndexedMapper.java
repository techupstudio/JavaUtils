package com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces;

public interface IndexedMapper<K, V> {
    V map(int index, K item);
}
