package com.allens.lib_base.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public abstract class BaseActivity extends AppCompatActivity implements ITopView, IUiHelper, IPermissionImp {
    private Activity mActivity;
    private RxPermissions rxPermissions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        ActivityStack.getInstance().pushActivity(this);

        baseInit(savedInstanceState);
        initView(savedInstanceState);
        //这是一个Activity或Fragment实例
        rxPermissions = new RxPermissions(this);
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTextSize(setTextSize());
    }

    @Override
    public int setTextSize() {
        return 1;
    }

    /***
     * 修改系统字体大小
     * @param fontScale 1是标准字体
     */
    private void updateTextSize(int fontScale) {
        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        if (fontScale <= 1) {
            fontScale = 1;
        }
        configuration.fontScale = fontScale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            ActivityStack.getInstance().removeActivity(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (rxPermissions != null) {
            rxPermissions = null;
        }
    }

    protected void baseInit(Bundle savedInstanceState) {
        setContentView(getContentViewId());
    }


    public abstract int getContentViewId();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initListener();


    @Override
    public Observable<Boolean> permissionRequest(String... permissions) {
        return rxPermissions.request(permissions);
    }

    @Override
    public Observable<Permission> permissionRequestEach(String... permissions) {
        return rxPermissions.requestEach(permissions);
    }

    @Override
    public RxPermissions getRxPermissions() {
        return rxPermissions;
    }

    @Override
    public void silence(boolean skip) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            //设置透明状态栏,这样才能让 ContentView 向上
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //取消  兼容  虚拟按键
            //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //设置状态栏颜色
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
            ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
                ViewCompat.setFitsSystemWindows(mChildView, false);
            }
        }
    }

    @Override
    public int getWith() {
        WindowManager wm = getWindowManager();
        return wm.getDefaultDisplay().getWidth();
    }

    @Override
    public int getHeight() {
        WindowManager wm = getWindowManager();
        return wm.getDefaultDisplay().getHeight();
    }

    @Override
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this, clz));
    }

    @Override
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showFragment(Fragment fragment, int resId) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment != null)
            transaction.replace(resId, fragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    @Override
    public String getResId(int id) {
        return getResources().getString(id);
    }

    @Override
    public Locale getLocal() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }

    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        return networkinfo != null && networkinfo.isAvailable();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
