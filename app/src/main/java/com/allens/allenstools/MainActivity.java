package com.allens.allenstools;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Camera;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.allens.lib_base.base.BaseActivity;

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
        $(R.id.test).setOnClickListener(v -> {
            // 1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
            ARouter.getInstance().build("/test/activity").navigation();
        });
    }

    @Override
    public void onTouchBig() {
        super.onTouchBig();
        System.out.println("======= big");
    }

    @Override
    public void onTouchSmall() {
        super.onTouchSmall();
        System.out.println("======= small");
    }
}
