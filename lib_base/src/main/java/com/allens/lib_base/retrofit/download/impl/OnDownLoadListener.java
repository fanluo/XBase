package com.allens.lib_base.retrofit.download.impl;

public abstract class OnDownLoadListener implements DownLoadProgressListener {


    @Override
    public void update(long read, long count, boolean done) {

    }

    public abstract void onProgress(int progress);

    public abstract void onError(Throwable throwable);

    public abstract void onSuccess(String path);

}
