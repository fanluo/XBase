package com.allens.lib_base.retrofit.interceptor;

import android.util.Log;

import com.allens.lib_base.log.LogHelper;

import okhttp3.logging.HttpLoggingInterceptor;

public class LogInterceptor {
    //创建拦截器
    public static HttpLoggingInterceptor register(Boolean logToFile) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> {
            Log.e("log>>>", "retrofitBack -> " + message); //打印retrofit日志
            if (logToFile)
                LogHelper.i(">> %s", message);
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }
}
