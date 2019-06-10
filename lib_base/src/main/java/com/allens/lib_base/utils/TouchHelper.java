package com.allens.lib_base.utils;

import android.view.MotionEvent;

public class TouchHelper {
    private static TouchHelper mInstance;

    private double nLenStart;
    private double nLenEnd;

    public static TouchHelper create() {
        if (mInstance == null) {
            synchronized (TouchHelper.class) {
                mInstance = new TouchHelper();
            }
        }
        return mInstance;
    }

    public boolean onTouchEvent(MotionEvent event) {

        int pCount = event.getPointerCount();// 触摸设备时手指的数量

        int action = event.getAction();// 获取触屏动作。比如：按下、移动和抬起等手势动作


        // 手势按下且屏幕上是两个手指数量时
        if ((action & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN && pCount == 2) {

            // 获取按下时候两个坐标的x轴的水平距离，取绝对值
            int xLen = Math.abs((int) event.getX(0) - (int) event.getX(1));
            // 获取按下时候两个坐标的y轴的水平距离，取绝对值
            int yLen = Math.abs((int) event.getY(0) - (int) event.getY(1));

            // 根据x轴和y轴的水平距离，求平方和后再开方获取两个点之间的直线距离。此时就获取到了两个手指刚按下时的直线距离
            nLenStart = Math.sqrt((double) xLen * xLen + (double) yLen * yLen);

        } else if ((action & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP && pCount == 2) {// 手势抬起且屏幕上是两个手指数量时

            // 获取抬起时候两个坐标的x轴的水平距离，取绝对值
            int xLen = Math.abs((int) event.getX(0) - (int) event.getX(1));
            // 获取抬起时候两个坐标的y轴的水平距离，取绝对值
            int yLen = Math.abs((int) event.getY(0) - (int) event.getY(1));

            // 根据x轴和y轴的水平距离，求平方和后再开方获取两个点之间的直线距离。此时就获取到了两个手指抬起时的直线距离
            nLenEnd = Math.sqrt((double) xLen * xLen + (double) yLen * yLen);

            // 根据手势按下时两个手指触点之间的直线距离A和手势抬起时两个手指触点之间的直线距离B。比较A和B的大小，得出用户是手势放大还是手势缩小
            if (nLenEnd > nLenStart) {
                if (listener != null) {
                    listener.onTouchBig();
                }
            } else if (nLenEnd < nLenStart) {
                if (listener != null) {
                    listener.onTouchSmall();
                }
            }
        }

        return true;
    }


    private OnTouchHelperListener listener;


    public OnTouchHelperListener getListener() {
        return listener;
    }

    public void setListener(OnTouchHelperListener listener) {
        this.listener = listener;
    }

    public interface OnTouchHelperListener {
        void onTouchBig();

        void onTouchSmall();
    }

}
