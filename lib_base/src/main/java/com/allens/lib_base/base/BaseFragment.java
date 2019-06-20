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
import androidx.fragment.app.Fragment;

import lombok.Getter;

public abstract class BaseFragment extends Fragment implements IUiHelper {


    @Getter
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


    abstract int getLayoutId();

    abstract void bindView(View view);


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
}
