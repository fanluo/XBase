package com.allens.lib_base.retrofit.download.pool;

import android.os.Handler;

import com.allens.lib_base.log.LogHelper;
import com.allens.lib_base.retrofit.download.DownLoadManager;
import com.allens.lib_base.retrofit.download.impl.OnDownLoadListener;
import com.orhanobut.hawk.Hawk;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.disposables.Disposable;
import lombok.Getter;

public class DownLoadPool {

    private static DownLoadPool instance;
    //任务
    @Getter
    private final ConcurrentHashMap<String, Disposable> hashMap;

    @Getter
    //监听
    private final ConcurrentHashMap<String, OnDownLoadListener> listenerHashMap;
    //下载位置
    private final ConcurrentHashMap<String, String> pathMap;


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
        hashMap = new ConcurrentHashMap<>();
        listenerHashMap = new ConcurrentHashMap<>();
        pathMap = new ConcurrentHashMap<>();
    }

    public void add(String url, Disposable disposable) {
        hashMap.put(url, disposable);
    }

    public void add(String url, String path) {
        pathMap.put(url, path);
    }

    public void add(String url, OnDownLoadListener loadListener) {
        listenerHashMap.put(url, loadListener);
    }

    public OnDownLoadListener getListener(String url) {
        return listenerHashMap.get(url);
    }

    public String getDownLoadPath(String url) {
        LogHelper.i("path Map %s", pathMap);
        return pathMap.get(url);
    }

    public void remove(String url) {
        Disposable disposable = hashMap.get(url);
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        hashMap.remove(url);
        listenerHashMap.remove(url);
        pathMap.remove(url);
        Hawk.delete(url);
    }

    public void pause(String url) {
        Disposable disposable = hashMap.get(url);
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }



}
