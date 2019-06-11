package com.allens.lib_base.network;

/***
 * 网络变化接口
 */
public interface OnNetWorkListener {
     void onNetWorkStatus(boolean isMobileConn, boolean isWifiConn);
}
