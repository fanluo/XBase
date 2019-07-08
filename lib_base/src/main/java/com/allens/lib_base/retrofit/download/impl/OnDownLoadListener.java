package com.allens.lib_base.retrofit.download.impl;

public abstract class OnDownLoadListener implements DownLoadProgressListener {


    @Override
    public void update(String key,long read, long count, boolean done) {

    }

    public abstract void onProgress(String key,int progress);

    public abstract void onError(String key,Throwable throwable);

    public abstract void onSuccess(String key,String path);

}
