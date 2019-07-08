package com.allens.lib_base.retrofit.subscriber;

import com.allens.lib_base.retrofit.download.impl.OnDownLoadListener;
import com.allens.lib_base.retrofit.impl.OnHttpListener;
import com.google.gson.Gson;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class DownLoadObserver extends BaseObserver<ResponseBody> {

    private OnDownLoadListener loadListener;

    public DownLoadObserver(OnDownLoadListener loadListener) {
        this.loadListener = loadListener;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(ResponseBody responseBody) {
        super.onNext(responseBody);
    }

    @Override
    public void onError(Throwable e) {
        if (loadListener != null) {
            loadListener.onError(e);
        }
    }

    @Override
    public void onComplete() {

    }
}
