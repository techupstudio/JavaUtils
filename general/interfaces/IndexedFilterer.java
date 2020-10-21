package com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces;

public interface IndexedFilterer<K> {
    boolean filter(int index, K item);
}