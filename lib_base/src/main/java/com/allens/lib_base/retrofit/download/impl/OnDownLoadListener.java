package com.allens.lib_base.retrofit.download.impl;

public abstract class OnDownLoadListener implements DownLoadProgressListener {


    @Override
    public void update(String key, long read, long count, boolean done) {

    }

    public abstract void onProgress(String key, int progress);

    public void onError(String key, Throwable throwable) {

    }

    public void onSuccess(String key, String path) {

    }

    public void onPause(String key) {

    }

    public void onCancel(String key) {

    }

}
