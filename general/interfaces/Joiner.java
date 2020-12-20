package com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces;

public interface Joiner<T, R> {
    R join(R result, T item);
}