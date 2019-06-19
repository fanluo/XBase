package com.allens.lib_base.retrofit.pool;

import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.disposables.Disposable;

public class DownLoadDisposablePoolImpl implements IDisposablePool {
    private ConcurrentHashMap<String, Disposable> map;

    @Override
    public void addDisposable(Disposable disposable) {

    }


    public void addDisposable(String key, Disposable disposable) {
        if (null == disposable) {
            return;
        }
        if (map == null) {
            map = new ConcurrentHashMap<>();
        }
        map.put(key, disposable);
    }

    @Override
    public void removeDisposable(Disposable disposable) {
    }

    public void removeDisposable(String key) {
        if (map != null) {
            map.remove(key);
            Disposable disposable = map.get(key);
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }

    @Override
    public void clearPool() {
        if (map != null) {
            for (String key : map.keySet()) {
                Disposable disposable = map.get(key);
                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                }
                removeDisposable(key);
            }
        }
        map = null;
    }
}
