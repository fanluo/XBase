package com.allens.lib_base.mvp;

public interface MvpCallback<M extends BaseModel, V extends BaseView, P extends BasePresenter> {
    M createModel();

    V createView();

    P createPresenter();
}
