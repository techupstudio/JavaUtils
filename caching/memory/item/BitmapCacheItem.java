package com.techupstudio.otc_chingy.mychurch.core.utils.caching.memory.item;

import android.graphics.Bitmap;

import java.util.Objects;

public class BitmapCacheItem extends CacheItem<Bitmap> {

    public BitmapCacheItem(Bitmap item) {
        super(item);
    }

    @Override
    public long getByteSize() {
        return getItem() == null ? 0 : getItem().getWidth() * getItem().getWidth();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BitmapCacheItem that = (BitmapCacheItem) o;
        return Objects.equals(getItem(), that.getItem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItem());
    }
}