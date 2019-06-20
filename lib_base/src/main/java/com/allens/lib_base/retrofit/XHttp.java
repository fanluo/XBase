package com.allens.lib_base.retrofit;

import android.content.Context;

import com.allens.lib_base.log.LogHelper;
import com.allens.lib_base.retrofit.impl.ApiService;
import com.allens.lib_base.retrofit.impl.OnDownLoadListener;
import com.allens.lib_base.retrofit.impl.OnHttpListener;
import com.allens.lib_base.retrofit.interceptor.HeardInterceptor;
import com.allens.lib_base.retrofit.pool.RxApiManager;
import com.allens.lib_base.retrofit.subscriber.BeanObserver;
import com.allens.lib_base.retrofit.subscriber.DownLoadObserver;
import com.allens.lib_base.retrofit.tool.UrlTool;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class XHttp {

    private static String url;

    public static final class Builder {
        public static final int DEFAULT_READ_TIMEOUT = 10;
        public static final int DEFAULT_WRITE_TIMEOUT = 10;
        public static final long DEFAULT_CONNECT_TIMEOUT = 10;
        public static final boolean retryOnConnectionFailure = false;


        private Context context;


        public Builder(Context context) {
            this.context = context;
            HttpManager.create();
        }

        public Builder writeTimeout(int timeout, TimeUnit unit) {
            HttpManager.create().getHttpConfigBuilder().writeTime(true);
            HttpManager.create().getOkhttpBuilder().writeTimeout(timeout, unit);
            return this;
        }

        public Builder readTimeout(int timeout, TimeUnit unit) {
            HttpManager.create().getHttpConfigBuilder().readTime(true);
            HttpManager.create().getOkhttpBuilder().readTimeout(timeout, unit);
            return this;
        }

        public Builder connectTimeout(int timeout, TimeUnit unit) {
            HttpManager.create().getHttpConfigBuilder().connectTime(true);
            HttpManager.create().getOkhttpBuilder().connectTimeout(timeout, unit);
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            url = baseUrl;
            HttpManager.create().getRetrofitBuilder().baseUrl(baseUrl);
            return this;
        }

        public Builder retryOnConnectionFailure(boolean retryOnConnectionFailure) {
            HttpManager.create().getHttpConfigBuilder().retryOnConnectionFailure(true);
            HttpManager.create().getOkhttpBuilder().retryOnConnectionFailure(retryOnConnectionFailure);
            return this;
        }

        public Builder isLog(Boolean isLog) {
            HttpManager.create().getHttpConfigBuilder().isLog(isLog);
            return this;
        }

        public Builder isLogToFile(Boolean toFile) {
            HttpManager.create().getHttpConfigBuilder().logToFile(toFile);
            return this;
        }

        public Builder addHeard(final Map<String, String> headMap) {
            if (headMap != null && headMap.size() > 0) {
                HttpManager.create().getOkhttpBuilder().addInterceptor(HeardInterceptor.register(headMap));
                return this;
            }
            return this;
        }


        public XHttp build() {
            HttpManager.create().build();
            return new XHttp();
        }
    }

    public <T> void doGet(final Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        HashMap<String, Object> map = new HashMap<>();
        listener.onMap(map);
        String getUrl = url + "/" + parameter;
        if (map.size() > 0) {
            String param = UrlTool.prepareParam(map);
            if (param.trim().length() >= 1) {
                getUrl += "?" + param;
            }
        }
        HttpManager.create().getService(ApiService.class)
                .doGet(getUrl)
                .subscribeOn(Schedulers.io())//在子线程取数据
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//在主线程显示ui
                .subscribe(new BeanObserver<T>(tClass, listener));
    }

    public <T> void doPost(final Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        HashMap<String, Object> map = new HashMap<>();
        listener.onMap(map);
        HttpManager.create().getService(ApiService.class)
                .doPost(parameter, map)
                .subscribeOn(Schedulers.io())//在子线程取数据
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//在主线程显示ui
                .subscribe(new BeanObserver<T>(tClass, listener));

    }

    public <T> void doBody(final Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        HashMap<String, Object> map = new HashMap<>();
        listener.onMap(map);
        JSONObject json = new JSONObject(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json.toString());
        HttpManager.create().getService(ApiService.class)
                .doBody(parameter, requestBody)
                .subscribeOn(Schedulers.io())//在子线程取数据
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//在主线程显示ui
                .subscribe(new BeanObserver<T>(tClass, listener));

    }

    public <T> void doPut(final Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        HashMap<String, Object> map = new HashMap<>();
        listener.onMap(map);
        HttpManager.create().getService(ApiService.class)
                .doPost(parameter, map)
                .subscribeOn(Schedulers.io())//在子线程取数据
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//在主线程显示ui
                .subscribe(new BeanObserver<T>(tClass, listener));

    }

    public <T> void doDelete(final Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        HashMap<String, Object> map = new HashMap<>();
        listener.onMap(map);
        HttpManager.create().getService(ApiService.class)
                .doDelete(parameter, map)
                .subscribeOn(Schedulers.io())//在子线程取数据
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//在主线程显示ui
                .subscribe(new BeanObserver<T>(tClass, listener));

    }


    public void doDownLoad(String key, String url, String downLoadPath, OnDownLoadListener loadListener) {
        HttpManager.create().getService(ApiService.class)
                .downloadSmallFile(url)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new DownLoadObserver(key, url, downLoadPath, loadListener));
    }


    public void cancelDownLoad(String key) {
        LogHelper.i("cancel download key %s", key);
        RxApiManager.newInstances().cancel(key);
    }
}
