package com.sample.telstratest;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Vara on 4/14/2015.
 * This class exposes methods to define memory cache, add Bitmap images to the cache, retrieve
 * images from the cache and clear the cache.
 */
public class ImageCacheManager {

    private LruCache<String, Bitmap> mMemoryCache;

    public ImageCacheManager(int cacheSizeInKB)
    {
        mMemoryCache = new LruCache<String, Bitmap>(cacheSizeInKB){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount()/1024;
            }
        };
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap)
    {
        if(getBitmapFromMemoryCache(key) == null)
            mMemoryCache.put(key, bitmap);
    }

    public Bitmap getBitmapFromMemoryCache(String key)
    {
        return mMemoryCache.get(key);
    }

    public void clearMemoryCache()
    {
        mMemoryCache.evictAll();
    }
}
