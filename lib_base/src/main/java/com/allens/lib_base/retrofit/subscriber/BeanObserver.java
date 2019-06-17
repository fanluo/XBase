package com.allens.lib_base.retrofit.subscriber;

import com.allens.lib_base.retrofit.impl.OnHttpListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class BeanObserver<T> extends BaseObserver<ResponseBody> {

    private Class<T> tClass;
    private OnHttpListener<T> listener;

    public BeanObserver(Class<T> tClass, OnHttpListener<T> listener) {
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
