package com.techupstudio.otc_chingy.mychurch.core.utils.caching.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public abstract class AbstractFileCache<K> extends AbstractCache<K, File> {

    public abstract File getCacheDirectory();

    @Override
    public boolean remove(K key) {
        File file = get(key);
        if (file != null && file.exists()) {
            return file.delete();
        }
        return false;
    }

    @Override
    public boolean containsKey(K key) {
        File file = get(key);
        return file != null && file.exists();
    }

    @Override
    public boolean containsValue(File item) {
        if (item != null && item.exists() && item.getParentFile() != null)
            return item.getParentFile().equals(getCacheDirectory());
        return false;
    }

    @Override
    public Collection<File> values() {
        ArrayList<File> fileList = new ArrayList<>();
        File cacheDir = getCacheDirectory();
        if (cacheDir != null && cacheDir.exists()) {
            File[] files = cacheDir.listFiles();
            if (files != null) fileList.addAll(Arrays.asList(files));
        }
        return fileList;
    }

    @Override
    public void clear() {
        Collection<File> files = values();
        for (File file : files) {
            file.delete();
        }
    }

    @Override
    public int size() {
        return values().size();
    }
}