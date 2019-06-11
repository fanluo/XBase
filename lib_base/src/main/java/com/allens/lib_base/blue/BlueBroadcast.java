package com.allens.lib_base.blue;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class BlueBroadcast extends BroadcastReceiver {
    private OnBlueStatusListener listener;

    public BlueBroadcast(OnBlueStatusListener blueStatusListener) {
        this.listener = blueStatusListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action == null) {
            return;
        }
        //获取蓝牙设备实例【如果无设备链接会返回null，如果在无实例的状态下调用了实例的方法，会报空指针异常】
        //主要与蓝牙设备有关系
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        switch (action) {
            case BluetoothDevice.ACTION_ACL_CONNECTED:
                if (listener != null) {
                    listener.onBlueStatusChange(BlueStatus.Connect);
                }
                if (device == null) {
                    System.out.println("【蓝牙广播】蓝牙设备 已链接");
                    return;
                }
                System.out.println("【蓝牙广播】蓝牙设备:" + device.getName() + "已链接");
                break;
            case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                if (listener != null) {
                    listener.onBlueStatusChange(BlueStatus.DisConnecet);
                }
                if (device == null) {
                    System.out.println("【蓝牙广播】蓝牙设备: 断开链接");
                    return;
                }
                System.out.println("【蓝牙广播】蓝牙设备:" + device.getName() + "断开链接");
                break;
            case BluetoothAdapter.ACTION_STATE_CHANGED:
                int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                switch (blueState) {
                    case BluetoothAdapter.STATE_OFF:
                        System.out.println("【蓝牙广播】蓝牙关闭");
                        if (listener != null) {
                            listener.onBlueStatusChange(BlueStatus.Close);
                        }
                        break;
                    case BluetoothAdapter.STATE_ON:
                        System.out.println("【蓝牙广播】蓝牙开启");
                        if (listener != null) {
                            listener.onBlueStatusChange(BlueStatus.Open);
                        }
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        System.out.println("【蓝牙广播】蓝牙准备关闭，提前关闭连接");
                        if (listener != null) {
                            listener.onBlueStatusChange(BlueStatus.PreapareClose);
                        }
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
}
