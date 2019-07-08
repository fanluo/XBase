package com.allens.lib_base.retrofit.subscriber;

import com.allens.lib_base.retrofit.download.bean.DownLoadBean;
import com.allens.lib_base.retrofit.download.impl.OnDownLoadListener;
import com.allens.lib_base.retrofit.download.pool.DownLoadPool;
import com.allens.lib_base.retrofit.impl.OnHttpListener;
import com.google.gson.Gson;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class DownLoadObserver extends BaseObserver<DownLoadBean> {

    private OnDownLoadListener loadListener;
    private DownLoadBean downLoadBean;

    private String url;

    public DownLoadObserver(String url, OnDownLoadListener loadListener) {
        this.loadListener = loadListener;
        this.url = url;
    }

    @Override
    public void onSubscribe(Disposable d) {
        DownLoadPool.getInstance().add(url, d);
    }

    @Override
    public void onNext(DownLoadBean downLoadBean) {
        super.onNext(downLoadBean);
        this.downLoadBean = downLoadBean;
    }

    @Override
    public void onError(Throwable e) {
        if (loadListener != null) {
            loadListener.onError(url, e);
        }
    }

    @Override
    public void onComplete() {
        DownLoadPool.getInstance().remove(url);
        if (loadListener != null) {
            if (downLoadBean.getIsSuccess() != null && downLoadBean.getIsSuccess())
                loadListener.onSuccess(url, downLoadBean.getPath());
            else
                loadListener.onError(url, downLoadBean.getThrowable() == null ? new Throwable("未知错误") : downLoadBean.getThrowable());
        }
    }
}
