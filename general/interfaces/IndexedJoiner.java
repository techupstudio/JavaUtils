package com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces;

public interface IndexedJoiner<T, R> {
    R join(R result, T item, int index);
}