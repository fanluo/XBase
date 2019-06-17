package com.allens.allenstools.test_act.mvp;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.allens.allenstools.R;
import com.allens.allenstools.test_act.mvp.contract.TestMvpContract;
import com.allens.allenstools.test_act.mvp.model.TestMvpModel;
import com.allens.allenstools.test_act.mvp.presenter.TestMvpPresenter;
import com.allens.lib_base.mvp.BaseMvpActivity;

@Route(path = "/act/mvp")
public class TestMvpAct extends BaseMvpActivity<TestMvpModel, TestMvpContract.View, TestMvpPresenter> implements TestMvpContract.View {
    @Override
    public int getContentViewId() {
        return R.layout.activity_mvp;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {
        $(R.id.btn).setOnClickListener(v -> {
            presenter.testToast();
        });

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

    @Override
    public void showToast() {
        toast("测试成功");
    }
}
