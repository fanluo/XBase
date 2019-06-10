package com.allens.allenstools.test_act;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.allens.allenstools.R;
import com.allens.lib_base.base.BaseActivity;

@Route(path = "/act/silence")
public class SilenceAct extends BaseActivity {
    @Override
    public int getContentViewId() {
        return R.layout.activity_silence;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void silence() {
        super.silence();
    }
}
