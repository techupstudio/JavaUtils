package com.techupstudio.GeneralUtils.collections;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.techupstudio.GeneralUtils.Funcs.format;


public class ObservableVariable<V> extends Variable<V> {

    private Map<ObservableVariable, Binder> mBinderMap;

    public static class Binder<T, R>{
        private ObservableVariable<T> requester;
        private ObservableVariable<R> receiver;
        private OnValueChangedListener<T> requesterBindListener;
        private OnValueChangedListener<R> receiverBindListener;

        public Binder(ObservableVariable<T> requester, ObservableVariable<R> receiver){
            if (requester == null && receiver == null)
                throw new Error("Cannot bind null objects");
            if (requester == null || receiver == null)
                throw new Error("Cannot create bind with NullPointer");
            else if (requester == receiver)
                throw new Error("Cannot create bind with self");
            else if (requester.hasBinder(receiver) || receiver.hasBinder(requester))
                throw new Error("bind "+ (requester.mBinderMap.get(receiver) != null ?
                                requester.mBinderMap.get(receiver) : receiver.mBinderMap.get(requester))+
                        " already exist. unbind to add again!");
            this.requester = requester;
            this.receiver = receiver;
        }

        public ObservableVariable<T> getRequester() {
            return requester;
        }

        public ObservableVariable<R> getReceiver() {
            return receiver;
        }

        public OnValueChangedListener<T> getRequesterBindListener() {
            return requesterBindListener;
        }

        public OnValueChangedListener<R> getReceiverBindListener() {
            return receiverBindListener;
        }

        public void bindUniDirectional(){

            unbind();

            requesterBindListener = new OnValueChangedListener<T>() {
                @Override
                public void onChange(T newValue, T oldValue) {
                    receiver.setValue((R) newValue);
                }
            };
            requester.addOnValueChangedListener(requesterBindListener);
            receiver.setValue((R) requester.getValue());
            requester.mBinderMap.put(receiver, this);
        }

        public void bindUniDirectional(Class<R> rClass){

            unbind();

            requesterBindListener = new OnValueChangedListener<T>() {
                @Override
                public void onChange(T newValue, T oldValue) {
                    receiver.setValue(rClass.cast(newValue));
                }
            };
            requester.addOnValueChangedListener(requesterBindListener);
            receiver.setValue(rClass.cast(requester.getValue()));
            requester.mBinderMap.put(receiver, this);
        }

        public void bindUniDirectional(UniDirectionalBindMethod<T, R> uniDirectionalBindMethod){

            unbind();

            if (uniDirectionalBindMethod == null)
                throw new Error("Cannot bind values with null UniDirectionalBindMethod");

            requesterBindListener = new OnValueChangedListener<T>() {
                @Override
                public void onChange(T newValue, T oldValue) {
                    receiver.setValue(uniDirectionalBindMethod.transform(newValue));
                }
            };
            requester.addOnValueChangedListener(requesterBindListener);
            receiver.setValue(uniDirectionalBindMethod.transform(requester.getValue()));
            requester.mBinderMap.put(receiver, this);
        }

        public void bindBiDirectional(){

            unbind();

            requesterBindListener = new OnValueChangedListener<T>() {
                @Override
                public void onChange(T newValue, T oldValue) {
                    receiver.setValue((R) newValue);
                }
            };
            requester.addOnValueChangedListener(requesterBindListener);
            receiverBindListener = new OnValueChangedListener<R>() {
                @Override
                public void onChange(R newValue, R oldValue) {
                    requester.setValue((T) newValue);
                }
            };
            receiver.addOnValueChangedListener(receiverBindListener);
            receiver.setValue((R) requester.getValue());
            requester.mBinderMap.put(receiver, this);
        }

        public void bindBiDirectional(Class<R> rClass, Class<T> tClass){

            unbind();

            requesterBindListener = new OnValueChangedListener<T>() {
                @Override
                public void onChange(T newValue, T oldValue) {
                    receiver.setValue(rClass.cast(newValue));
                }
            };
            requester.addOnValueChangedListener(requesterBindListener);
            receiverBindListener = new OnValueChangedListener<R>() {
                @Override
                public void onChange(R newValue, R oldValue) {
                    requester.setValue(tClass.cast(newValue));
                }
            };
            receiver.addOnValueChangedListener(receiverBindListener);
            receiver.setValue(rClass.cast(requester.getValue()));
            requester.mBinderMap.put(receiver, this);
        }


        public void bindBiDirectional(BiDirectionalBindMethod<T, R> biDirectionalBindMethod){

            unbind();

            if (biDirectionalBindMethod == null)
                throw new Error("Cannot bind values with null BiDirectionalBindMethod");



            requesterBindListener = new OnValueChangedListener<T>() {
                @Override
                public void onChange(T newValue, T oldValue) {
                    receiver.setValue(biDirectionalBindMethod.transform(newValue));
                }
            };
            requester.addOnValueChangedListener(requesterBindListener);
            receiverBindListener = new OnValueChangedListener<R>() {
                @Override
                public void onChange(R newValue, R oldValue) {
                    requester.setValue(biDirectionalBindMethod.reverse(newValue));
                }
            };
            receiver.setValue(biDirectionalBindMethod.transform(requester.getValue()));
            receiver.addOnValueChangedListener(receiverBindListener);
            requester.mBinderMap.put(receiver, this);
        }

        public void unbind(){
            if (requesterBindListener != null)
                requester.removeOnValueChangedListener(requesterBindListener);
            if (receiverBindListener != null)
                receiver.removeOnValueChangedListener(receiverBindListener);
        }

        @Override
        public String toString() {
            return "Binder<"+ requester +": "+ receiver +">("+(requesterBindListener ==null? "uni":"bi")+"directional)";
        }
    }


    public interface UniDirectionalBindMethod<T, R> {
        R transform(T requesterValue);
    }


    public interface BiDirectionalBindMethod<T, R> {
        R transform(T requesterValue);
        T reverse(R receiverValue);
    }
    public interface OnValueSetListener<V> {
        void onSet(V Value);
    }

    public interface OnValueGetListener<V> {
        void onGet(V value);
    }

    public static abstract class OnValueChangedListener<T> {
        public abstract void onChange(T newValue, T oldValue);

        public boolean isEqual(T newValue, T oldValue){
            return newValue == oldValue;
        }
    }

    private List<OnValueSetListener<V>> onValueSetListeners;
    private List<OnValueGetListener<V>> onValueGetListeners;
    private List<OnValueChangedListener<V>> onValueChangedListeners;

    public ObservableVariable() {
        init();
    }

    public ObservableVariable(V initialValue) {
        super.setValue(initialValue);
        init();
    }

    ObservableVariable(OnValueChangedListener<V> onValueChangedListener) {
        this.onValueChangedListeners.add(onValueChangedListener);
    }

    ObservableVariable(V initialValue, OnValueChangedListener<V> onValueChangedListener) {
        init();
        super.setValue(initialValue);
        this.onValueChangedListeners.add(onValueChangedListener);
    }

    private void init() {
        mBinderMap = new HashMap<>();
        onValueSetListeners = new ArrayList<>();
        onValueGetListeners = new ArrayList<>();
        onValueChangedListeners = new ArrayList<>();
    }

    private void set(V value) {
        super.setValue(value);
    }

    private V get() {
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
        runOnValueSetListeners(value);
        set(value);
        runOnValueChangedListeners(value, oldValue);
    }

    private void runOnValueSetListeners(V value) {
        for (OnValueSetListener<V> onValueSetListener: onValueSetListeners) {
            if (onValueSetListener != null)
                onValueSetListener.onSet(value);
        }
    }

    private void runOnValueGetListeners(V value) {
        for (OnValueGetListener<V> onValueGetListener: onValueGetListeners) {
            if (onValueGetListener != null)
                onValueGetListener.onGet(value);
        }
    }

    private void runOnValueChangedListeners(V newValue, V oldValue) {
        for (OnValueChangedListener<V> onValueChangedListener: onValueChangedListeners) {
            if (onValueChangedListener != null && !onValueChangedListener.isEqual(newValue, oldValue)) {
                onValueChangedListener.onChange(newValue, oldValue);
            }
        }
    }

    public List<OnValueSetListener<V>> getOnValueSetListeners() {
        return onValueSetListeners;
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

    public void addOnValueGetListener(OnValueGetListener<V> onValueGetListener) {
        this.onValueGetListeners.add(onValueGetListener);
    }

    public void addOnValueChangedListener(OnValueChangedListener<V> onValueChangedListener) {
        this.onValueChangedListeners.add(onValueChangedListener);
    }

    public void removeOnValueSetListener(OnValueSetListener<V> onValueSetListener) {
        this.onValueSetListeners.remove(onValueSetListener);
    }

    public void removeOnValueGetListener(OnValueGetListener<V> onValueGetListener) {
        this.onValueGetListeners.remove(onValueGetListener);
    }

    public void removeOnValueChangedListener(OnValueChangedListener<V> onValueChangedListener) {
        this.onValueChangedListeners.remove(onValueChangedListener);
    }

    public void bindUniDirectional(ObservableVariable<V> receiver){
        Binder<V, V> binder = new Binder<>(this, receiver);
        binder.bindUniDirectional();
    }
    public void bindUniDirectional(ObservableVariable receiver, UniDirectionalBindMethod<V, Object> uniDirectionalBindMethod){
        Binder<V, Object> binder = new Binder<V, Object>(this, receiver);
        binder.bindUniDirectional(uniDirectionalBindMethod);
    }
    public void bindBiDirectional(ObservableVariable<V> receiver){
        Binder<V, V> binder = new Binder<>(this, receiver);
        binder.bindBiDirectional();
    }
    public void bindBiDirectional(ObservableVariable receiver, BiDirectionalBindMethod<V, Object> biDirectionalBindMethod){
        Binder<V, Object> binder = new Binder<V, Object>(this, receiver);
        binder.bindBiDirectional(biDirectionalBindMethod);
    }

    public void unbind(ObservableVariable other){
        if (hasBinder(other))
            getBinder(other).unbind();
        if (other.hasBinder(other))
            other.getBinder(this).unbind();
    }

    public Binder getBinder(ObservableVariable other){
        return mBinderMap.get(other);
    }

    public boolean hasBinder(ObservableVariable other){
        return mBinderMap.get(other) != null;
    }

    public String toString() {
        return format("ObservableVariable<<> : <>>", getName(), getValue());
    }

}