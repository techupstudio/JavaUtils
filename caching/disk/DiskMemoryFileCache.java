package com.techupstudio.otc_chingy.mychurch.core.utils.caching.disk;

import androidx.annotation.NonNull;

import com.techupstudio.otc_chingy.mychurch.core.utils.caching.memory.MemoryCache;
import com.techupstudio.otc_chingy.mychurch.core.utils.caching.memory.item.FileCacheItem;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.Funcs;

import java.io.File;
import java.util.Map;
import java.util.Objects;

public class DiskMemoryFileCache extends DiskFileCache {
    private final MemoryCache<String, FileCacheItem> memoryCache;

    public DiskMemoryFileCache(@NonNull File cacheDirectory) {
        super(cacheDirectory);
        this.memoryCache = new MemoryCache<>();
    }

    public MemoryCache<String, FileCacheItem> getMemoryCache() {
        return memoryCache;
    }

    @Override
    public boolean put(String key, File item) {
        if (item != null && key != null
                && memoryCache.containsKey(key)
                && memoryCache.get(key) != null
                && memoryCache.get(key).getItem() == item) {
            return true;
        }
        memoryCache.put(key, new FileCacheItem(item));
        return super.put(key, item);
    }

    @Override
    public File get(String key) {
        if (memoryCache.containsKey(key))
            return memoryCache.get(key).getItem();
        File file = super.get(key);
        if (file != null)
            memoryCache.put(key, new FileCacheItem(file));
        return file;
    }

    @Override
    public boolean remove(String key) {
        memoryCache.remove(key);
        return super.remove(key);
    }

    @Override
    public void clear() {
        memoryCache.clear();
        super.clear();
    }

    @Override
    public boolean putAll(Map<? extends String, ? extends File> map) {
        memoryCache.putAll(Funcs.map(map, (entry) -> new MemoryCacheEntry(entry.getKey(), new FileCacheItem(entry.getValue()))));
        return super.putAll(map);
    }

    @Override
    public boolean putIfAbsent(String key, File item) {
        memoryCache.putIfAbsent(key, new FileCacheItem(item));
        return super.putIfAbsent(key, item);
    }

    public static class MemoryCacheEntry implements Map.Entry<String, FileCacheItem> {

        String key;
        FileCacheItem value;

        public MemoryCacheEntry(String key, FileCacheItem value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        @Override
        public FileCacheItem getValue() {
            return value;
        }

        @Override
        public FileCacheItem setValue(FileCacheItem value) {
            FileCacheItem old = this.getValue();
            this.value = value;
            return old;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MemoryCacheEntry that = (MemoryCacheEntry) o;
            return Objects.equals(key, that.key) &&
                    Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }
}
