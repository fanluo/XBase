package com.allens.allenstools.test_act.mvp;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.allens.allenstools.test_act.mvp.contract.TestMvpContract;
import com.allens.allenstools.test_act.mvp.model.TestMvpModel;
import com.allens.allenstools.test_act.mvp.presenter.TestMvpPresenter;
import com.allens.lib_base.mvp.BaseMvpActivity;

@Route(path = "/act/mvp")
public class TestMvpAct extends BaseMvpActivity<TestMvpModel, TestMvpContract.View, TestMvpPresenter> implements TestMvpContract.View {
    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }


    @Override
    public TestMvpModel createModel() {
        return new TestMvpModel();
    }

    @Override
    public TestMvpContract.View createView() {
        return this;
    }

    @Override
    public TestMvpPresenter createPresenter() {
        return new TestMvpPresenter();
    }
}
