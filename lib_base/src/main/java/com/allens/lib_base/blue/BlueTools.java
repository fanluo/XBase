package com.allens.lib_base.blue;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.IntentFilter;

public class BlueTools {

    private BlueBroadcast blueBroadcast;

    public void register(Context context, OnBlueStatusListener blueStatusListener) {
        /***
         * 蓝牙监听广播
         */
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        intentFilter.addAction("android.bluetooth.BluetoothAdapter.STATE_TURNING_OFF");
        intentFilter.addAction("android.bluetooth.BluetoothAdapter.STATE_OFF");
        intentFilter.addAction("android.bluetooth.BluetoothAdapter.STATE_TURNING_ON");
        intentFilter.addAction("android.bluetooth.BluetoothAdapter.STATE_ON");
        blueBroadcast = new BlueBroadcast(blueStatusListener);
        context.registerReceiver(blueBroadcast, intentFilter);
    }

    public void unRegister(Context context) {
        if (blueBroadcast != null)
            context.unregisterReceiver(blueBroadcast);
    }
}
