package com.allens.lib_base.app.impl;

public interface IToolmpl {

    //获取沙盒位置
    String getBasePath();

    //锁屏检测
    void LockScreen();

    //前后台判断
    void backOrFront();

    //网络变化
    void netWorkChange();

    //蓝牙广播变化
    void blueStatusChange();
}
