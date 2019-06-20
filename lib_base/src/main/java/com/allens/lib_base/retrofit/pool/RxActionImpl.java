package com.allens.lib_base.retrofit.pool;

import org.reactivestreams.Subscription;

import io.reactivex.disposables.Disposable;

public interface RxActionImpl<T> {
    void add(T tag, Disposable disposable);

    void cancel(T tag);

    void cancelAll();

}
