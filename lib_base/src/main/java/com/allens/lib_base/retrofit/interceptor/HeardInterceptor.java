package com.allens.lib_base.retrofit.interceptor;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;

public class HeardInterceptor {

    //添加请求头
    public static Interceptor register(final Map<String, String> headMap) {
        return chain -> {
            Request request = chain.request();
            Request.Builder builder1 = request.newBuilder();
            Request build = null;
            for (Map.Entry<String, String> entry : headMap.entrySet()) {
                build = builder1.addHeader(entry.getKey(), entry.getValue()).build();
            }
            return chain.proceed(build);
        };
    }
}
