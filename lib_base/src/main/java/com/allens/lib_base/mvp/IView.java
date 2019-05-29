package com.allens.lib_base.mvp;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;

public interface IView extends LifecycleOwner {
    Context getContext();

    void showToast(String info);
}
