package com.allens.lib_base.mvp;

import android.content.Context;

import androidx.annotation.CallSuper;


public abstract class MvpPresenter<V extends IView, M extends IModel> {
    protected V mView;
    protected M mModel;

    @CallSuper
    public void attachView(V view) {
        mView = view;
        if (mModel == null) {
            mModel = createModel();
        }
    }

    protected abstract M createModel();

    @CallSuper
    public void detachView() {
        if (mModel != null) {
            mModel.clearPool();
        }
        mModel = null;
        mView = null;
    }

    public Context getContext() {
        return mView.getContext();
    }

    public M getModel() {
        return mModel;
    }
}
