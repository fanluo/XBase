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
}
