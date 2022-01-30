package com.techupstudio.otc_chingy.mychurch.core.utils.general.collections.observable.interfaces;

public abstract class OnValueChangedListener<T> {
    public abstract void onChange(T newValue, T oldValue);

    public boolean isEqual(T newValue, T oldValue) {
        return newValue == oldValue;
    }
}