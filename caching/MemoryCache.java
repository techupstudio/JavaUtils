package com.techupstudio.otc_chingy.mychurch.core.utils.caching;

import com.techupstudio.otc_chingy.mychurch.core.utils.caching.core.AbstractMemoryCache;
import com.techupstudio.otc_chingy.mychurch.core.utils.caching.memory.CacheItem;

public class MemoryCache<K, V extends CacheItem> extends AbstractMemoryCache<K, V> {
    @Override
    public long computeByteSize(CacheItem item) {
        return item.getByteSize();
    }
}
