package com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces;

import android.os.Build;

import java.util.Iterator;
import java.util.function.Consumer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public abstract class Enumerator<T> implements Iterable<T> {

    private int index = 0;

    public abstract T get(int index);

    public abstract int size();

    public boolean hasNext() {
        return index < size();
    }

    public T getNext() {
        return get(index);
    }

    public void reset() {
        index = 0;
    }

    public int enumerationsRemaining() {
        return size() - index;
    }

    public int enumerationsPassed() {
        return index;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean isComplete() {
        return index == size() - 1;
    }


    @NonNull
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            int i = 0;

            @Override
            public boolean hasNext() {
                return i < size();
            }

            @Override
            public T next() {
                i++;
                return get(i - 1);
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void forEach(@NonNull Consumer<? super T> action) {
        for (int i = 0; i < size(); i++) {
            action.accept(get(i));
        }
    }

}
