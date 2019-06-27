package com.allens.lib_base.retrofit.download;

import com.allens.lib_base.retrofit.impl.OnDownLoadListener;
import com.allens.lib_base.retrofit.provider.HttpProvider;
import com.allens.lib_base.retrofit.subscriber.DownLoadObserver;
import com.allens.lib_base.retrofit.tool.FileTool;

public class DownLoadManager {

    public static void doDownLoad(String key, String url, String downLoadPath, OnDownLoadListener loadListener) {
        if (FileTool.isAlreadyDownLoadFromUrl(downLoadPath, url)) {
            loadListener.already(key, downLoadPath);
            return;
        }
        loadListener.onStart(key);
        HttpProvider.getObservableDownLoad(url)
                .subscribe(new DownLoadObserver(key, url, downLoadPath, loadListener));
    }

    public static void doDownLoad(String key, String url, String fileName, String downLoadPath, OnDownLoadListener loadListener) {
        if (FileTool.isAlreadyDownLoadFromUrl(downLoadPath, fileName)) {
            loadListener.already(key, downLoadPath);
            return;
        }
        loadListener.onStart(key);
        HttpProvider.getObservableDownLoad(url)
                .subscribe(new DownLoadObserver(key, fileName, downLoadPath, loadListener));
    }
}
