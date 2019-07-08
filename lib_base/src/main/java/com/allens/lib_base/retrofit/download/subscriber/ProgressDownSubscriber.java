package com.allens.lib_base.retrofit.download.subscriber;


import android.os.Handler;


import com.allens.lib_base.log.LogHelper;
import com.allens.lib_base.retrofit.download.HttpDownManager;
import com.allens.lib_base.retrofit.download.enums.DownState;
import com.allens.lib_base.retrofit.download.impl.DownloadProgressListener;
import com.allens.lib_base.retrofit.download.impl.HttpDownOnNextListener;
import com.allens.lib_base.retrofit.download.info.DownInfo;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.lang.ref.SoftReference;
import java.util.Observable;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 断点下载处理类Subscriber
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 */
public class ProgressDownSubscriber<T> implements DownloadProgressListener, Observer<T> {
    //弱引用结果回调
    private SoftReference<HttpDownOnNextListener> mSubscriberOnNextListener;
    /*下载数据*/
    private DownInfo downInfo;
    private Handler handler;

    public ProgressDownSubscriber(DownInfo downInfo, Handler handler) {
        this.mSubscriberOnNextListener = new SoftReference<>(downInfo.getListener());
        this.downInfo = downInfo;
        this.handler = handler;
    }

    public void setDownInfo(DownInfo downInfo) {
        this.mSubscriberOnNextListener = new SoftReference<>(downInfo.getListener());
        this.downInfo = downInfo;
    }


    @Override
    public void onSubscribe(Disposable d) {
        LogHelper.d("下载   onSubscribe");
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onStart();
        }
        downInfo.setState(DownState.START);
    }

    @Override
    public void onNext(T t) {
        LogHelper.d("下载   onNext");
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onNext(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        LogHelper.d("下载   onError %s", e.getMessage());
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onError(e);
        }
        HttpDownManager.getInstance().remove(downInfo);
        downInfo.setState(DownState.ERROR);
//        DbDownUtil.getInstance().update(downInfo);
    }

    @Override
    public void onComplete() {
        LogHelper.d("下载   onComplete ");
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onComplete();
        }
        HttpDownManager.getInstance().remove(downInfo);
        downInfo.setState(DownState.FINISH);
//        DbDownUtil.getInstance().update(downInfo);
    }

    @Override
    public void update(long read, long count, boolean done) {
        LogHelper.d("下载   update read:%s; count: %s; done: %s ", read, count, done);
        if (downInfo.getCountLength() > count) {
            read = downInfo.getCountLength() - count + read;
        } else {
            downInfo.setCountLength(count);
        }
        downInfo.setReadLength(read);

        if (mSubscriberOnNextListener.get() == null || !downInfo.isUpdateProgress()) return;
        handler.post(() -> {
            /*如果暂停或者停止状态延迟，不需要继续发送回调，影响显示*/
            if (downInfo.getState() == DownState.PAUSE || downInfo.getState() == DownState.STOP)
                return;
            downInfo.setState(DownState.DOWN);
            mSubscriberOnNextListener.get().updateProgress(downInfo.getReadLength(), downInfo.getCountLength());
        });
    }


}