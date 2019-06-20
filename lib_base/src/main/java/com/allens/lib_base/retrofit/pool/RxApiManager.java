package com.allens.lib_base.retrofit.pool;

import android.util.ArrayMap;

import java.util.Set;

import io.reactivex.disposables.Disposable;

public class RxApiManager implements RxActionImpl<Object> {

    private static RxApiManager sInstance = null;

    private ArrayMap<Object, Disposable> maps;


    public static RxApiManager newInstances() {

        if (sInstance == null) {
            synchronized (RxApiManager.class) {
                if (sInstance == null) {
                    sInstance = new RxApiManager();
                }
            }
        }
        return sInstance;
    }

    private RxApiManager() {
        maps = new ArrayMap<>();
    }

    @Override
    public void add(Object tag, Disposable disposable) {
        maps.put(tag, disposable);
    }


    @Override
    public void cancel(Object tag) {
        if (maps.isEmpty()) {
            return;
        }
        Disposable disposable = maps.get(tag);
        if (disposable == null) {
            return;
        }
        if (!disposable.isDisposed()) {
            disposable.dispose();
            maps.remove(tag);
        }
    }

    @Override
    public void cancelAll() {
        if (maps.isEmpty()) {
            return;
        }
        Set<Object> keys = maps.keySet();
        for (Object apiKey : keys) {
            cancel(apiKey);
        }
    }
}
