package com.allens.lib_base.blue;


public interface OnBlueStatusListener {
    /***
     * blue 状态的变化
     * @param status 0 关闭
     *               1 打开
     *               2 连接断开
     *               3 连接成功
     *               4 准备关闭蓝牙
     */
    void onBlueStatusChange(BlueStatus status);
}