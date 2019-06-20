package com.allens.lib_base.retrofit.compose;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxComposeManager {

   public static final ObservableTransformer mainCompose = upstream -> upstream
           .subscribeOn(Schedulers.io())//在子线程取数据
           .unsubscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread());//在主线程显示ui

    public static <T> ObservableTransformer<T, T> applyMain() {
        return (ObservableTransformer<T, T>) mainCompose;
    }
}
