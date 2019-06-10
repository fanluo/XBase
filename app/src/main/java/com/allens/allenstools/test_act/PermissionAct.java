package com.allens.allenstools.test_act;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.allens.allenstools.R;
import com.allens.lib_base.base.BaseActivity;

@Route(path = "/act/permission")
public class PermissionAct extends BaseActivity {
    @Override
    public int getContentViewId() {
        return R.layout.activity_permission;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        $(R.id.button).setOnClickListener(v -> {
        });
    }

    @Override
    protected void initListener() {

    }
}
