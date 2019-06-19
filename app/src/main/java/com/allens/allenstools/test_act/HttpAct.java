package com.allens.allenstools.test_act;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.allens.allenstools.R;
import com.allens.lib_base.base.BaseActivity;
import com.allens.lib_base.log.LogHelper;
import com.allens.lib_base.retrofit.XHttp;
import com.allens.lib_base.retrofit.impl.OnDownLoadListener;
import com.allens.lib_base.retrofit.impl.OnHttpListener;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;


@Route(path = "/act/http")
public class HttpAct extends BaseActivity {

    private XHttp xHttp;

    @Override
    public int getContentViewId() {
        return R.layout.activity_http;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @SuppressLint("CheckResult")
    @Override
    protected void initListener() {
        permissionRequest(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        LogHelper.i("给了权限");
                        toast("给了权限");
                    } else {
                        LogHelper.i("拒绝权限");
                        toast("拒绝权限");
                    }
                });

        xHttp = new XHttp.Builder(this)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .baseUrl("https://www.wanandroid.com")
                .retryOnConnectionFailure(false)
                .isLog(true)
                .isLogToFile(true)
//                .addHeard()
                .build();


        $(R.id.button3).setOnClickListener(v -> {
            xHttp.doGet(String.class, "/wxarticle/chapters/json", new OnHttpListener<String>() {
                @Override
                public void onMap(Map<String, Object> map) {
                    map.put("size", "1");
                }

                @Override
                public void onSuccess(String s) {

                }

                @Override
                public void onError(Throwable e) {

                }
            });
        });

        $(R.id.button).setOnClickListener(v -> {
            String url = "http://dik.img.kttpdq.com/pic/134/93216/6504077b3c9af2ca_1366x768.jpg";
            xHttp.doDownLoad("123", url, "sdcard/allens", new OnDownLoadListener() {

                @Override
                public void onProgress(String key, int terms) {
                    LogHelper.i("key %s progress %s", key, terms);
                }

                @Override
                public void onError(String key, Throwable e) {
                    LogHelper.i("key %s , error %s", key, e.getMessage());
                }

                @Override
                public void onSuccess(String key, String path) {
                    LogHelper.i("key %s success path %s", key, path);
                }

                @Override
                public void onCancel(String key) {

                }

                @Override
                public void already(String path) {
                    LogHelper.i(" already path %s", path);
                }
            });
        });


        $(R.id.cancel_by_id).setOnClickListener(v -> {
            xHttp.cancelDownLoad("123");
        });
    }
}