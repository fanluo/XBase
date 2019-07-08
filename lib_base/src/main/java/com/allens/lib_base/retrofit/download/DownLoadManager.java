package com.allens.lib_base.retrofit.download;

import android.os.Handler;
import android.os.Looper;

import com.allens.lib_base.log.LogHelper;
import com.allens.lib_base.retrofit.HttpManager;
import com.allens.lib_base.retrofit.download.impl.OnDownLoadListener;
import com.allens.lib_base.retrofit.download.pool.DownLoadPool;
import com.allens.lib_base.retrofit.impl.ApiService;
import com.allens.lib_base.retrofit.subscriber.DownLoadObserver;
import com.allens.lib_base.retrofit.tool.FileTool;
import com.orhanobut.hawk.Hawk;

import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class DownLoadManager {


    private static DownLoadManager instance;
    private final Handler handler;

    public static DownLoadManager getInstance() {
        if (instance == null) {
            synchronized (DownLoadManager.class) {
                if (instance == null) {
                    instance = new DownLoadManager();
                }
            }
        }
        return instance;
    }


    private DownLoadManager() {
        handler = new Handler(Looper.getMainLooper());
    }

    public void startDownLoad(String url, String savePath, String name, OnDownLoadListener loadListener) {
        long currentLength = Hawk.get(url, 0L);
        Retrofit retrofit = HttpManager.create().createDownLoadRetrofit();
        LogHelper.i("startDownLoad current %s",currentLength);
        retrofit.create(ApiService.class)
                .downloadFile("bytes=" + currentLength + "-", url)
                .subscribeOn(Schedulers.io())//在子线程取数据
                .unsubscribeOn(Schedulers.io())
                .map(responseBody -> {
                    LogHelper.d("download map %s", responseBody.contentLength());
                    return FileTool.downToFile(url, currentLength, responseBody, savePath, name, handler, loadListener);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DownLoadObserver(url, loadListener));
    }

    public void startDownLoad(String url, String savePath, OnDownLoadListener loadListener) {
        startDownLoad(url, savePath, url, loadListener);
    }

    public void pause(String url) {
        DownLoadPool.getInstance().remove(url);
    }
}
