package com.techupstudio.otc_chingy.mychurch.core.utils.general.collections.observable;


import com.techupstudio.otc_chingy.mychurch.core.utils.general.collections.observable.interfaces.BiDirectionalBindMethod;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.collections.observable.interfaces.OnValueChangedListener;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.collections.observable.interfaces.UniDirectionalBindMethod;

import androidx.annotation.NonNull;

public class Binder<T, R> {
    private ObservableVariable<T> requester;
    private ObservableVariable<R> receiver;
    private OnValueChangedListener<T> requesterBindListener;
    private OnValueChangedListener<R> receiverBindListener;

    public Binder(ObservableVariable<T> requester, ObservableVariable<R> receiver) {
        if (requester == null && receiver == null)
            throw new Error("Cannot bind null objects");
        if (requester == null || receiver == null)
            throw new Error("Cannot create bind with NullPointer");
        else if (requester == receiver)
            throw new Error("Cannot create bind with self");
        else if (requester.hasBinder(receiver) || receiver.hasBinder(requester))
            throw new Error("bind " + (requester.mBinderMap.get(receiver) != null ?
                    requester.mBinderMap.get(receiver) : receiver.mBinderMap.get(requester)) +
                    " already exist. unbind to set again!");
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

    public void bindUniDirectional() {

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

    public void bindUniDirectional(final Class<R> rClass) {

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

    public void bindUniDirectional(final UniDirectionalBindMethod<T, R> uniDirectionalBindMethod) {

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

    public void bindBiDirectional() {

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

    public void bindBiDirectional(final Class<R> rClass, final Class<T> tClass) {

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


    public void bindBiDirectional(final BiDirectionalBindMethod<T, R> biDirectionalBindMethod) {

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

    public void unbind() {
        if (requesterBindListener != null)
            requester.removeOnValueChangedListener(requesterBindListener);
        if (receiverBindListener != null)
            receiver.removeOnValueChangedListener(receiverBindListener);
    }

    @NonNull
    @Override
    public String toString() {
        return "Binder<" + requester + ": " + receiver + ">(" + (requesterBindListener == null ? "uni" : "bi") + "directional)";
    }
}
