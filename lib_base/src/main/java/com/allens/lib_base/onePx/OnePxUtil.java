package com.allens.lib_base.onePx;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class OnePxUtil {

    private static OnePixelReceiver onePixelReceiver;

    /***
     *
     * @param context
     * @param isOpenOnePx 是否开启一像素包活
     * @param lockScreenListener 锁屏监听
     */
    public static void register(Context context, boolean isOpenOnePx, OnLockScreenListener lockScreenListener) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        onePixelReceiver = new OnePixelReceiver(isOpenOnePx, lockScreenListener);
        context.registerReceiver(onePixelReceiver, intentFilter);
    }


    public static void unRgeister(Context context) {
        if (onePixelReceiver != null)
            context.unregisterReceiver(onePixelReceiver);
    }


}
