package com.techupstudio.otc_chingy.mychurch.core.utils.caching.core;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class AbstractMemoryCache<K, V> extends AbstractCache<K, V> {

    private static final String TAG = "MemoryCache";

    //Last argument true for LRU ordering
    private final Map<K, V> cache = Collections.synchronizedMap(
            new LinkedHashMap<K, V>(10, 1.5f, true)
    );

    //current allocated size
    private long consumedMemorySize = 0;

    //max memory cache folder used to download images in bytes
    private long usableMemoryLimit;

    public AbstractMemoryCache() {
        //use 25% of available heap size
        setUsableMemoryLimit(Runtime.getRuntime().maxMemory() / 4);
    }

    public long getUsableMemoryLimit() {
        return usableMemoryLimit;
    }

    public void setUsableMemoryLimit(long usableMemoryLimit) {
        this.usableMemoryLimit = usableMemoryLimit;
    }

    public long getConsumedMemorySize() {
        return consumedMemorySize;
    }

    @Override
    public boolean put(K key, V item) {
        try {
            if (cache.containsKey(key))
                consumedMemorySize -= computeByteSize(cache.get(key));
            cache.put(key, item);
            consumedMemorySize += computeByteSize(item);
            releaseMemory();
            return true;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    @Override
    public V get(K key) {
        try {
            return cache.get(key);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean remove(K key) {
        if (cache.containsKey(key)) {
            try {
                V value = cache.remove(key);
                consumedMemorySize -= computeByteSize(value);
                return true;
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean containsKey(K key) {
        return cache.containsKey(key);
    }

    @Override
    public boolean containsValue(V item) {
        return cache.containsValue(item);
    }

    @Override
    public Set<K> keys() {
        return cache.keySet();
    }

    @Override
    public Collection<V> values() {
        return cache.values();
    }

    public void clear() {
        cache.clear();
        consumedMemorySize = 0;
    }

    @Override
    public int size() {
        return cache.size();
    }

    private void releaseMemory() {

        if (consumedMemorySize > usableMemoryLimit) {
            //least recently accessed item will be the first one iterated
            Iterator<Entry<K, V>> i = cache.entrySet().iterator();

            while (i.hasNext()) {
                Entry<K, V> entry = i.next();
                consumedMemorySize -= computeByteSize(entry.getValue());
                i.remove();
                if (consumedMemorySize <= usableMemoryLimit)
                    break;
            }
        }
    }

    public abstract long computeByteSize(V item);//        return bitmap.getRowBytes() * bitmap.getHeight();

}