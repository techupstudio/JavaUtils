package com.techupstudio.otc_chingy.mychurch.core.utils.caching;

import com.techupstudio.otc_chingy.mychurch.core.utils.caching.core.AbstractFileCache;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.testing.Preconditions;
import com.techupstudio.otc_chingy.mychurch.core.utils.io.FileManager;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import androidx.annotation.NonNull;

public class FileCache extends AbstractFileCache<String> {

    @NonNull
    private File cacheDirectory;

    public FileCache(@NonNull File cacheDirectory) {
        this.cacheDirectory = cacheDirectory;
        if (!cacheDirectory.exists()) {
            if (!cacheDirectory.mkdirs()) throw new Error("could not create cache directory!.");
        } else {
            Preconditions.checkArgument(cacheDirectory.isDirectory(), "Cached directory cant be a file!");
        }
    }

    @NonNull
    @Override
    public File getCacheDirectory() {
        return cacheDirectory;
    }

    public void setCacheDirectory(@NonNull File cacheDirectory) {
        if (!cacheDirectory.exists()) {
            if (!cacheDirectory.mkdirs()) throw new Error("could not create cache directory!.");
        }
        this.cacheDirectory = cacheDirectory;
    }

    @Override
    public boolean put(String key, File item) {
        File cachedFile = new File(getCacheDirectory(), key);
        if (FileManager.makeFileOrFolder(cachedFile))
            return FileManager.save(item, cachedFile);
        return false;
    }

    @Override
    public File get(String key) {
        File file = new File(getCacheDirectory(), key);
        if (file.exists()) return file;
        return null;
    }

    @Override
    public Set<String> keys() {
        HashSet<String> set = new HashSet<>();
        String[] list = getCacheDirectory().list();
        if (list != null) set.addAll(Arrays.asList(list));
        return set;
    }
}
