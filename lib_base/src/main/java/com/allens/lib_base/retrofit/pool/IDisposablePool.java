package com.allens.lib_base.retrofit.pool;

import io.reactivex.disposables.Disposable;


public interface IDisposablePool {
    void addDisposable(Disposable disposable);

    void removeDisposable(Disposable disposable);

    /**
     * 丢弃连接 在view销毁时调用
     */
    void clearPool();
}