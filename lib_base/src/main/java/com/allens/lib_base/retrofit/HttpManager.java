package com.allens.lib_base.retrofit;

import com.allens.lib_base.retrofit.interceptor.LogInterceptor;

import java.util.concurrent.TimeUnit;

import lombok.Getter;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class HttpManager {
    private static HttpManager mInstance;

    @Getter
    private OkHttpClient.Builder okhttpBuilder;
    @Getter
    private Retrofit.Builder retrofitBuilder;
    @Getter
    private final HttpConfig.HttpConfigBuilder httpConfigBuilder;
    @Getter
    private Retrofit retrofit;


    private HttpManager() {
        okhttpBuilder = new OkHttpClient.Builder();
        retrofitBuilder = new Retrofit.Builder();
        httpConfigBuilder = new HttpConfig.HttpConfigBuilder();
    }

    public static HttpManager create() {
        if (mInstance == null) {
            synchronized (HttpManager.class) {
                mInstance = new HttpManager();
            }
        }
        return mInstance;
    }

    public HttpManager build() {
        HttpConfig httpConfig = httpConfigBuilder.build();
        if (!httpConfig.connectTime) {
            okhttpBuilder.connectTimeout(XHttp.Builder.DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        }
        if (!httpConfig.readTime) {
            okhttpBuilder.readTimeout(XHttp.Builder.DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);
        }
        if (!httpConfig.readTime) {
            okhttpBuilder.writeTimeout(XHttp.Builder.DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS);
        }
        if (!httpConfig.retryOnConnectionFailure) {
            okhttpBuilder.retryOnConnectionFailure(XHttp.Builder.retryOnConnectionFailure);
        }
        if (httpConfig.isLog) {
            okhttpBuilder.addInterceptor(LogInterceptor.register(httpConfig.logToFile));//添加拦截
        }

        retrofit = retrofitBuilder
                .client(okhttpBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return this;
    }

    //获取接口类
    public <T> T getService(Class<T> tClass) {
        if (retrofit == null) {
            new Throwable("please create than build").printStackTrace();
        }
        return retrofit.create(tClass);
    }
}
