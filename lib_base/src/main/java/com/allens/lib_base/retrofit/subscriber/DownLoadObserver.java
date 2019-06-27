package com.allens.lib_base.retrofit.subscriber;

import android.app.DownloadManager;

import com.allens.lib_base.log.LogHelper;
import com.allens.lib_base.retrofit.HttpManager;
import com.allens.lib_base.retrofit.impl.OnDownLoadListener;
import com.allens.lib_base.retrofit.impl.OnHttpListener;
import com.allens.lib_base.retrofit.pool.RxApiManager;
import com.allens.lib_base.retrofit.tool.DownLoadManager;
import com.allens.lib_base.retrofit.tool.FileTool;
import com.google.gson.Gson;

import java.util.logging.Logger;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class DownLoadObserver extends BaseObserver<ResponseBody> {


    private String key, url, downLoadPath;

    private OnDownLoadListener loadListener;
    private Disposable disposable;
    private String newFilePath;
    private boolean isSuccess;

    public DownLoadObserver(String key, String url, String downLoadPath, OnDownLoadListener loadListener) {
        this.loadListener = loadListener;
        this.key = key;
        this.url = url;
        this.downLoadPath = downLoadPath;
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
        RxApiManager.newInstances().add(key, d);
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        super.onNext(responseBody);
        String fileName = FileTool.getFileName(url);
        LogHelper.i("下载的文件名称 %s", fileName);
        newFilePath = FileTool.getString(downLoadPath, url);
        LogHelper.i("下载的文件保存位置 %s", newFilePath);
        DownLoadManager downloadManager = new DownLoadManager();
        isSuccess = downloadManager.downLoad(responseBody, newFilePath, key, loadListener);
    }

    @Override
    public void onError(Throwable e) {
        loadListener.onError(key, e);
    }

    @Override
    public void onComplete() {
        LogHelper.i("onComplete downLoad key %s, isDisposed : %s, isDownLoad success %s", key, disposable.isDisposed(), isSuccess);
        if (isSuccess)
            loadListener.onSuccess(key, newFilePath);
    }
}
