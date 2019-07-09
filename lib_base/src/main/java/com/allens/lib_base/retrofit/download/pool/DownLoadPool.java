package com.allens.lib_base.retrofit.download.pool;

import android.os.Handler;

import com.allens.lib_base.retrofit.download.DownLoadManager;
import com.allens.lib_base.retrofit.download.impl.OnDownLoadListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.reactivex.disposables.Disposable;
import lombok.Getter;

public class DownLoadPool {

    private static DownLoadPool instance;
    private final HashMap<String, Disposable> hashMap;

    @Getter
    private final HashMap<String, OnDownLoadListener> listenerHashMap;

    public static DownLoadPool getInstance() {
        if (instance == null) {
            synchronized (DownLoadPool.class) {
                if (instance == null) {
                    instance = new DownLoadPool();
                }
            }
        }
        return instance;
    }


    private DownLoadPool() {
        hashMap = new HashMap<>();
        listenerHashMap = new HashMap<>();
    }

    public void add(String url, Disposable disposable) {
        hashMap.put(url, disposable);
    }

    public void remove(String url) {
        Disposable disposable = hashMap.get(url);
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        hashMap.remove(url);
        listenerHashMap.remove(url);
    }

    public void pause(String url) {
        Disposable disposable = hashMap.get(url);
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public void clearAll() {
        for (Map.Entry<String, Disposable> entry : hashMap.entrySet()) {
            remove(entry.getKey());
        }
    }

}
