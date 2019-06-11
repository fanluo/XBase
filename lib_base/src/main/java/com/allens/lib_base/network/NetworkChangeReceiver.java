package com.allens.lib_base.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private OnNetWorkListener listener;

    public NetworkChangeReceiver(OnNetWorkListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("网络状态发生变化");
        //检测API是不是小于23，因为到了API23之后getNetworkInfo(int networkType)方法被弃用
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connMgr == null) {
                return;
            }
            //获取ConnectivityManager对象对应的NetworkInfo对象
            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //获取移动数据连接的信息
            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                System.out.println("WIFI已连接,移动数据已连接");
                if (listener != null)
                    listener.onNetWorkStatus(true, true);
            } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
                System.out.println("WIFI已连接,移动数据已断开");
                if (listener != null)
                    listener.onNetWorkStatus(false, true);
            } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                if (listener != null)
                    listener.onNetWorkStatus(true, false);
                System.out.println("WIFI已断开,移动数据已连接");
            } else {
                if (listener != null)
                    listener.onNetWorkStatus(false, false);
            }
            //API大于23时使用下面的方式进行网络监听
        } else {
            System.out.println("API level 大于23");
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connMgr == null) {
                return;
            }
            //获取所有网络连接的信息
            Network[] networks = connMgr.getAllNetworks();
            if (networks == null) {
                return;
            }
            //通过循环将网络信息逐个取出来
            boolean isMobileConn = false;
            boolean isWifiConn = false;
            for (int i = 0; i < networks.length; i++) {
                //获取ConnectivityManager对象对应的NetworkInfo对象
                NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                if (ConnectivityManager.TYPE_MOBILE == networkInfo.getType()) {
                    isMobileConn = networkInfo.isConnected();
                } else if (ConnectivityManager.TYPE_WIFI == networkInfo.getType()) {
                    isWifiConn = networkInfo.isConnected();
                }
            }
            if (listener != null) {
                listener.onNetWorkStatus(isMobileConn, isWifiConn);
            }
        }
    }
}
