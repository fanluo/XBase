package com.allens.allenstools;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.allens.lib_base.app.BaseApplication;
import com.allens.lib_base.bean.LogInfo;
import com.allens.lib_base.log.LogHelper;

public class MyAPP extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    @Override
    protected LogInfo onLogInfo() {
        return LogInfo.builder()
                .fileName("log")
                .isOpen(true)
                .path("11111111log")
                .maxFileSize(10)
                .maxM(5)
                .tag("log--->")
                .build();
    }

    @Override
    protected void onAppFrontOrBack(boolean isBack) {
        LogHelper.i("当前锁屏状态 %s", isBack);
    }

}
