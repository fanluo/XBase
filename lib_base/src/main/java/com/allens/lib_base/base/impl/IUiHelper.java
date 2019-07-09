package com.allens.lib_base.base.impl;

import android.view.View;

public interface IUiHelper {


    void toast(String msg);

    void toast(int resId);


    void showLoading();

    void hideLoading();


    /***
     * 绑定控件
     * @param resId
     * @param <T>
     * @return
     */
    <T extends View> T $(int resId);


    /***
     * 获取资源的String
     * @param id
     * @return
     */
    String getResId(int id);


    /* 获取SharedPreference 参数*/
    <T> T getPref(String key);

    <T> T getPref(String key, T defaultValue);

    <T> boolean putPref(String key, T value);

    boolean deleteAllPref();

    boolean deletePref(String key);

    long getPrefCount();

    boolean containsPref(String key);
}
