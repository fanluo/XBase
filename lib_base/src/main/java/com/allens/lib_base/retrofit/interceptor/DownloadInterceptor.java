package com.allens.lib_base.retrofit.interceptor;

import com.allens.lib_base.retrofit.body.DownloadResponseBody;
import com.allens.lib_base.retrofit.impl.OnDownLoadListener;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class DownloadInterceptor implements Interceptor {
    private OnDownLoadListener downloadListener;
    private static String mDownloadKey;

    public DownloadInterceptor(OnDownLoadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder().body(
                new DownloadResponseBody(mDownloadKey, response.body(), downloadListener)).build();
    }


    public static Interceptor register(String key, OnDownLoadListener downloadListener) {
        mDownloadKey = key;
        return new DownloadInterceptor(downloadListener);
    }
}