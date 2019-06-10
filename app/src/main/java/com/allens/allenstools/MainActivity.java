package com.allens.allenstools;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Camera;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.allens.lib_base.base.BaseActivity;
import com.allens.lib_base.log.LogHelper;

public class MainActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {
        $(R.id.btn_touch).setOnClickListener(v -> {
            ARouter.getInstance().build("/act/touch").navigation();
        });

        $(R.id.btn_permission).setOnClickListener(v -> {
            ARouter.getInstance().build("/act/permission").navigation();
        });

        $(R.id.btn_silence).setOnClickListener(v -> {
            ARouter.getInstance().build("/act/silence").navigation();
        });
    }
}
