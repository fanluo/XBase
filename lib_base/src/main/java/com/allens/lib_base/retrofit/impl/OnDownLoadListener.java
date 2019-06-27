package com.allens.lib_base.retrofit.impl;

public interface OnDownLoadListener {
    void onProgress(String key, int terms);

    void onError(String key, Throwable e);

    void onSuccess(String key, String path);

    void already(String path);
}
