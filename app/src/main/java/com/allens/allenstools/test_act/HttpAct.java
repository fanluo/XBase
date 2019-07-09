package com.allens.allenstools.test_act;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.allens.allenstools.R;
import com.allens.allenstools.TestBean;
import com.allens.lib_base.base.BaseActivity;
import com.allens.lib_base.log.LogHelper;
import com.allens.lib_base.retrofit.XHttp;
import com.allens.lib_base.retrofit.download.impl.OnDownLoadListener;
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
                .retryOnConnectionFailure(false)
                .isLog(true)
                .isLogToFile(true)
//                .addHeard()
                .build();


        $(R.id.button_get).setOnClickListener(v -> {
            do_body();
        });

        $(R.id.start_download).setOnClickListener(v -> {
            xHttp.doDownload(url, "sdcard/allens","text1.mp4", loadListener);
        });

        $(R.id.pause_1).setOnClickListener(v -> {
            xHttp.doDownLoadPause(url);
        });
        $(R.id.cancel).setOnClickListener(v -> {
            xHttp.doDownLoadCancel(url);
        });


        $(R.id.start_download_2).setOnClickListener(v -> {
            xHttp.doDownload(url2, "sdcard/allens","text2.mp4", loadListener);
        });

        $(R.id.pause_2).setOnClickListener(v -> {
            xHttp.doDownLoadPause(url2);
        });
        $(R.id.cancel_2).setOnClickListener(v -> {
            xHttp.doDownLoadCancel(url2);
        });


    }

    String url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
//    String url2 = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
//    String url2 = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";


    String url2 = "http://v.ysbang.cn//data/video/2015/rkb/2015rkb01.mp4";

//    String url2 = "http://pic1.win4000.com/pic/c/6b/44765b0881.jpg";

    OnDownLoadListener loadListener = new OnDownLoadListener() {
        @Override
        public void onError(String key, Throwable throwable) {
            LogHelper.d("download onError %s", throwable.getMessage());
            if (key.equals(url))
                test(key + "  download 失败" + throwable.getMessage());
            else
                text1(key + "  download 失败" + throwable.getMessage());
        }

        @Override
        public void onSuccess(String key, String path) {
            LogHelper.d("download success " + path);
            if (key.equals(url))
                test(key + " download success " + path);
            else
                text1(key + " download success " + path);
        }

        @Override
        public void update(String key, long read, long count, boolean done) {
            super.update(key, read, count, done);
//            LogHelper.d(key + " download update read %s , count %s, done %s", read, count, done);
        }

        @Override
        public void onPause(String key) {
            super.onPause(key);
            if (key.equals(url))
                test(key + " download onPause " + key);
            else
                text1(key + " download onPause " + key);
        }

        @Override
        public void onCancel(String key) {
            super.onCancel(key);
            if (key.equals(url))
                test(key + " download onCancel " + key);
            else
                text1(key + " download onCancel " + key);
        }

        @Override
        public void onProgress(String key, int progress) {
            LogHelper.d(key + " progress %s", progress);
            if (key.equals(url))
                test("进度-----》" + progress);
            else
                text1("进度-----》" + progress);
        }
    };

    private void do_body() {
        LogHelper.i("[网络请求 start]");
        xHttp.doBody(this, TestBean.class, "/app/new_word_list", new OnHttpListener<TestBean>() {
            @Override
            public void onMap(Map<String, Object> map) {
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
                LogHelper.i("getListener 请求成功 %s, thread %s", s.toString(), Thread.currentThread().getName());
                test("请求成功--->\" + s.toString()");
            }

            @Override
            public void onError(Throwable e) {
                LogHelper.i("getListener 请求失败 %s, thread %s", e.getMessage(), Thread.currentThread().getName());
                test("请求失败--->" + e.getMessage());
            }
        });
    }

    private void test(String info) {
        ((TextView) $(R.id.text)).setText(info);
    }

    private void text1(String info) {
        ((TextView) $(R.id.text1)).setText(info);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogHelper.i("activity finish ");
    }
}
