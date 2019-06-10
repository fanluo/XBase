package com.allens.lib_base.app;

public interface IToolmpl {

    //获取沙盒位置
    String getBasePath();

    //锁屏检测
    void LockScreen();

    //前后台判断
    void backOrFront();
}
