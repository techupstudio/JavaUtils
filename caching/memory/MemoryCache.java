package com.techupstudio.otc_chingy.mychurch.core.utils.caching.memory;

import com.techupstudio.otc_chingy.mychurch.core.utils.caching.core.AbstractMemoryCache;
import com.techupstudio.otc_chingy.mychurch.core.utils.caching.memory.item.CacheItem;

public class MemoryCache<K, V extends CacheItem<?>> extends AbstractMemoryCache<K, V> {
    @Override
    public long computeByteSize(V item) {
        return item.getByteSize();
    }
}
