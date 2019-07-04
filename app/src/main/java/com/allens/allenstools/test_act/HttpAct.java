package com.allens.allenstools.test_act;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.allens.allenstools.R;
import com.allens.allenstools.TestBean;
import com.allens.lib_base.base.BaseActivity;
import com.allens.lib_base.log.LogHelper;
import com.allens.lib_base.retrofit.XHttp;
import com.allens.lib_base.retrofit.impl.OnDownLoadListener;
import com.allens.lib_base.retrofit.impl.OnHttpListener;

import java.util.Map;
import java.util.concurrent.TimeUnit;


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

        xHttp = new XHttp.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .baseUrl("http://134.175.88.222/")
//                .baseUrl("https://www.wanandroid.com")
                .retryOnConnectionFailure(false)
                .isLog(true)
                .isLogToFile(true)
//                .addHeard()
                .build();


        $(R.id.button3).setOnClickListener(v -> {
            LogHelper.i("[网络请求 start]");
            xHttp.doBody(this, TestBean.class, "/app/new_word_list", new OnHttpListener<TestBean>() {
                @Override
                public void onMap(Map<String, Object> map) {
//                    map.put("size", "1");
//                    {"at":"bc55768398bebca94edfcdd65b3b4777","pageIndex":1,"appid":"HITVxaFRrwGwyZ2P","pageSize":5,"id":"cca7ff68df2b","sn":"XB02A0A184601000999","lang":"cn"}
                    map.put("at", "bc55768398bebca94edfcdd65b3b4777");
                    map.put("pageIndex", 1);
                    map.put("appid", "HITVxaFRrwGwyZ2P");
                    map.put("id", "cca7ff68df2b");
                    map.put("pageSize", 20);
                    map.put("sn", "XB02A0A184601000999");
                    map.put("lang", "cn");
                }


                @Override
                public void onSuccess(TestBean s) {
                    LogHelper.i("get 请求成功 %s, thread %s", s.toString(), Thread.currentThread().getName());
                }

                @Override
                public void onError(Throwable e) {
                    LogHelper.i("get 请求失败 %s, thread %s", e.getMessage(), Thread.currentThread().getName());
                }
            });
        });

        $(R.id.button).setOnClickListener(v -> {
            String url = "http://dik.img.kttpdq.com/pic/134/93216/6504077b3c9af2ca_1366x768.jpg";
            xHttp.doDownLoad("123", url, "美女.png", "sdcard/allens", new OnDownLoadListener() {

                @Override
                public void onStart(String key) {
                    toast("开始下载");
                }

                @Override
                public void onProgress(String key, int terms) {
                    ((Button) $(R.id.button)).setText("下载 " + terms);
                    LogHelper.i("key %s progress %s， thread name %s", key, terms, Thread.currentThread().getName());
                }

                @Override
                public void onError(String key, Throwable e) {
                    LogHelper.i("key %s , error %s", key, e.getMessage());
                    toast("下载失败");
                }

                @Override
                public void onSuccess(String key, String path) {
                    LogHelper.i("key %s success path %s", key, path);
                    toast("下载成功 " + path);
                }

                @Override
                public void already(String key, String path) {
                    LogHelper.i(" already path %s", path);
                    toast("文件已下载");
                }
            });
        });


        $(R.id.cancel_by_id).setOnClickListener(v -> {
            xHttp.cancelDownLoad("123");
            ((Button) $(R.id.button)).setText("下载");
        });

        $(R.id.button4).setOnClickListener(v -> {
            String url = "https://media.w3.org/2010/05/sintel/trailer.mp4";
//            String url = "http://dik.img.kttpdq.com/pic/134/93216/6504077b3c9af2ca_1366x768.jpg";
            xHttp.downLoadByService(this, "测试.mp4", "sdcard/allens", url);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogHelper.i("activity finish ");
        xHttp.cancelAllDownload();
    }
}
