package com.allens.lib_base.mvp;


import io.reactivex.disposables.Disposable;

public interface IDisposablePool {

    /***
     * 添加
     * @param disposable
     */
    void addDisposable(Disposable disposable);

    void removeDisposable(Disposable disposable);

    /***
     * 丢弃连接 在view销毁时调用
     */
    void clearPool();
}