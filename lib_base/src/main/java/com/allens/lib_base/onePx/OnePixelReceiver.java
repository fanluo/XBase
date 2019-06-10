package com.allens.lib_base.onePx;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * 描述:
 * <p>
 * <p>
 * 广播
 *
 * @author allens
 * @date 2018/1/24
 */

public class OnePixelReceiver extends BroadcastReceiver {
    private OnLockScreenListener lockScreenListener;
    private Boolean isOpenOnePx;

    public OnePixelReceiver(boolean isOpenOnePx, OnLockScreenListener lockScreenListener) {
        this.lockScreenListener = lockScreenListener;
        this.isOpenOnePx = isOpenOnePx;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //屏幕关闭启动1像素Activity
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            lockScreenListener.onLockScreen(true);
            if (isOpenOnePx) {
                Log.e("OnePx", "屏幕关闭启动1像素Activity");
                Intent it = new Intent(context, OnePxActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                context.startActivity(it);
            }
            //屏幕打开 结束1像素
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            lockScreenListener.onLockScreen(false);
            if (isOpenOnePx) {
                Log.e("OnePx", "屏幕打开 结束1像素");
                context.sendBroadcast(new Intent("finish"));
            }
        }
    }
}