package com.techupstudio.otc_chingy.mychurch.core.utils.general.collections.observable.interfaces;

public interface UniDirectionalBindMethod<T, R> {
    R transform(T requesterValue);
}