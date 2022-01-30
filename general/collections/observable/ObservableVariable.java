package com.techupstudio.otc_chingy.mychurch.core.utils.general.collections.observable;


import com.techupstudio.otc_chingy.mychurch.core.utils.general.collections.Variable;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.collections.observable.interfaces.BiDirectionalBindMethod;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.collections.observable.interfaces.OnSetValueListener;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.collections.observable.interfaces.OnValueChangedListener;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.collections.observable.interfaces.OnValueGetListener;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.collections.observable.interfaces.OnValueSetListener;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.collections.observable.interfaces.UniDirectionalBindMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ObservableVariable<V> extends Variable<V> {

    protected Map<ObservableVariable<?>, Binder<?, ?>> mBinderMap;
    private List<OnValueSetListener<V>> onValueSetListeners;
    private List<OnSetValueListener<V>> onSetValueListeners;
    private List<OnValueGetListener<V>> onValueGetListeners;
    private List<OnValueChangedListener<V>> onValueChangedListeners;

    public ObservableVariable() {
        init();
    }

    public ObservableVariable(V initialValue) {
        init();
        super.setValue(initialValue);
    }

    public ObservableVariable(OnValueChangedListener<V> onValueChangedListener) {
        init();
        this.onValueChangedListeners.add(onValueChangedListener);
    }

    public ObservableVariable(V initialValue, OnValueChangedListener<V> onValueChangedListener) {
        init();
        super.setValue(initialValue);
        this.onValueChangedListeners.add(onValueChangedListener);
    }

    private void init() {
        mBinderMap = new HashMap<>();
        onValueSetListeners = new ArrayList<>();
        onSetValueListeners = new ArrayList<>();
        onValueGetListeners = new ArrayList<>();
        onValueChangedListeners = new ArrayList<>();
    }

    protected void set(V value) {
        super.setValue(value);
    }

    protected V get() {
        return super.getValue();
    }

    @Override
    public V getValue() {
        V value = get();
        runOnValueGetListeners(value);
        return value;
    }

    @Override
    public void setValue(V value) {
        V oldValue = get();
        runOnSetValueListeners(value);
        set(value);
        runOnValueSetListeners(value);
        if (!equals(oldValue, value))
            runOnValueChangedListeners(value, oldValue);
    }

    private <X, Y> boolean equals(X o1, Y o2) {
        if (o1 == o2) return true;
        if ((o1 == null || o2 == null) || o1.getClass() != o2.getClass()) return false;
        return o1.equals(o2);
    }

    private void runOnSetValueListeners(V value) {
        for (OnSetValueListener<V> onSetValueListener : onSetValueListeners) {
            if (onSetValueListener != null)
                onSetValueListener.onSet(value);
        }
    }

    private void runOnValueSetListeners(V value) {
        for (OnValueSetListener<V> onValueSetListener : onValueSetListeners) {
            if (onValueSetListener != null)
                onValueSetListener.onSet(value);
        }
    }

    private void runOnValueGetListeners(V value) {
        for (OnValueGetListener<V> onValueGetListener : onValueGetListeners) {
            if (onValueGetListener != null)
                onValueGetListener.onGet(value);
        }
    }

    private void runOnValueChangedListeners(V newValue, V oldValue) {
        for (OnValueChangedListener<V> onValueChangedListener : onValueChangedListeners) {
            if (onValueChangedListener != null && !onValueChangedListener.isEqual(newValue, oldValue)) {
                onValueChangedListener.onChange(newValue, oldValue);
            }
        }
    }

    public List<OnValueSetListener<V>> getOnValueSetListeners() {
        return onValueSetListeners;
    }

    public List<OnSetValueListener<V>> getOnSetValueListeners() {
        return onSetValueListeners;
    }

    public List<OnValueGetListener<V>> getOnValueGetListeners() {
        return onValueGetListeners;
    }

    public List<OnValueChangedListener<V>> getOnValueChangedListeners() {
        return onValueChangedListeners;
    }

    public void addOnValueSetListener(OnValueSetListener<V> onValueSetListener) {
        this.onValueSetListeners.add(onValueSetListener);
    }

    public void addOnSetValueListener(OnSetValueListener<V> onSetValueListener) {
        this.onSetValueListeners.add(onSetValueListener);
    }

    public void addOnValueGetListener(OnValueGetListener<V> onValueGetListener) {
        this.onValueGetListeners.add(onValueGetListener);
    }

    public void addOnValueChangedListener(OnValueChangedListener<V> onValueChangedListener) {
        this.onValueChangedListeners.add(onValueChangedListener);
    }

    public void removeOnValueSetListener(OnValueSetListener<V> onValueSetListener) {
        this.onValueSetListeners.remove(onValueSetListener);
    }

    public void removeOnSetValueListener(OnSetValueListener<V> onSetValueListener) {
        this.onSetValueListeners.remove(onSetValueListener);
    }

    public void removeOnValueGetListener(OnValueGetListener<V> onValueGetListener) {
        this.onValueGetListeners.remove(onValueGetListener);
    }

    public void removeOnValueChangedListener(OnValueChangedListener<V> onValueChangedListener) {
        this.onValueChangedListeners.remove(onValueChangedListener);
    }

    public void bindUniDirectional(ObservableVariable<V> receiver) {
        Binder<V, V> binder = new Binder<>(this, receiver);
        binder.bindUniDirectional();
    }

    public <T> void bindUniDirectional(ObservableVariable<T> receiver, UniDirectionalBindMethod<V, T> uniDirectionalBindMethod) {
        Binder<V, T> binder = new Binder<V, T>(this, receiver);
        binder.bindUniDirectional(uniDirectionalBindMethod);
    }

    public void bindBiDirectional(ObservableVariable<V> receiver) {
        Binder<V, V> binder = new Binder<>(this, receiver);
        binder.bindBiDirectional();
    }

    public <T> void bindBiDirectional(ObservableVariable<T> receiver, BiDirectionalBindMethod<V, T> biDirectionalBindMethod) {
        Binder<V, T> binder = new Binder<V, T>(this, receiver);
        binder.bindBiDirectional(biDirectionalBindMethod);
    }

    public <T> void unbind(ObservableVariable<T> other) {
        if (hasBinder(other))
            getBinder(other).unbind();
        if (other.hasBinder(this))
            other.getBinder(this).unbind();
    }

    public <T> Binder<V, T> getBinder(ObservableVariable<T> other) {
        return (Binder<V, T>) mBinderMap.get(other);
    }

    public <T> boolean hasBinder(ObservableVariable<T> other) {
        return mBinderMap.get(other) != null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ObservableVariable{");
        sb.append("BinderMap=").append(mBinderMap);
        sb.append(", onValueSetListeners=").append(onValueSetListeners);
        sb.append(", onValueGetListeners=").append(onValueGetListeners);
        sb.append(", onValueChangedListeners=").append(onValueChangedListeners);
        sb.append('}');
        return sb.toString();
    }
}