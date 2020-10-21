package com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces;

public interface Processor<T, R> {
    R process(T item);
}
