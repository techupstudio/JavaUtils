package com.techupstudio.otc_chingy.mychurch.core.utils.caching.memory.item;

public abstract class CacheItem<T> {
    T item;

    public CacheItem(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public abstract long getByteSize();
}
