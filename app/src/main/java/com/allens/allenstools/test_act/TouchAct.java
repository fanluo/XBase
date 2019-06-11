package com.allens.allenstools.test_act;

import android.os.Bundle;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.allens.allenstools.R;
import com.allens.lib_base.base.BaseActivity;
import com.allens.lib_base.log.LogHelper;

@Route(path = "/act/touch")
public class TouchAct extends BaseActivity {
    @Override
    public int getContentViewId() {
        return R.layout.activity_touch;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }


    @Override
    public void onTouchBig() {
        super.onTouchBig();
        toast("放大");
        LogHelper.i("放大");
    }

    @Override
    public void onTouchSmall() {
        super.onTouchSmall();
        toast("缩小");
        LogHelper.i("缩小");
    }
}
