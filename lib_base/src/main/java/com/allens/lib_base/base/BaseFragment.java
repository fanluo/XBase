package com.allens.lib_base.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.allens.lib_base.base.impl.IUiHelper;
import com.orhanobut.hawk.Hawk;
import com.trello.rxlifecycle3.components.support.RxFragment;

public abstract class BaseFragment extends RxFragment implements IUiHelper {


    protected Activity activity;

    private View view;

    /**
     * 获得全局的，防止使用getActivity()为空
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container
            , Bundle savedInstanceState) {

        view = inflater.inflate(getLayoutId(), container, false);
        bindView(view);
        return view;
    }


    public abstract int getLayoutId();

    public abstract void bindView(View view);


    /**
     * =======================================================================================================================
     * startActivity
     * =======================================================================================================================
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(activity, clz));
    }

    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(activity, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * =======================================================================================================================
     * 显示toast
     * =======================================================================================================================
     */

    @Override
    public void toast(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toast(int resId) {
        Toast.makeText(activity, getResId(resId), Toast.LENGTH_SHORT).show();
    }


    /**
     * =======================================================================================================================
     * 加载中动画
     * =======================================================================================================================
     */

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * =======================================================================================================================
     * findViewById 简写
     * =======================================================================================================================
     */

    @Override
    public <T extends View> T $(int resId) {
        if (view != null)
            return view.findViewById(resId);
        return null;
    }


    /**
     * =======================================================================================================================
     * 获取资源 id 返回String
     * =======================================================================================================================
     */
    @Override
    public String getResId(int id) {
        return getResources().getString(id);
    }


    /**
     * =======================================================================================================================
     * 获取SharedPreference
     * =======================================================================================================================
     */

    @Override
    public <T> T getPref(String key) {
        return Hawk.get(key);
    }

    @Override
    public <T> T getPref(String key, T defaultValue) {
        return Hawk.get(key, defaultValue);
    }

    @Override
    public boolean deletePref(String key) {
        return Hawk.delete(key);
    }

    @Override
    public boolean deleteAllPref() {
        return Hawk.deleteAll();
    }

    @Override
    public long getPrefCount() {
        return Hawk.count();
    }

    @Override
    public <T> boolean putPref(String key, T value) {
        return Hawk.put(key, value);
    }

    @Override
    public boolean containsPref(String key) {
        return Hawk.contains(key);
    }
}
