package com.allens.lib_base.retrofit.download.subscriber;


import android.os.Handler;


import com.allens.lib_base.retrofit.download.impl.DownloadProgressListener;
import com.allens.lib_base.retrofit.download.impl.HttpDownOnNextListener;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.lang.ref.SoftReference;


/**
 * 断点下载处理类Subscriber
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * Created by WZG on 2016/7/16.
 */
public class ProgressDownSubscriber<T> implements DownloadProgressListener, Subscriber<T> {

    @Override
    public void update(long read, long count, boolean done) {

    }

    @Override
    public void onSubscribe(Subscription s) {

    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {

    }
}