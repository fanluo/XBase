package com.allens.lib_base.mvp;


import android.os.Bundle;

import com.allens.lib_base.base.BaseActivity;


/***
 *
 * @param <V>
 * @param <P>
 */
public abstract class BaseMvpActivity<M extends BaseModel, V extends BaseView, P extends BasePresenter> extends BaseActivity implements MvpCallback<M, V, P> {

    protected P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //创建Presenter
        presenter = createPresenter();
        if (presenter != null) {
            //将Model层注册到Presenter中
            presenter.registerModel(createModel());
            //将View层注册到Presenter中
            presenter.registerView(createView());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            //Activity销毁时的调用，让具体实现BasePresenter中onViewDestroy()方法做出决定
            presenter.destroy();
        }
    }
}
