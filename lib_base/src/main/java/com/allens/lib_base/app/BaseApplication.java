package com.allens.lib_base.app;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.allens.lib_base.bean.LogInfo;
import com.allens.lib_base.log.LogHelper;
import com.allens.lib_base.utils.AppFrontBackHelper;
import com.orhanobut.hawk.Hawk;

import java.io.File;


/***
 * base application
 */
public abstract class BaseApplication extends Application implements IToolmpl {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        //key value
        init_Hawk();
        //log
        init_log();
        //前后台变化
        init_backOrFrontListener();
    }

    /**
     * =======================================================================================================================
     * key value
     * =======================================================================================================================
     */
    private void init_Hawk() {
        Hawk.init(context).build();
    }

    /**
     * =======================================================================================================================
     * log
     * =======================================================================================================================
     */
    private void init_log() {
        LogInfo logInfo = onLogInfo();
        if (logInfo == null) {
            return;
        }
        LogHelper.setOpen(logInfo.isOpen);
        if (TextUtils.isEmpty(logInfo.getPath())) {
            logInfo.setPath(getBasePath());
        }
        LogHelper.init(this, logInfo.getTag(), logInfo.getPath(), logInfo.getMaxM(), logInfo.getMaxFileSize());
    }

    protected abstract LogInfo onLogInfo();


    /**
     * =======================================================================================================================
     * 前后台
     * =======================================================================================================================
     */
    private AppFrontBackHelper helper;

    /***
     * 判断App  是否前后台状态
     */
    private void init_backOrFrontListener() {
        helper = new AppFrontBackHelper();
        helper.register(this, new AppFrontBackHelper.OnAppStatusListener() {
            @Override
            public void onFront() {
                //应用切到前台处理
                onAppFrontOrBack(false);
            }

            @Override
            public void onBack() {
                //应用切到后台处理
                onAppFrontOrBack(true);
            }
        });
    }

    protected abstract void onAppFrontOrBack(boolean isBack);


    /**
     * =======================================================================================================================
     * 获取沙盒位置
     * =======================================================================================================================
     */
    @Override
    public String getBasePath() {
        String p = Environment.getExternalStorageDirectory().getPath();
        File f = this.getExternalFilesDir(null);
        if (null != f) {
            p = f.getAbsolutePath();
        }
        return p;
    }
}
