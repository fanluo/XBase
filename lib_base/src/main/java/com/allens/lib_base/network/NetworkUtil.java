package com.allens.lib_base.network;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * 描述:
 * <p>
 * Created by allens on 2018/3/28.
 */

public class NetworkUtil {


    public static final int TYPE_NONE = -1;
    public static final int TYPE_MOBILE = 0;
    public static final int TYPE_WIFI = 1;
    private static NetworkChangeReceiver networkChangeReceiver;


    /***
     * register 网络变化广播
     * @param context
     */
    public static void register(Context context,OnNetWorkListener listener) {
        if (networkChangeReceiver == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            networkChangeReceiver = new NetworkChangeReceiver(listener);
            context.registerReceiver(networkChangeReceiver, intentFilter);
        }
    }


    /***
     * 取消注册 网络变化广播
     * @param context
     */
    public static void unRegister(Context context) {
        if (networkChangeReceiver != null)
            context.unregisterReceiver(networkChangeReceiver);
    }


    /**
     * 检查网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }
        return true;
    }


    /**
     * 获取网络状态
     *
     * @param context
     * @return one of TYPE_NONE, TYPE_MOBILE, TYPE_WIFI
     * @permission android.permissionDialog.ACCESS_NETWORK_STATE
     */
    public static int getNetWorkStates(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return checkState_23orNew(context);
        } else {
            return checkState_23(context);
        }
    }

    //检测当前的网络状态
    //API版本23以下时调用此方法进行检测
    //因为API23后getNetworkInfo(int networkType)方法被弃用
    private static int checkState_23(Context context) {
        //步骤1：通过Context.getSystemService(Context.CONNECTIVITY_SERVICE)获得ConnectivityManager对象
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //步骤2：获取ConnectivityManager对象对应的NetworkInfo对象
        //NetworkInfo对象包含网络连接的所有信息
        //步骤3：根据需要取出网络连接信息
        //获取WIFI连接的信息
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        Boolean isWifiConn = networkInfo.isConnected();

        //获取移动数据连接的信息
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        Boolean isMobileConn = networkInfo.isConnected();
        if (isWifiConn) {
            return TYPE_WIFI;//WIFI
        } else if (isMobileConn) {
            return TYPE_MOBILE;//移动数据
        } else {
            return TYPE_NONE;
        }
    }

    // API 23及以上时调用此方法进行网络的检测
    // getAllNetworks() 在API 21后开始使用
    //步骤非常类似
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static int checkState_23orNew(Context context) {
        //获得ConnectivityManager对象
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //获取所有网络连接的信息
        Network[] networks = connMgr.getAllNetworks();
        //用于存放网络连接信息
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
        if (isWifiConn) {
            return TYPE_WIFI;//WIFI
        } else if (isMobileConn) {
            return TYPE_MOBILE;//移动数据
        } else {
            return TYPE_NONE;
        }
    }
}
