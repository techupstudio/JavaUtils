package com.techupstudio.otc_chingy.mychurch.core.utils.caching.memory.item;

import com.techupstudio.otc_chingy.mychurch.core.utils.io.FileManager;

import java.io.File;
import java.util.Objects;

public class FileCacheItem extends CacheItem<File> {

    public FileCacheItem(File item) {
        super(item);
    }

    @Override
    public long getByteSize() {
        return getItem() == null ? 0 : FileManager.sizeInBytes(getItem());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileCacheItem that = (FileCacheItem) o;
        return Objects.equals(getItem(), that.getItem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItem());
    }
}