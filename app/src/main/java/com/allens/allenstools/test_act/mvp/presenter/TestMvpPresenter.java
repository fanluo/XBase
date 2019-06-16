package com.allens.allenstools.test_act.mvp.presenter;

import com.allens.allenstools.test_act.mvp.contract.TestMvpContract;
import com.allens.lib_base.mvp.BasePresenter;

public class TestMvpPresenter extends BasePresenter<TestMvpContract.Model, TestMvpContract.View> implements TestMvpContract.Presenter {
    @Override
    protected void onViewDestroy() {

    }
}
