package com.allens.lib_base.base;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import java.util.Locale;

public interface ITopView {


    /***
     * 是否开启沉寂式布局
     */
    void silence();

    int getWith();

    int getHeight();

    /***
     * 跳转
     * @param clz activity
     */
    void startActivity(Class<?> clz);

    void startActivity(Class<?> clz, Bundle bundle);

    void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode);


    void showFragment(Fragment fragment, int resId);


    /***
     * 获取当前系统语言状态
     * @return
     */
    Locale getLocal();

    /***
     * 检查网络是否可用
     * @return true 可用
     *         false 不可用
     */
    boolean isNetworkAvailable();

    Context getContext();
}
