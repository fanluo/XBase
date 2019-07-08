package com.allens.lib_base.retrofit.download;

import com.allens.lib_base.log.LogHelper;
import com.allens.lib_base.retrofit.HttpManager;
import com.allens.lib_base.retrofit.download.impl.OnDownLoadListener;
import com.allens.lib_base.retrofit.impl.ApiService;
import com.allens.lib_base.retrofit.subscriber.DownLoadObserver;
import com.allens.lib_base.retrofit.tool.FileTool;

import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class DownLoadManager {


    public static void startDownLoad(String url, String savePath, OnDownLoadListener loadListener) {
        Retrofit retrofit = HttpManager.create().createDownLoadRetrofit();
        retrofit.create(ApiService.class)
                .downloadFile(url)
                .subscribeOn(Schedulers.io())//在子线程取数据
                .unsubscribeOn(Schedulers.io())
                .map(responseBody -> {
                    LogHelper.d("download map %s", responseBody.contentLength());
                    boolean isSuccess = FileTool.downToFile(responseBody, savePath);
                    return responseBody;
                })
                .subscribe(new DownLoadObserver(loadListener));
    }
}
