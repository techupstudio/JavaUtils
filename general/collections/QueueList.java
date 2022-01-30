package com.techupstudio.otc_chingy.mychurch.core.utils.general.collections;


import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces.Action;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class QueueList<T> extends Queue<T> implements Iterable<T> {


    public T peek(int index) {
        return items.peek(index);
    }

    public T pop(int index) {
        return items.pop(index);
    }

    public void replace(int index, T object) {
        items.update(index, object);
    }

    public void remove(int index) {
        items.remove(index);
    }

    public void remove(T object) {
        items.remove(object);
    }

    public void insert(int index, T object) {
        items.insert(index, object);
    }

    public boolean contains(T object) {
        return items.contains(object);
    }

    public int findFirstIndexOf(T object) {
        return items.firstIndexOf(object);
    }

    public int findLastIndexOf(T object) {
        return items.lastIndexOf(object);
    }

    public List<T> toList() {
        return items.asList();
    }

    public Queue<T> toQueue() {
        return this;
    }


    public void forEach(Action<T> action) {
        items.forEach(action);
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void forEach(@NonNull Consumer<? super T> action) {
        items.forEach(action);
    }

    @Override
    public String toString() {
        return super.toString().replace("Queue", "QueueList");
    }
}