package com.allens.lib_base.mvp;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;

import com.allens.lib_base.base.BaseActivity;


/***
 *
 * @param <V>
 * @param <P>
 */
public abstract class MvpActivity<V extends IView, P extends IPresenter<V>> extends BaseActivity implements MvpCallback<V, P> {

    protected P mPresenter;
    protected V mView;

    @Override
    protected void baseInit(@Nullable Bundle savedInstanceState) {
        super.baseInit(savedInstanceState);
        init(savedInstanceState);
    }

    /**
     * 注意如果在这里获取intent的数据在构造中传给Presenter的话,获取代码需要在super调用之前
     */
    @CallSuper
    protected void init(Bundle savedInstanceState) {
        mView = createView();
        if (getPresenter() == null) {
            mPresenter = createPresenter();
            if (mPresenter != null) {
                mPresenter.attachView(getMvpView());
                getLifecycle().addObserver(mPresenter);
            }
        }
    }

    @Override
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(P presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public V getMvpView() {
        return this.mView;
    }

    @Override
    public void setMvpView(V view) {
        this.mView = view;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            setMvpView(null);
            setPresenter(null);
        }
    }

}
