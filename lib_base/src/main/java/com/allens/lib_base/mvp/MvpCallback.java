package com.allens.lib_base.mvp;

public interface MvpCallback<V extends IView, P extends IPresenter<V>> {
    //创建Presenter  调用在init中
    P createPresenter();

    //创建View
    V createView();

    P getPresenter();

    void setPresenter(P presenter);

    V getMvpView();

    void setMvpView(V view);
}
