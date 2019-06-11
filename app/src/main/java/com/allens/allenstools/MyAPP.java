package com.allens.allenstools;

import android.app.Application;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.allens.lib_base.app.BaseApplication;
import com.allens.lib_base.bean.LogInfo;
import com.allens.lib_base.blue.BlueStatus;
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
//                .path("11111111log")
                .maxFileSize(10)
                .maxM(5)
                .tag("log--->")
                .build();
    }

    @Override
    public void onAppFrontOrBack(boolean isBack) {
        super.onAppFrontOrBack(isBack);
        LogHelper.i("当前是否在后台 %s", isBack);
    }

    @Override
    public boolean onOpenOnePx() {
        //是否开启一像素保活
        return true;
    }

    @Override
    public void onAppLockScreen(boolean isBack) {
        LogHelper.i("app 当前是否是锁屏状态 %s", isBack);
    }

    @Override
    public void onAppNetWorkStatus(boolean isMobileConn, boolean isWifiConn) {
        super.onAppNetWorkStatus(isMobileConn, isWifiConn);
        LogHelper.i("app 网络状态 手机连接 %s, wifi 连接 %s", isMobileConn, isWifiConn);
    }

    @Override
    public void onBlueStatusChange(BlueStatus status) {
        super.onBlueStatusChange(status);
        LogHelper.i("app 蓝牙连接状态 %s", status);
    }
}
