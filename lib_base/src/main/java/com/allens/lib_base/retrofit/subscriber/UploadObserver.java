package com.allens.lib_base.retrofit.subscriber;

import com.allens.lib_base.retrofit.impl.OnHttpListener;
import com.allens.lib_base.retrofit.upload.OnUploadListener;
import com.google.gson.Gson;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class UploadObserver<T> extends BaseObserver<ResponseBody> {

    private Class<T> tClass;
    private OnUploadListener<T> listener;

    public UploadObserver(Class<T> tClass, OnUploadListener<T> listener) {
        this.tClass = tClass;
        this.listener = listener;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(ResponseBody responseBody) {
        super.onNext(responseBody);
        try {
            Gson gson = new Gson();
            String json = responseBody.string();
            T t = gson.fromJson(json, tClass);
            listener.onSuccess(t);
        } catch (Throwable e) {
            listener.onError(e);
        }
    }

    @Override
    public void onError(Throwable e) {
        listener.onError(e);
    }

    @Override
    public void onComplete() {

    }
}
