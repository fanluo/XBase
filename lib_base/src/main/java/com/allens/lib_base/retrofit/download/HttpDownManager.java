package com.allens.lib_base.retrofit.download;

import android.os.Handler;
import android.os.Looper;

import com.allens.lib_base.log.LogHelper;
import com.allens.lib_base.retrofit.XHttp;
import com.allens.lib_base.retrofit.download.info.DownInfo;
import com.allens.lib_base.retrofit.download.interceptor.DownloadInterceptor;
import com.allens.lib_base.retrofit.download.subscriber.ProgressDownSubscriber;
import com.allens.lib_base.retrofit.download.tools.FileManager;
import com.allens.lib_base.retrofit.impl.ApiService;
import com.allens.lib_base.retrofit.interceptor.LogInterceptor;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpDownManager {

    /*单利对象*/
    private volatile static HttpDownManager INSTANCE;
//    /*记录下载数据*/
//    private Set<DownInfo> downInfos;
//    /*回调sub队列*/
//    private HashMap<String, ProgressDownSubscriber> subMap;
    /*下载进度回掉主线程*/
    private Handler handler;

    public static HttpDownManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpDownManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpDownManager();
                }
            }
        }
        return INSTANCE;
    }


    public HttpDownManager() {
//        downInfos = new HashSet<>();
//        subMap = new HashMap<>();
        handler = new Handler(Looper.getMainLooper());
    }


    /**
     * 移除下载数据
     *
     * @param info
     */
    public void remove(DownInfo info) {
//        subMap.remove(info.getUrl());
//        downInfos.remove(info);
    }


    public void start(String url, String filePath) {
        DownInfo downInfo = new DownInfo();
        downInfo.setUrl(url);
        downInfo.setSavePath(filePath);
        startDown(downInfo);

    }

    public void startDown(DownInfo info) {
        /*正在下载不处理*/
//        if (info == null || subMap.get(info.getUrl()) != null) {
//            LogHelper.d("正在下载不处理");
//            subMap.get(info.getUrl()).setDownInfo(info);
//            return;
//        }
        LogHelper.d("添加回调处理类");
        /*添加回调处理类*/
        ProgressDownSubscriber subscriber = new ProgressDownSubscriber(info, handler);
        /*记录回调sub*/
//        subMap.put(info.getUrl(), subscriber);
        /*获取service，多次请求公用一个sercie*/
        ApiService httpService;
//        if (downInfos.contains(info)) {
//            httpService = info.getService();
//        } else {
            DownloadInterceptor interceptor = new DownloadInterceptor(subscriber);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //手动创建一个OkHttpClient并设置超时时间
            builder.connectTimeout(info.getConnectTimeOut(), TimeUnit.SECONDS);

            builder.addInterceptor(LogInterceptor.register(false));//添加拦截 测试
            builder.addInterceptor(interceptor);
            Retrofit retrofit = new Retrofit.Builder()
                    .client(builder.build())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    //如果 获取不到base url  就随便写一个 反正是用来下载的  可以随便写
                    .baseUrl(XHttp.getUrl() == null ? "http://134.175.88.222/" : XHttp.getUrl())
                    .build();
            httpService = retrofit.create(ApiService.class);
//        }
        info.setService(httpService);
//        downInfos.add(info);
        startDown(httpService, info, subscriber);

    }

    private void startDown(ApiService httpService, DownInfo info, ProgressDownSubscriber subscriber) {
        LogHelper.d("start get download info ");
        httpService.downloadFile("bytes=" + info.getReadLength() + "-", info.getUrl())
                /*指定线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*失败后的retry配置*/
//                .retryWhen(new RetryWhenNetworkException())
                /*读取下载写入文件*/
                .map(responseBody -> {
                    LogHelper.d("读取下载写入文件----》length %s",responseBody.contentLength());
                    FileManager.writeToFile(responseBody, new File(info.getSavePath()), info);
                    return info;
                })
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*数据回调*/
                .subscribe(subscriber);
    }

}
