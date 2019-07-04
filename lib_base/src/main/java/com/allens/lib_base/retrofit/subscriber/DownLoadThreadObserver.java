package com.allens.lib_base.retrofit.subscriber;

import com.allens.lib_base.log.LogHelper;
import com.allens.lib_base.retrofit.download.FileManager;
import com.allens.lib_base.retrofit.impl.OnDownLoadListener;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class DownLoadThreadObserver extends BaseObserver<ResponseBody> implements OnDownLoadListener {

    private long start;
    private String key, path;

    public DownLoadThreadObserver(long start, String key, String path) {
        this.start = start;
        this.key = key;
        this.path = path;
    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        FileManager downloadManager = new FileManager();
        LogHelper.i("多线程下载 当前线程 %s ; file path %s", Thread.currentThread().getName(), path);
        boolean isSuccess = downloadManager.downLoadBIg(responseBody, path, key, start, this);
        LogHelper.i("多线程下载 当前线程 %s ， 是否下载成功 %s", Thread.currentThread().getName(), isSuccess);
    }

    @Override
    public void onError(Throwable e) {
        LogHelper.i("多线程下载 当前线程 %s ; error: %s", Thread.currentThread().getName(), e.getMessage());
    }

    @Override
    public void onComplete() {
        LogHelper.i("多线程下载 当前线程 %s ; 下载完成", Thread.currentThread().getName());
    }

    @Override
    public void onStart(String key) {

    }

    @Override
    public void onProgress(String key, int terms) {
        LogHelper.i("多线程下载 当前线程 %s,progress %s", Thread.currentThread().getName(), terms);
    }

    @Override
    public void onError(String key, Throwable e) {
        LogHelper.i("多线程下载 当前线程 %s ; errorBySave: %s", Thread.currentThread().getName(), e.getMessage());
    }

    @Override
    public void onSuccess(String key, String path) {

    }

    @Override
    public void already(String key, String path) {

    }
}
