package com.allens.lib_base.mvp;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.allens.lib_base.base.BaseActivity;


/***
 *
 * @param <V>
 * @param <P>
 */
public abstract class MvpFragment<M extends BaseModel, V extends BaseView, P extends BasePresenter> extends Fragment implements MvpCallback<M, V, P> {

    protected P presenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        presenter = createPresenter();
        if (presenter != null) {
            presenter.registerModel(createModel());
            presenter.registerView(createView());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (presenter != null) {
            presenter.destroy();
        }
    }
}
